package com.springboot.dietapplication.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDSimpleFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class PDFService {

    @Autowired
    MenuService menuService;

    public File generateMenu(Long menuId) {

        try {

            File file = File.createTempFile("resources/PDF/hello-world", ".pdf");

            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,true,true);
            writeText(contentStream, PDType1Font.TIMES_BOLD, 24, "Hello world");
            contentStream.close();

            document.save(file);
            document.close();

            return file;
        } catch (IOException | AssertionError e) {
            e.printStackTrace();
            return new File("dummy.pdf");
        }

    }

    private void writeText(PDPageContentStream contentStream, PDSimpleFont font, int fontSize, String text) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(200,685);
        contentStream.setFont(font, fontSize);
        contentStream.showText(text);
        contentStream.endText();
    }
}
