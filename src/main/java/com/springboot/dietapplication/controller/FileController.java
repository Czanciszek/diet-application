package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.excel.ProductReplacementsExcel;
import com.springboot.dietapplication.model.type.GenerateMenuType;
import com.springboot.dietapplication.service.*;
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

    @Autowired
    DataService dataService;

    @Autowired
    PDFServiceV2 pdfService;

    @Autowired
    MenuServiceV2 menuService;

    @PostMapping("/uploadProducts")
    public void handleFileUploadToPsql(
            @RequestParam("upload") MultipartFile multipartFile,
            RedirectAttributes redirectAttributes) {
        processFile(multipartFile);
    }

    @PostMapping("/uploadProductReplacements")
    public void handleFileUploadReplacements(
            @RequestParam("upload") MultipartFile multipartFile,
            RedirectAttributes redirectAttributes) {
        processFileReplacements(multipartFile);
    }

    @PostMapping(value = "/menu", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] generateMenu(@RequestBody GenerateMenuType generateMenuType) throws IOException {

        // TODO: Add check whenever user wants to override recommendations
        menuService.updateMenuRecommendations(generateMenuType);

        File file = pdfService.generateMenu(generateMenuType);
        final InputStream targetStream = new DataInputStream(new FileInputStream(file));
        return IOUtils.toByteArray(targetStream);
    }

    private void processFile(MultipartFile multipartFile) {


    }

    private void processFileReplacements(MultipartFile multipartFile) {

        File file = new File("src/main/resources/ProductData/tmpData.xlsx");

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Reader<ProductReplacementsExcel> reader = Reader.create(ProductReplacementsExcel.class);
        List<ProductReplacementsExcel> productExcelList = reader
                .from(file)
                .start(1)
                .asList();

        try {
            boolean delete = file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        dataService.updateProductReplacements(productExcelList);
    }

}
