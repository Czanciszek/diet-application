package com.springboot.dietapplication.service;

import com.springboot.dietapplication.helper.RomanianNumber;
import com.springboot.dietapplication.model.psql.menu.PsqlFoodType;
import com.springboot.dietapplication.model.psql.menu.PsqlMenuProduct;
import com.springboot.dietapplication.model.psql.product.PsqlShoppingProduct;
import com.springboot.dietapplication.model.psql.user.UserEntity;
import com.springboot.dietapplication.model.type.*;
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
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PDFService {

    @Autowired
    MenuService menuService;
    @Autowired
    MealService mealService;
    @Autowired
    PatientService patientService;
    @Autowired
    JwtUserDetailsService userDetailsService;

    int pageOffset = 680;
    MenuType menu;
    PatientType patient;

    GenerateMenuType generateMenuType;

    Map<String, Integer> fileLocations = new HashMap<>();

    public File generateMenu(GenerateMenuType generateMenuType) {

        try {
            this.generateMenuType = generateMenuType;
            Long menuId = generateMenuType.getMenuId();

            File file = File.createTempFile("resources/PDF/menu_" + generateMenuType.getMenuId(), ".pdf");

            PDDocument document = new PDDocument();

            menu = menuService.getMenuById(menuId);
            patient = patientService.getPatientByMenuId(menuId);

            if (generateMenuType.isGenerateRecipes()) {
                fileLocations.put("Recipe-start", 0);
                makeMenuDishRecipes(document);
                fileLocations.put("Recipe-end", document.getNumberOfPages() - 1);
            }

            List<PsqlMenuProduct> menuProductList = menuService.menuProducts(menuId);
            makeMenuDetails(document, menuProductList);

            if (generateMenuType.isGenerateRecipes())
                moveRecipesToTheEnd(document);

            if (generateMenuType.isGenerateShoppingList())
                makeShoppingList(document);

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
        header += patient.isSex() ? "Pani " : "Pana ";
        header += patient.getName() + " " + patient.getSurname();
        writeText(contentStream, new Point(40, 740), timesBold, 24, header);
        setNewLine(document, contentStream, new Point(0, -20), false, true);
    }

    private void makeFooter(PDPageContentStream contentStream) throws IOException {
        UserEntity user = userDetailsService.getCurrentUser();
        String footerText = user.getPdfFooter();
        writeText(contentStream, new Point(40, 20), PDType1Font.TIMES_ROMAN, 12, footerText);
    }

    private void makeDateRange(PDPageContentStream contentStream, MenuType menu) throws IOException {
        String dateRange = getDateRange(menu);
        writeText(contentStream, new Point(40, 710), PDType1Font.TIMES_BOLD, 14, dateRange);
    }

    private void makeMenuDetails(PDDocument document, List<PsqlMenuProduct> menuProductList) throws IOException {

        Locale.setDefault(new Locale("pl", "PL"));
        ResourceBundle bundle = ResourceBundle.getBundle("strings/configuration");

        Map<DateTime, List<PsqlMenuProduct>> menuProductsMap = mapMenuMeals(menuProductList);

        PDType0Font timesNormal = PDType0Font.load(document, getFont("times.ttf"));
        PDType0Font timesBold = PDType0Font.load(document, getFont("timesbd.ttf"));

        pageOffset = 0;
        PDPageContentStream contentStream = setNewPage(document, true);
        long currentWeekMealId = -1;
        int weekMealCount = 0;

        makeHeader(document, contentStream);

        if (generateMenuType.getRecommendations() != null && !generateMenuType.getRecommendations().isEmpty()) {
            showRecommendations(document, contentStream);
        }

        Set<Long> dayMealIds = new HashSet<>();
        for (Map.Entry<DateTime, List<PsqlMenuProduct>> dayEntry : menuProductsMap.entrySet()) {

            if (dayEntry.getValue().size() == 0) continue;
            dayMealIds.clear();

            long weekMealId = dayEntry.getValue().get(0).getWeekMealId();
            if (currentWeekMealId != weekMealId) {
                weekMealCount++;
                currentWeekMealId = weekMealId;

                if (pageOffset < 380) {
                    closeContentStream(contentStream);
                    contentStream = setNewPage(document, true);
                } else {
                    contentStream = setNewLine(document, contentStream, new Point(0, -20), false, true);
                }

                writeText(contentStream, new Point(40, pageOffset), timesBold, 14, RomanianNumber.getRomanianValue(weekMealCount));
                contentStream = setNewLine(document, contentStream, new Point(0, -20), false, true);
            }

            contentStream = setNewLine(document, contentStream, new Point(0, -20), true, true);

            String dayType = dayEntry.getValue().get(0).getDayType();
            String dateLine = bundle.getString(dayType);
            if (generateMenuType.isShowDates()) {
                String date = parseDateToString(dayEntry.getKey());
                dateLine += " - " + date;
            }

            writeText(contentStream, new Point(40, pageOffset), timesBold, 14, dateLine);
            contentStream = setNewLine(document, contentStream, new Point(0, -20), false, true);

            Comparator<PsqlMenuProduct> comp = (p1, p2) -> {
                if (getAssignedValue(p1.getFoodType()) == getAssignedValue(p2.getFoodType())) {
                    return Boolean.compare(p1.isProduct(), p2.isProduct());
                }
                return getAssignedValue(p1.getFoodType()) > getAssignedValue(p2.getFoodType()) ? 1: -1;
            };
            dayEntry.getValue().sort(comp);

            long foodType = 0;

            for (PsqlMenuProduct product : dayEntry.getValue()) {

                if (dayMealIds.contains(product.getMealId())) continue;

                if (foodType != product.getFoodType().getId()) {
                    foodType = product.getFoodType().getId();
                    String text = bundle.getString(product.getFoodTypeName()) + ": ";
                    writeText(contentStream, new Point(40, pageOffset), timesBold, 12, text);
                }

                String text;
                if (product.isProduct()) {
                    text = product.getProductName() + " " + Math.round(product.getGrams()) + "g";
                } else {
                    if (product.getMealGrams() > 0) {
                        text = product.getMealName() + " - " + Math.round(product.getMealGrams()) + "g";
                    } else {
                        text = product.getMealName() + " - " + setPortionLabel(Math.round(product.getMealPortions()));
                    }

                    if (generateMenuType.isGenerateRecipes() && fileLocations.containsKey(product.getMealName())) {
                        float textWidth = timesNormal.getStringWidth(text) / 1000 * 12;
                        PDRectangle rectangle = new PDRectangle(150, pageOffset, textWidth, 20);
                        int pageLocation = fileLocations.get(product.getMealName());
                        addHyperlink(document, rectangle, pageLocation);
                    }
                }
                writeText(contentStream, new Point(150, pageOffset), timesNormal, 12, text);

                dayMealIds.add(product.getMealId());
                contentStream = setNewLine(document, contentStream, new Point(0, -20), false, true);
                contentStream = checkPageOffset(document, contentStream, true);
            }

            //Check whenever pageOffset is too close margin
            contentStream = checkPageOffset(document, contentStream, true);

        }

        closeContentStream(contentStream);
    }

    private int getAssignedValue(PsqlFoodType foodType) {
        switch (foodType.getName().toUpperCase()) {
            case "PRE_BREAKFAST":
                return 0;
            case "BREAKFAST":
                return 1;
            case "BRUNCH":
                return 2;
            case "SNACK":
                return 3;
            case "DINNER":
                return 4;
            case "TEA":
                return 5;
            case "SUPPER":
                return 6;
            case "PRE_WORKOUT":
                return 7;
            case "POST_WORKOUT":
                return 8;
            case "OVERNIGHT":
                return 9;
        }

        return Integer.MAX_VALUE;
    }

    private void writeFoodType(PDDocument document, PDPageContentStream contentStream, FoodType foodType) throws IOException {
        PDType0Font timesBold = PDType0Font.load(document, getFont("timesbd.ttf"));
        int fontSize = 24;

        switch (foodType) {
            case PRE_BREAKFAST:
                writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Przedśniadania");
                break;
            case BREAKFAST:
                writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Śniadania");
                break;
            case BRUNCH:
                writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "II Śniadania");
                break;
            case SNACK:
                writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Przekąska");
                break;
            case DINNER:
                writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Obiady");
                break;
            case TEA:
                writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Podwieczorki");
                break;
            case SUPPER:
                writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Kolacje");
                break;
            case PRE_WORKOUT:
                writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Przedtreningówki");
                break;
            case POST_WORKOUT:
                writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Potreningówki");
            case OVERNIGHT:
                writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Posiłki nocne");
                break;
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

    private void makeMenuDishRecipes(PDDocument document) throws IOException {
        List<MealType> mealList = mealService
                .getMealsByMenuId(menu.getId())
                .stream()
                .filter(meal -> (meal.getIsProduct() == 0) && meal.getOriginMealId() != null && meal.getOriginMealId().equals(meal.getId()))
                .collect(Collectors.toList());

        PDType0Font timesNormal = PDType0Font.load(document, getFont("times.ttf"));
        PDType0Font timesBold = PDType0Font.load(document, getFont("timesbd.ttf"));

        PDPageContentStream contentStream = null;
        pageOffset = 0;

        if (mealList.size() == 0) return;
        mealList.sort(new FoodTypeComparator());

        FoodType foodType = null;

        for (MealType meal : mealList) {

            if (!meal.getFoodType().equals(foodType)) {
                // If new food type, make new page and display name type
                closeContentStream(contentStream);
                contentStream = setNewPage(document, false);

                foodType = meal.getFoodType();
                writeFoodType(document, contentStream, foodType);
                contentStream = setNewLine(document, contentStream, new Point(0, -40), false, false);
            } else {
                // If same as previous, check if should add new page or just set new line
                if (pageOffset < 380) {
                    closeContentStream(contentStream);
                    contentStream = setNewPage(document, false);
                } else {
                    contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);
                }
            }

            // Display dish name
            writeText(contentStream, new Point(40, pageOffset), timesBold, 18, meal.getName());
            contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);
            fileLocations.put(meal.getName(), document.getNumberOfPages() -1);

            // Display dish portions
            String portions = setPortionLabel(Math.round(meal.getDishPortions()));
            writeText(contentStream, new Point(40, pageOffset), timesBold, 14, "(" + portions + ")");
            contentStream = setNewLine(document, contentStream, new Point(0, -30), false, false);

            // Display ingredient word
            if (meal.getProductList().size() > 0) {
                writeText(contentStream, new Point(40, pageOffset), timesBold, 14,"Składniki:");
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

        closeContentStream(contentStream);
    }

    private void makeShoppingList(PDDocument document) throws IOException {

        List<PsqlShoppingProduct> shoppingProductList = menuService.getShoppingProductsForMenu(menu.getId());

        PDType0Font timesNormal = PDType0Font.load(document, getFont("times.ttf"));
        PDType0Font timesBold = PDType0Font.load(document, getFont("timesbd.ttf"));

        if (shoppingProductList.size() == 0) {
            return;
        }

        PDPageContentStream contentStream = setNewPage(document, false);

        // Display title
        writeText(contentStream, new Point(40, pageOffset), timesBold, 20,"Lista zakupów:");
        contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);

        String categoryName = "";

        for (PsqlShoppingProduct shoppingProduct : shoppingProductList) {

            if (!shoppingProduct.getCategoryName().equals(categoryName)) {
                if (pageOffset < 160) {
                    closeContentStream(contentStream);
                    contentStream = setNewPage(document, false);
                }

                categoryName = shoppingProduct.getCategoryName();

                contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);
                writeText(contentStream, new Point(40, pageOffset), timesBold, 16,shoppingProduct.getCategoryName());
                contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);
            } else if (pageOffset < 80) {
                closeContentStream(contentStream);
                contentStream = setNewPage(document, false);
            }

            String productSummary = "\u2022 " + shoppingProduct.getProductName() + " " + Math.round(shoppingProduct.getGrams()) + "g";
            writeText(contentStream, new Point(60, pageOffset), timesNormal, 14, productSummary);
            contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);

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

    private Map<DateTime, List<PsqlMenuProduct>> mapMenuMeals(List<PsqlMenuProduct> menuProductList) {
        Map<DateTime, List<PsqlMenuProduct>> menuProductsMap = new TreeMap<>();
        for (PsqlMenuProduct menuProduct : menuProductList) {
            DateTime date = new DateTime(menuProduct.getDate());

            if (menuProductsMap.containsKey(date)) {
                List<PsqlMenuProduct> psqlMenuProductList = menuProductsMap.get(date);
                psqlMenuProductList.add(menuProduct);
                menuProductsMap.put(date, psqlMenuProductList);
            } else {
                menuProductsMap.put(date, new ArrayList<>(Collections.singletonList(menuProduct)));
            }
        }

        return menuProductsMap;
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
            URL resource = PDFService.class.getResource("/fonts/" + fontName);

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

    private String parseDateToString(DateTime date) {
        return date.toString("dd/MM/yyyy");
    }

    private String getDateRange(MenuType menuType) {

        String oldestDate = parseDateToString(new DateTime(menuType.getStartDate()));
        String newestDate = parseDateToString(new DateTime(menuType.getEndDate()));

        return oldestDate + " - " + newestDate;
    }

    private void closeContentStream(PDPageContentStream contentStream) throws IOException {
        if (contentStream != null) {
            contentStream.close();
        }
    }
}
