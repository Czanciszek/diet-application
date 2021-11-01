package com.springboot.dietapplication.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class PDFService {

    public File generate() {

        try {

            File file = File.createTempFile("resources/PDF/hello-world", ".pdf");

            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,true,true);

            contentStream.setFont(PDType1Font.TIMES_BOLD, 24);
            contentStream.beginText();
            contentStream.newLineAtOffset(200,685);
            contentStream.showText("Hello World");
            contentStream.endText();
            contentStream.close();

            document.save(file);
            document.close();

            return file;
        } catch (IOException | AssertionError e) {
            e.printStackTrace();
            return new File("dummy.pdf");
        }

    }
}
