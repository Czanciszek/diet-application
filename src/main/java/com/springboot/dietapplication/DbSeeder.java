package com.springboot.dietapplication;

import com.springboot.dietapplication.Model.Base.DocRef;
import com.springboot.dietapplication.Model.Base.Header;
import com.springboot.dietapplication.Model.Dish.Dish;
import com.springboot.dietapplication.Model.Product.AmountType;
import com.springboot.dietapplication.Model.Product.ProductForDish;
import com.springboot.dietapplication.Model.Product.Product;
import com.springboot.dietapplication.Model.Excel.ProductExcel;
import com.springboot.dietapplication.Model.User.User;
import com.springboot.dietapplication.MongoRepository.DishRepository;
import com.springboot.dietapplication.MongoRepository.ProductRepository;
import io.github.biezhi.excel.plus.Reader;
import org.joda.time.DateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {

    private ProductRepository productRepository;
    private DishRepository dishRepository;

    public DbSeeder(ProductRepository productRepository, DishRepository dishRepository) {
        this.productRepository = productRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    public void run(String... args) {
        List<ProductExcel> importedProducts = new ArrayList<>();

        try {
            URL url = getClass().getClassLoader().getResource("ProductData/Stage8.xlsx");
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

        User user = new User("id", "name", "password", "imageId");
        Header header = new Header();
        header.setCreatedBy(DocRef.fromDoc(user));

        List<Product> products = new ArrayList<>();
        for (ProductExcel productExcel : importedProducts) {
            Product product = new Product(productExcel);
            long currentTime = new DateTime().getMillis();
            product.setHeader(header);
            product.getHeader().setCreatedEpochMillis(currentTime);
            products.add(product);
        }

        this.productRepository.deleteAll();
        this.dishRepository.deleteAll();

        this.productRepository.saveAll(products);

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
