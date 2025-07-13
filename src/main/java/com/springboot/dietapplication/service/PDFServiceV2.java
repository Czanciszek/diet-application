package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.mongo.dish.MongoDishProduct;
import com.springboot.dietapplication.model.mongo.menu.MongoMeal;
import com.springboot.dietapplication.model.mongo.menu.MongoWeekMenu;
import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import com.springboot.dietapplication.model.mongo.user.UserEntity;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.model.type.enums.AmountType;
import com.springboot.dietapplication.utils.RomanianNumber;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PDFServiceV2 {

    @Autowired
    ProductServiceV2 productService;
    @Autowired
    MenuServiceV2 menuService;
    @Autowired
    MealServiceV2 mealService;
    @Autowired
    JwtUserDetailsService userDetailsService;

    int pageOffset = 680;
    MenuType menu;

    GenerateMenuType generateMenuType;

    Map<String, Integer> fileLocations = new HashMap<>();

    public File generateMenu(GenerateMenuType generateMenuType) {

        try {
            this.generateMenuType = generateMenuType;
            String menuId = generateMenuType.getMenuId();

            File file = File.createTempFile("resources/PDF/menu_" + generateMenuType.getMenuId(), ".pdf");

            PDDocument document = new PDDocument();

            menu = menuService.getMenuById(menuId);

            List<MongoMeal> menuMeals = mealService.findMealsInMenu(menu.getId());

            if (generateMenuType.isGenerateRecipes()) {
                fileLocations.put("Recipe-start", 0);
                makeMenuDishRecipes(document, menuMeals);
                fileLocations.put("Recipe-end", document.getNumberOfPages() - 1);
            }

            makeMenuDetails(document);

            if (generateMenuType.isGenerateRecipes())
                moveRecipesToTheEnd(document);

            if (generateMenuType.isGenerateShoppingList())
                makeShoppingList(document, menuMeals);

            document.save(file);
            document.close();

            return file;
        } catch (IOException | AssertionError e) {
            e.printStackTrace();
            return new File("dummy.pdf");
        }

    }

    private void addHyperlink(PDDocument document, PDRectangle rectangle, int destinationPage) throws IOException {

        PDPageDestination destination = new PDPageFitWidthDestination();
        destination.setPage(document.getPage(destinationPage));

        PDActionGoTo action = new PDActionGoTo();
        action.setDestination(destination);

        PDAnnotationLink link = new PDAnnotationLink();
        link.setAction(action);
        link.setRectangle(rectangle);

        PDPage page = document.getPage(document.getNumberOfPages() - 1);
        page.getAnnotations().add(link);
    }

    private void moveRecipesToTheEnd(PDDocument document) {
        PDPageTree allPages = document.getDocumentCatalog().getPages();
        if (allPages.getCount() < 2
                || !fileLocations.containsKey("Recipe-end")
                || !fileLocations.containsKey("Recipe-start")
        ) { return; }

        int firstPageIndex =  fileLocations.get("Recipe-start");
        int lastPageIndex = fileLocations.get("Recipe-end");
        List<PDPage> recipePages = new ArrayList<>();
        int wsk = firstPageIndex;
        while (firstPageIndex++ <= lastPageIndex) {
            recipePages.add(allPages.get(wsk));
            allPages.remove(wsk);
        }

        recipePages.forEach(allPages::add);
    }

    private void makeHeader(PDDocument document, PDPageContentStream contentStream) throws IOException {
        PDType0Font timesBold = PDType0Font.load(document, getFont("timesbd.ttf"));
        String header = "Dieta dla ";
        switch (menu.getPatient().getSex()) {
            case FEMALE -> header += "Pani ";
            case MALE -> header += "Pana ";
            case OTHER -> {}
        }
        header += menu.getPatient().getName() + " " + menu.getPatient().getSurname();
        writeText(contentStream, new Point(40, 740), timesBold, 24, header);
        setNewLine(document, contentStream, new Point(0, -20), false, true);
    }

    private void makeFooter(PDPageContentStream contentStream) throws IOException {
        UserEntity user = userDetailsService.getCurrentUser();
        String footerText = user.getPdfFooter();
        writeText(contentStream, new Point(40, 20), PDType1Font.TIMES_ROMAN, 12, footerText);
    }

    private void makeDateRange(PDPageContentStream contentStream, MenuType menu) throws IOException {
        String startDate = changeDateFormat(menu.getStartDate());
        String endDate = changeDateFormat(menu.getEndDate());
        String dateRange = startDate + " - " + endDate;
        writeText(contentStream, new Point(40, 710), PDType1Font.TIMES_BOLD, 14, dateRange);
    }

    private void makeMenuDetails(PDDocument document) throws IOException {
        List<MongoWeekMenu> weekMenus = mealService.weekMealsInMenu(menu.getId());

        Locale.setDefault(new Locale("pl", "PL"));
        ResourceBundle bundle = ResourceBundle.getBundle("strings/configuration");

        PDType0Font timesNormal = PDType0Font.load(document, getFont("times.ttf"));
        PDType0Font timesBold = PDType0Font.load(document, getFont("timesbd.ttf"));

        pageOffset = 0;
        PDPageContentStream contentStream = setNewPage(document, true);

        makeHeader(document, contentStream);

        if (generateMenuType.getRecommendations() != null && !generateMenuType.getRecommendations().isEmpty()) {
            showRecommendations(document, contentStream);
        }

        for (int i = 0; i < weekMenus.size(); i++) {
            if (pageOffset < 380) {
                closeContentStream(contentStream);
                contentStream = setNewPage(document, true);
            } else {
                contentStream = setNewLine(document, contentStream, new Point(0, -20), false, true);
            }

            writeText(contentStream, new Point(40, pageOffset), timesBold, 14, RomanianNumber.getRomanianValue(i+1));
            contentStream = setNewLine(document, contentStream, new Point(0, -20), true, true);

            Map<String, List<MongoMeal>> weekList = new TreeMap<>(weekMenus.get(i).getMeals());
            for (Map.Entry<String, List<MongoMeal>> dayEntry: weekList.entrySet()) {

                contentStream = setNewLine(document, contentStream, new Point(0, -20), false, true);
                contentStream = checkPageOffset(document, contentStream, true);

                DayOfWeek dayOfWeek = LocalDate.parse(dayEntry.getKey()).getDayOfWeek();
                String dayName = bundle.getString(dayOfWeek.toString());
                String dateLine = generateMenuType.isShowDates() ?
                        dayName + " - " + changeDateFormat(dayEntry.getKey()) :
                        dayName;

                writeText(contentStream, new Point(40, pageOffset), timesBold, 14, dateLine);
                contentStream = setNewLine(document, contentStream, new Point(0, -20), false, true);

                Comparator<MongoMeal> comp = (p1, p2) -> {
                    if (getAssignedValue(p1.getFoodType()) == getAssignedValue(p2.getFoodType())) {
                        return Boolean.compare(p1.getIsProduct(), p2.getIsProduct());
                    }
                    return getAssignedValue(p1.getFoodType()) > getAssignedValue(p2.getFoodType()) ? 1 : -1;
                };
                dayEntry.getValue().sort(comp);

                FoodType foodType = null;

                for (MongoMeal meal : dayEntry.getValue()) {

                    if (foodType != meal.getFoodType()) {
                        foodType = meal.getFoodType();
                        String text = bundle.getString(meal.getFoodType().name()) + ": ";
                        writeText(contentStream, new Point(40, pageOffset), timesBold, 12, text);
                    }

                    String text;
                    Float mealGrams = meal.getProducts()
                            .stream()
                            .reduce(0.0f, (partialGramsResult, product) -> partialGramsResult + product.getGrams(), Float::sum);

                    if (meal.getIsProduct()) {
                        text = meal.getName() + " " + Math.round(mealGrams) + "g";
                    } else {
                        if (meal.getGrams() > 0) {
                            text = meal.getName() + " - " + Math.round(meal.getGrams()) + "g";
                        } else {
                            text = meal.getName() + " - " + setPortionLabel(Math.round(meal.getPortions()));
                        }
                    }

                    if (!meal.getIsProduct() && generateMenuType.isGenerateRecipes() && fileLocations.containsKey(meal.getName())) {
                        float textWidth = timesNormal.getStringWidth(text) / 1000 * 12;
                        PDRectangle rectangle = new PDRectangle(150, pageOffset, textWidth, 20);
                        int pageLocation = fileLocations.get(meal.getName());
                        addHyperlink(document, rectangle, pageLocation);
                    }

                    writeText(contentStream, new Point(150, pageOffset), timesNormal, 12, text);

                    contentStream = setNewLine(document, contentStream, new Point(0, -20), false, true);
                    contentStream = checkPageOffset(document, contentStream, true);
                }

                //Check whenever pageOffset is too close margin
                contentStream = checkPageOffset(document, contentStream, true);
            }
        }

        closeContentStream(contentStream);
    }

    private int getAssignedValue(FoodType foodType) {
        return switch (foodType.name().toUpperCase()) {
            case "PRE_BREAKFAST" -> 0;
            case "BREAKFAST" -> 1;
            case "BRUNCH" -> 2;
            case "SNACK" -> 3;
            case "DINNER" -> 4;
            case "TEA" -> 5;
            case "SUPPER" -> 6;
            case "PRE_WORKOUT" -> 7;
            case "POST_WORKOUT" -> 8;
            case "OVERNIGHT" -> 9;
            default -> Integer.MAX_VALUE;
        };

    }

    private void writeFoodType(PDDocument document, PDPageContentStream contentStream, FoodType foodType) throws IOException {
        PDType0Font timesBold = PDType0Font.load(document, getFont("timesbd.ttf"));
        int fontSize = 24;

        switch (foodType) {
            case PRE_BREAKFAST -> writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Przedśniadania");
            case BREAKFAST -> writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Śniadania");
            case BRUNCH -> writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "II Śniadania");
            case SNACK -> writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Przekąska");
            case DINNER -> writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Obiady");
            case TEA -> writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Podwieczorki");
            case SUPPER -> writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Kolacje");
            case PRE_WORKOUT -> writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Przedtreningówki");
            case POST_WORKOUT -> writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Potreningówki");
            case OVERNIGHT -> writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Posiłki nocne");
        }
    }

    private void showRecommendations(PDDocument document, PDPageContentStream contentStream) throws IOException {
        PDType0Font timesNormal = PDType0Font.load(document, getFont("times.ttf"));
        PDType0Font timesBoldItalic = PDType0Font.load(document, getFont("timesbi.ttf"));

        String recommendations = "Zalecenia:";
        writeText(contentStream, new Point(40, pageOffset), timesBoldItalic, 20, recommendations);
        contentStream = setNewLine(document, contentStream, new Point(0, -20), false, true);

        // Display recommendations
        String[] textLines = generateMenuType.getRecommendations().split("\n");
        for (String line : textLines) {
            if (line.length() == 0) {
                contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);
                continue;
            }

            int index = 0;
            int margin = 100;
            do {
                String subLine = (line.length() > index + margin) ? line.substring(index, index + margin) : line.substring(index);

                int newIndex = subLine.lastIndexOf(" ");
                if (newIndex > 0 && (index + margin) < line.length())
                    subLine = subLine.substring(0, newIndex);

                writeText(contentStream, new Point(60, pageOffset), timesNormal, 12, subLine);
                contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);
                index += (newIndex > 0 && (index + margin) < line.length()) ? newIndex : margin;

                //Check whenever pageOffset is too close margin
                contentStream = checkPageOffset(document, contentStream, false);
            } while (index < line.length());
        }

        setNewLine(document, contentStream, new Point(0, -20), false, true);
    }

    private void makeMenuDishRecipes(PDDocument document, List<MongoMeal> menuMeals) throws IOException {

        List<MealType> mealList = menuMeals
                .stream()
                .filter(meal -> (!meal.getIsProduct()) && meal.getAttachToRecipes() && meal.getOriginDishId() != null)
                .map(MealType::new)
                .collect(Collectors.toList());

        if (mealList.size() == 0) return;

        PDType0Font timesNormal = PDType0Font.load(document, getFont("times.ttf"));
        PDType0Font timesBold = PDType0Font.load(document, getFont("timesbd.ttf"));

        PDPageContentStream contentStream = null;
        pageOffset = 0;

        Map<FoodType, List<MealType>> recipeMealsMap = new TreeMap<>();
        mealList.forEach(meal -> {
            List<MealType> currentMeals = recipeMealsMap.getOrDefault(meal.getFoodType(), new ArrayList<>());
            currentMeals.add(meal);
            recipeMealsMap.put(meal.getFoodType(), currentMeals);
        });

        for (Map.Entry<FoodType, List<MealType>> foodTypeListEntry: recipeMealsMap.entrySet()) {

            closeContentStream(contentStream);
            contentStream = setNewPage(document, false);

            writeFoodType(document, contentStream, foodTypeListEntry.getKey());
            contentStream = setNewLine(document, contentStream, new Point(0, -40), false, false);

            List<MealType> sortedMeal = foodTypeListEntry.getValue().stream().sorted().collect(Collectors.toList());
            for (MealType meal: sortedMeal) {

                if (pageOffset < 380) {
                    closeContentStream(contentStream);
                    contentStream = setNewPage(document, false);
                } else {
                    contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);
                }

                // Display dish name
                writeText(contentStream, new Point(40, pageOffset), timesBold, 18, meal.getName());
                contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);
                fileLocations.put(meal.getName(), document.getNumberOfPages() - 1);

                // Display dish portions
                String portions = setPortionLabel(Math.round(meal.getDishPortions()));
                writeText(contentStream, new Point(40, pageOffset), timesBold, 14, "(" + portions + ")");
                contentStream = setNewLine(document, contentStream, new Point(0, -30), false, false);

                // Display ingredient word
                if (meal.getProductList().size() > 0) {
                    writeText(contentStream, new Point(40, pageOffset), timesBold, 14, "Składniki:");
                    contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);
                }

                // Display dish ingredients
                for (ProductDishType product : meal.getProductList()) {
                    String productPart = "\u2022 ";

                    int grams = ((int) meal.getDishPortions() / (int) meal.getPortions()) * (int) product.getGrams();
                    if (grams > 0) {
                        productPart += grams + "g ";
                    }

                    if (product.getAmount() > 0 && product.getAmountType() != null && !product.getAmountType().equals(AmountType.NONE)) {
                        productPart += "(x" + product.getAmount() + " " + AmountType.toLocalizedString(product.getAmountType()) + ") ";
                    }

                    String name = (product.getProductName() == null || product.getProductName().isEmpty()) ?
                            (product.getProductId() + "!!!!") : product.getProductName();
                    productPart += name;
                    writeText(contentStream, new Point(60, pageOffset), timesNormal, 14, productPart);
                    contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);

                    //Check whenever pageOffset is too close margin
                    contentStream = checkPageOffset(document, contentStream, false);
                }
                contentStream = setNewLine(document, contentStream, new Point(0, -10), false, false);

                // Display recipe word
                writeText(contentStream, new Point(40, pageOffset), timesBold, 14, "Sposób przygotowania:");
                contentStream = setNewLine(document, contentStream, new Point(0, -30), false, false);

                // Display recipe
                if (meal.getRecipe() == null) {
                    continue;
                }

                String[] recipeLines = meal.getRecipe().replaceAll("\t", "    ").split("\n");
                for (String line : recipeLines) {
                    if (line.length() == 0) {
                        contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);
                        continue;
                    }

                    int index = 0;
                    int margin = 100;
                    do {
                        //Check whenever pageOffset is too close margin
                        contentStream = checkPageOffset(document, contentStream, false);

                        String subLine = (line.length() > index + margin) ? line.substring(index, index + margin) : line.substring(index);

                        int newIndex = subLine.lastIndexOf(" ");
                        if (newIndex > 0 && (index + margin) < line.length())
                            subLine = subLine.substring(0, newIndex);

                        writeText(contentStream, new Point(60, pageOffset), timesNormal, 12, subLine);
                        contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);
                        index += (newIndex > 0 && (index + margin) < line.length()) ? newIndex : margin;

                    } while (index < line.length());
                }
            }

        }

        closeContentStream(contentStream);
    }

    private void makeShoppingList(PDDocument document, List<MongoMeal> menuMeals) throws IOException {

        PDType0Font timesNormal = PDType0Font.load(document, getFont("times.ttf"));
        PDType0Font timesBold = PDType0Font.load(document, getFont("timesbd.ttf"));

        if (menuMeals.size() == 0) {
            return;
        }

        PDPageContentStream contentStream = setNewPage(document, false);

        // Display title
        writeText(contentStream, new Point(40, pageOffset), timesBold, 20,"Lista zakupów:");
        contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);

        // Obtain all product ids included in menu
        Set<String> productIds = menuMeals
                .stream()
                .flatMap(meal -> meal.getProducts().stream()
                        .map(MongoDishProduct::getProductId))
                .collect(Collectors.toSet());

        // Get all product properties by obtained productIds
        List<MongoProduct> productTypes = productService
                .findAll(productIds.stream().toList());

        // Group all found product by its categoryType
        Map<CategoryType, List<MongoProduct>> categoryProducts = new TreeMap<>();
        productTypes.forEach(product -> {
            List<MongoProduct> currentProducts = categoryProducts.getOrDefault(product.getCategory(), new ArrayList<>());
            currentProducts.add(product);
            categoryProducts.put(product.getCategory(), currentProducts);
        });

        // Find grams summary used for each product
        Map<String, Float> productGramsSummary = new HashMap<>();
        menuMeals
                .stream()
                .flatMap(meal -> meal.getProducts().stream())
                .forEach(dishProduct -> {
                    float currentGrams = productGramsSummary.getOrDefault(dishProduct.getProductId(), 0f);
                    productGramsSummary.put(dishProduct.getProductId(), dishProduct.getGrams() + currentGrams);
                });

        for (Map.Entry<CategoryType, List<MongoProduct>> categoryTypeListEntry: categoryProducts.entrySet()) {

            if (pageOffset < 160) {
                closeContentStream(contentStream);
                contentStream = setNewPage(document, false);
            }

            contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);
            writeText(contentStream, new Point(40, pageOffset), timesBold, 16,categoryTypeListEntry.getKey().getCategory());
            contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);

            List<MongoProduct> sortedProducts = categoryTypeListEntry.getValue().stream().sorted().collect(Collectors.toList());
            for (MongoProduct product: sortedProducts) {
                if (pageOffset < 80) {
                    closeContentStream(contentStream);
                    contentStream = setNewPage(document, false);
                }

                float grams = productGramsSummary.getOrDefault(product.getId(), 0f);
                String productSummary = "\u2022 " + product.getName() + " " + Math.round(grams) + "g";
                writeText(contentStream, new Point(60, pageOffset), timesNormal, 14, productSummary);
                contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);
            }
        }

        closeContentStream(contentStream);
    }

    private String setPortionLabel(int portions) {
        if (portions == 1) {
            return portions + " porcja";
        } else if (portions < 5) {
            return portions + " porcje";
        }
        return portions + " porcji";
    }

    private String changeDateFormat(String date) {
        String[] dateParts = date.split("-");
        if (dateParts.length != 3) return "";
        return dateParts[2] + "/" + dateParts[1] + "/" + dateParts[0];
    }

    private PDPageContentStream checkPageOffset(PDDocument document, PDPageContentStream contentStream, boolean withDateRange) throws IOException {
        if (pageOffset <= 80) {
            return setNewLine(document, contentStream, new Point(0, -20), true, withDateRange);
        }
        return contentStream;
    }

    private PDPageContentStream setNewLine(PDDocument document, PDPageContentStream contentStream, Point offset, boolean createNewPageIfNeeded, boolean withDateRange) throws IOException {
        pageOffset += offset.y;

        if (createNewPageIfNeeded && pageOffset <= 80) {
            closeContentStream(contentStream);
            contentStream = setNewPage(document, withDateRange);
        }

        return contentStream;
    }

    private PDPageContentStream setNewPage(PDDocument document, boolean withDateRange) throws IOException {
        PDPage page = new PDPage();
        document.addPage(page);

        boolean shouldAddDates = withDateRange && generateMenuType.isShowDates();
        pageOffset = shouldAddDates ? 680 : 720;

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        if (shouldAddDates) {
            makeDateRange(contentStream, menu);
        }

        makeFooter(contentStream);

        return contentStream;
    }

    private File getFont(String fontName) {

        try {
            URL resource = PDFServiceV2.class.getResource("/fonts/" + fontName);

            if (resource == null )
                return null;

            return Paths.get(resource.toURI()).toFile();
        } catch (Exception e) {
            return null;
        }

    }

    private void writeText(PDPageContentStream contentStream, Point offset, PDFont font, int fontSize, String text) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(offset.x, offset.y);
        contentStream.setFont(font, fontSize);
        contentStream.showText(text);
        contentStream.endText();
    }

    private void closeContentStream(PDPageContentStream contentStream) throws IOException {
        if (contentStream != null) {
            contentStream.close();
        }
    }
}
