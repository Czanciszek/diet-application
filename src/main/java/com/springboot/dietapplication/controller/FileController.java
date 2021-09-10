package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.service.mongo.MongoDataService;
import com.springboot.dietapplication.service.psql.PsqlDataService;
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

    private final MongoDataService mongoDataService;
    private final PsqlDataService psqlDataService;

    public FileController(MongoDataService mongoDataService, PsqlDataService psqlDataService) {
        this.mongoDataService = mongoDataService;
        this.psqlDataService = psqlDataService;
    }

    @PostMapping("/mongo/files/uploadProducts")
    public void handleFileUploadToMongo(@RequestParam("upload") MultipartFile multipartFile,
                                 RedirectAttributes redirectAttributes) {
        processFile(multipartFile, "mongo");
    }

    @PostMapping("/psql/files/uploadProducts")
    public void handleFileUploadToPsql(@RequestParam("upload") MultipartFile multipartFile,
                                   RedirectAttributes redirectAttributes) {
        processFile(multipartFile, "psql");
    }

    void processFile(MultipartFile multipartFile, String type) {

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
            e.printStackTrace();;
        }

        if (type.equals("mongo")) {
            mongoDataService.saveProducts(productExcelList);
        } else if (type.equals("psql")) {
            psqlDataService.saveProducts(productExcelList);
        }

    }

}
