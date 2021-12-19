package com.springboot.dietapplication.service;

import com.springboot.dietapplication.helper.RomanianNumber;
import com.springboot.dietapplication.model.psql.menu.PsqlMenuProduct;
import com.springboot.dietapplication.model.type.MenuType;
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

    int pageOffset = 680;
    MenuType menu;

    public File generateMenu(Long menuId) {

        try {

            File file = File.createTempFile("resources/PDF/menu_" + menuId, ".pdf");

            PDDocument document = new PDDocument();

            menu = menuService.getMenuById(menuId);
            List<PsqlMenuProduct> menuProductList = menuService.menuProductLists(menuId);
            makeMenuDetails(document, menuProductList);

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
                    contentStream = setNewLine(document, contentStream, new Point(0, -20), false);
                }

                writeText(contentStream, new Point(40, pageOffset), timesBold, 14, RomanianNumber.getRomanianValue(weekMealCount));
                contentStream = setNewLine(document, contentStream, new Point(0, -20), false);
            }

            contentStream = setNewLine(document, contentStream, new Point(0, -20), true);

            String date = parseDateToString(dayEntry.getKey());
            String dayType = dayEntry.getValue().get(0).getDayType();

            writeText(contentStream, new Point(40, pageOffset), timesBold, 12, bundle.getString(dayType) + " - " + date);
            contentStream = setNewLine(document, contentStream, new Point(0, -20), false);

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
                contentStream = setNewLine(document, contentStream, new Point(0, -20), false);
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

    private PDPageContentStream setNewLine(PDDocument document, PDPageContentStream contentStream, Point offset, boolean createNewPageIfNeeded) throws IOException {
        pageOffset += offset.y;

        if (createNewPageIfNeeded && pageOffset <= 120) {
            closeContentStream(contentStream);
            contentStream = setNewPage(document, true);
        }

        return contentStream;
    }

    private PDPageContentStream setNewPage(PDDocument document, boolean withDateRange) throws IOException {
        PDPage page = new PDPage();
        document.addPage(page);
        resetPageOffset();

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        if (withDateRange) {
            makeDateRange(contentStream, menu);
        }

        makeFooter(contentStream);

        return contentStream;
    }

    private void resetPageOffset() {
        pageOffset = 680;
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
