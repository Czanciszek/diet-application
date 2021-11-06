package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.service.DataService;
import com.springboot.dietapplication.service.PDFService;
import io.github.biezhi.excel.plus.Reader;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/files")
public class FileController {

    @Autowired DataService dataService;
    @Autowired PDFService pdfService;

    @PostMapping("/uploadProducts")
    public void handleFileUploadToPsql(
            @RequestParam("upload") MultipartFile multipartFile,
            RedirectAttributes redirectAttributes) {
        processFile(multipartFile);
    }

    @GetMapping(value = "/menu/{menuId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] generateMenu(@PathVariable("menuId") Long menuId) throws IOException {

        File file = pdfService.generateMenu(menuId);
        final InputStream targetStream = new DataInputStream(new FileInputStream(file));
        return IOUtils.toByteArray(targetStream);
    }

    private void processFile(MultipartFile multipartFile) {

        File file = new File("src/main/resources/ProductData/tmpData.xlsx");

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Reader<ProductExcel> reader = Reader.create(ProductExcel.class);
        List<ProductExcel> productExcelList = reader
                .from(file)
                .start(1)
                .asList();

        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        dataService.saveProducts(productExcelList);
    }

}
