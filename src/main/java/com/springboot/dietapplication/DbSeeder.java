package com.springboot.dietapplication;

import com.springboot.dietapplication.model.base.DocRef;
import com.springboot.dietapplication.model.base.Header;
import com.springboot.dietapplication.model.dish.Dish;
import com.springboot.dietapplication.model.product.AmountType;
import com.springboot.dietapplication.model.product.ProductForDish;
import com.springboot.dietapplication.model.product.Product;
import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.user.User;
import com.springboot.dietapplication.repository.DishRepository;
import com.springboot.dietapplication.repository.ProductRepository;
import com.springboot.dietapplication.repository.UserRepository;
import io.github.biezhi.excel.plus.Reader;
import org.joda.time.DateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {

    private ProductRepository productRepository;
    private DishRepository dishRepository;
    private UserRepository userRepository;

    public DbSeeder(ProductRepository productRepository, DishRepository dishRepository,
                    UserRepository userRepository) {
        this.productRepository = productRepository;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {

        this.productRepository.deleteAll();
        this.dishRepository.deleteAll();
        this.userRepository.deleteAll();

        User user1 = new User("aaa",
                "$2y$12$xQyJdsoamI/19a4p3bgRcOj2KeLpxPWj3.whkTrjz2jzIbO9fnr6m", "aaa");
        userRepository.save(user1);

        User user = new User("name", "password", "imageId");
        Header header = new Header();
        header.setCreatedBy(DocRef.fromDoc(user));

        List<ProductExcel> importedProducts = new ArrayList<>();

        List<String> fileImportList = Arrays.asList(
                "ProductData/Stage5.xlsx",
                "ProductData/Stage8.xlsx");

        for (String filePath : fileImportList) {
            try {
                URL url = getClass().getClassLoader().getResource(filePath);
                assert url != null;
                File file = Paths.get(url.toURI()).toFile();

                importedProducts = Reader.create(ProductExcel.class)
                        .from(file)
                        .start(1)
                        .asList();
            } catch (IllegalArgumentException e) {
                System.out.println("File not found");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<Product> products = new ArrayList<>();
            for (ProductExcel productExcel : importedProducts) {
                Product product = new Product(productExcel);
                long currentTime = new DateTime().getMillis();
                product.setHeader(header);
                product.getHeader().setCreatedEpochMillis(currentTime);
                products.add(product);
            }

            this.productRepository.saveAll(products);
        }

        Product cukier = this.productRepository.findByNameLike("Cukier");
        Product karmelki = this.productRepository.findByNameLike("Karmelki nadziewane");

        List<ProductForDish> productsToAdd = new ArrayList<>();
        ProductForDish productForDish = new ProductForDish(DocRef.fromDoc(cukier), 12.0f, AmountType.PIECE);
        ProductForDish productForDish2 = new ProductForDish(DocRef.fromDoc(karmelki), 0.3f, AmountType.G);

        productsToAdd.add(productForDish);
        productsToAdd.add(productForDish2);

        Dish dish = new Dish(productsToAdd);
        dish.setName("Karmelki cukrowane");
        dish.setPrimaryImageId("Zdjecie karmelkow");
        long currentTime = new DateTime().getMillis();
        dish.setHeader(header);
        dish.getHeader().setCreatedEpochMillis(currentTime);
        dish.setPortions(2.5f);
        dish.setRecipe("Połącz karmelki z cukrem");
        this.dishRepository.save(dish);

        System.out.println(dish.getId());
    }
}
