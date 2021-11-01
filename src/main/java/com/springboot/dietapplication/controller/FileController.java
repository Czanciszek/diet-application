package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.service.DataService;
import io.github.biezhi.excel.plus.Reader;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("api")
public class FileController {

    @Autowired
    DataService dataService;

    @PostMapping("/psql/files/uploadProducts")
    public void handleFileUploadToPsql(@RequestParam("upload") MultipartFile multipartFile,
                                   RedirectAttributes redirectAttributes) {
        processFile(multipartFile);
    }

    @GetMapping(value = "/get-file", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] getFile() throws IOException {
        File file = importFile("dummy.pdf");
        final InputStream targetStream = new DataInputStream(new FileInputStream(file));
        return IOUtils.toByteArray(targetStream);
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

        dataService.saveProducts(productExcelList);
    }

    private File importFile(String filePath) {
        try {
            URL url = getClass().getClassLoader().getResource(filePath);
            assert url != null;

            return Paths.get(url.toURI()).toFile();
        } catch (IllegalArgumentException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new File(filePath);
    }

}
