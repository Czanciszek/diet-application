package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.service.PsqlDataService;
import io.github.biezhi.excel.plus.Reader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("api/")
public class FileController {

    private final PsqlDataService psqlDataService;

    public FileController(PsqlDataService psqlDataService) {
        this.psqlDataService = psqlDataService;
    }

    @PostMapping("/psql/files/uploadProducts")
    public void handleFileUploadToPsql(@RequestParam("upload") MultipartFile multipartFile,
                                   RedirectAttributes redirectAttributes) {
        processFile(multipartFile);
    }

    void processFile(MultipartFile multipartFile) {

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

        psqlDataService.saveProducts(productExcelList);
    }

}
