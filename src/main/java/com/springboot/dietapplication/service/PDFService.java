package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.menu.PsqlMenuProductList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDSimpleFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PDFService {

    @Autowired
    MenuService menuService;

    public File generateMenu(Long menuId) {

        try {

            File file = File.createTempFile("resources/PDF/menu_" + menuId, ".pdf");

            List<PsqlMenuProductList> menuProductList = menuService.menuProductLists(menuId);


            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,true,true);
            makeFooter(contentStream);
            writeText(contentStream, new Point(200, 685), PDType1Font.TIMES_BOLD, 24, "Hello world");
            contentStream.close();

            document.save(file);
            document.close();

            return file;
        } catch (IOException | AssertionError e) {
            e.printStackTrace();
            return new File("dummy.pdf");
        }

    }

    private void makeFooter(PDPageContentStream contentStream) throws IOException {
        String footerText = "Agnieszka Kaszuba-Czana Dietetyk kliniczny, Trener personalny";
        writeText(contentStream, new Point(50, 20), PDType1Font.TIMES_ROMAN, 12, footerText);
    }

    private void writeText(PDPageContentStream contentStream, Point offset, PDSimpleFont font, int fontSize, String text) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(offset.x, offset.y);
        contentStream.setFont(font, fontSize);
        contentStream.showText(text);
        contentStream.endText();
    }
}
