package com.springboot.dietapplication.service;

import com.springboot.dietapplication.helper.RomanianNumber;
import com.springboot.dietapplication.model.psql.menu.PsqlMenuProduct;
import com.springboot.dietapplication.model.type.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
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

@Service
public class PDFService {

    @Autowired
    MenuService menuService;

    @Autowired
    DishService dishService;

    @Autowired
    ProductService productService;

    int pageOffset = 680;
    MenuType menu;

    public File generateMenu(Long menuId) {

        try {

            File file = File.createTempFile("resources/PDF/menu_" + menuId, ".pdf");

            PDDocument document = new PDDocument();

            menu = menuService.getMenuById(menuId);

            List<PsqlMenuProduct> menuProductList = menuService.menuProductLists(menuId);
            makeMenuDetails(document, menuProductList);

            List<DishType> dishList = dishService.getAllByMenuId(menuId);
            List<ProductType> productTypeList = productService.getAll();
            makeMenuDishRecipes(document, dishList, productTypeList);

            document.save(file);
            document.close();

            return file;
        } catch (IOException | AssertionError e) {
            e.printStackTrace();
            return new File("dummy.pdf");
        }

    }

    private void makeFooter(PDPageContentStream contentStream) throws IOException {
        String footerText = "Agnieszka Kaszuba-Czana Dietetyk kliniczny, Psychodietetyk, Trener personalny";
        writeText(contentStream, new Point(40, 20), PDType1Font.TIMES_ROMAN, 12, footerText);
    }

    private void makeDateRange(PDPageContentStream contentStream, MenuType menu) throws IOException {
        String dateRange = getDateRange(menu);
        writeText(contentStream, new Point(40, 720), PDType1Font.TIMES_BOLD, 14, dateRange);
    }

    private void makeMenuDetails(PDDocument document, List<PsqlMenuProduct> menuProductList) throws IOException {

        Locale.setDefault(new Locale("pl", "PL"));
        ResourceBundle bundle = ResourceBundle.getBundle("strings/configuration");

        Map<DateTime, List<PsqlMenuProduct>> menuProductsMap = mapMenuMeals(menuProductList);

        PDType0Font timesNormal = PDType0Font.load(document, getFont("times.ttf"));
        PDType0Font timesBold = PDType0Font.load(document, getFont("timesbd.ttf"));

        PDPageContentStream contentStream = null;
        long currentWeekMealId = -1;
        int weekMealCount = 0;
        pageOffset = 0;

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

            String date = parseDateToString(dayEntry.getKey());
            String dayType = dayEntry.getValue().get(0).getDayType();

            writeText(contentStream, new Point(40, pageOffset), timesBold, 12, bundle.getString(dayType) + " - " + date);
            contentStream = setNewLine(document, contentStream, new Point(0, -20), false, true);

            dayEntry.getValue().sort(Comparator.comparingLong(PsqlMenuProduct::getFoodTypeId));
            long foodType = 0;

            for (PsqlMenuProduct product : dayEntry.getValue()) {

                if (dayMealIds.contains(product.getMealId())) continue;

                if (foodType != product.getFoodTypeId()) {
                    foodType = product.getFoodTypeId();
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
                }
                writeText(contentStream, new Point(150, pageOffset), timesNormal, 12, text);

                dayMealIds.add(product.getMealId());
                contentStream = setNewLine(document, contentStream, new Point(0, -20), false, true);
            }

        }

        closeContentStream(contentStream);
    }

    private void writeFoodType(PDDocument document, PDPageContentStream contentStream, FoodType foodType) throws IOException {
        PDType0Font timesBold = PDType0Font.load(document, getFont("timesbd.ttf"));
        int fontSize = 24;

        switch (foodType) {
            case BREAKFAST:
                writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "Śniadania");
                break;
            case BRUNCH:
                writeText(contentStream, new Point(40, pageOffset), timesBold, fontSize, "II Śniadania");
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
                break;
        }
    }

    private void makeMenuDishRecipes(PDDocument document, List<DishType> dishList, List<ProductType> productTypeList) throws IOException {
        PDType0Font timesNormal = PDType0Font.load(document, getFont("times.ttf"));
        PDType0Font timesBold = PDType0Font.load(document, getFont("timesbd.ttf"));

        PDPageContentStream contentStream = null;
        pageOffset = 0;

        if (dishList.size() == 0) return;
        dishList.sort(Comparator.comparing(DishType::getFoodType));

        FoodType foodType = null;

        for (DishType dish : dishList) {

            if (!dish.getFoodType().equals(foodType)) {
                // If new food type, make new page and display name type
                closeContentStream(contentStream);
                contentStream = setNewPage(document, false);

                foodType = dish.getFoodType();
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
            writeText(contentStream, new Point(40, pageOffset), timesBold, 18, dish.getName());
            contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);

            // Display dish portions
            String portions = setPortionLabel(Math.round(dish.getPortions()));
            writeText(contentStream, new Point(40, pageOffset), timesBold, 14, "(" + portions + ")");
            contentStream = setNewLine(document, contentStream, new Point(0, -30), false, false);

            // Display ingredient word
            if (dish.getProducts().size() > 0) {
                writeText(contentStream, new Point(40, pageOffset), timesBold, 14,"Składniki:");
                contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);
            }

            // Display dish ingredients
            for (ProductDishType product : dish.getProducts()) {
                int grams = (int) product.getGrams();
                Optional<ProductType> productType = productTypeList.stream().filter( x -> x.getId().equals(product.getProductId())).findFirst();
                String name;
                if (productType.isPresent()) {
                    name = productType.get().getName();
                } else {
                    // Shouldn't happen, whenever useful to find the source of missing product
                    name = product.getProductId() + "!!!!";
                }
                String productPart = "\u2022 " + grams + "g " + name;
                writeText(contentStream, new Point(60, pageOffset), timesNormal, 14,productPart);
                contentStream = setNewLine(document, contentStream, new Point(0, -20), false, false);

                //Check whenever pageOffset is too close margin
                contentStream = checkPageOffset(document, contentStream);
            }
            contentStream = setNewLine(document, contentStream, new Point(0, -10), false, false);

            // Display recipe word
            writeText(contentStream, new Point(40, pageOffset), timesBold, 14, "Sposób przygotowania:");
            contentStream = setNewLine(document, contentStream, new Point(0, -30), false, false);

            // Display recipe
            String[] recipeLines = dish.getRecipe().split("\n");
            for (String line : recipeLines) {
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
                    contentStream = checkPageOffset(document, contentStream);
                } while (index < line.length());
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

    private PDPageContentStream checkPageOffset(PDDocument document, PDPageContentStream contentStream) throws IOException {
        if (pageOffset <= 120) {
            return setNewLine(document, contentStream, new Point(0, -20), true, false);
        }
        return contentStream;
    }

    private PDPageContentStream setNewLine(PDDocument document, PDPageContentStream contentStream, Point offset, boolean createNewPageIfNeeded, boolean withDateRange) throws IOException {
        pageOffset += offset.y;

        if (createNewPageIfNeeded && pageOffset <= 120) {
            closeContentStream(contentStream);
            contentStream = setNewPage(document, withDateRange);
        }

        return contentStream;
    }

    private PDPageContentStream setNewPage(PDDocument document, boolean withDateRange) throws IOException {
        PDPage page = new PDPage();
        document.addPage(page);
        pageOffset = withDateRange ? 680 : 720;

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        if (withDateRange) {
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
