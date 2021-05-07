package com.springboot.dietapplication.service.psql;

import com.springboot.dietapplication.model.type.ProductDishType;
import com.springboot.dietapplication.model.psql.dish.PsqlDish;
import com.springboot.dietapplication.model.psql.dish.PsqlProductDish;
import com.springboot.dietapplication.model.type.DishType;
import com.springboot.dietapplication.repository.psql.PsqlDishRepository;
import com.springboot.dietapplication.repository.psql.PsqlProductDishRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PsqlDishService {

    private final PsqlDishRepository dishRepository;
    private final PsqlProductDishRepository productDishRepository;

    PsqlDishService(PsqlDishRepository dishRepository,
                    PsqlProductDishRepository psqlProductDishRepository) {
        this.dishRepository = dishRepository;
        this.productDishRepository = psqlProductDishRepository;
    }

    public List<DishType> getAll() {
        List<DishType> dishTypeList = new ArrayList<>();

        List<PsqlDish> dishes = this.dishRepository.findAll();
        for (PsqlDish psqlDish : dishes) {
            DishType dishType = new DishType(psqlDish);

            List<ProductDishType> dishTypes = new ArrayList<>();
            List<PsqlProductDish> productDishList =
                    this.productDishRepository.findPsqlProductDishesByDishId(psqlDish.getId());
            for (PsqlProductDish productDish : productDishList) {
                ProductDishType productDishType = new ProductDishType(productDish);
                dishTypes.add(productDishType);
            }

            dishType.setProducts(dishTypes);
            dishTypeList.add(dishType);
        }

        return dishTypeList;
    }

    public DishType getDishById(Long dishId) {
        return new DishType();
    }

    public ResponseEntity<DishType> insert(DishType dish) {
        PsqlDish psqlDish = new PsqlDish(dish);

        this.dishRepository.save(psqlDish);

        if (psqlDish.getId() > 0)
            this.productDishRepository.deletePsqlProductDishesByDishId(psqlDish.getId());

        for (ProductDishType productDish : dish.getProducts()) {
            PsqlProductDish psqlProductDish = new PsqlProductDish(productDish);
            psqlProductDish.setDishId(psqlDish.getId());
            this.productDishRepository.save(psqlProductDish);
        }

        return ResponseEntity.ok().body(null);
    }

    public ResponseEntity<Void> delete(Long id) {
        List<PsqlProductDish> productDishList =
                this.productDishRepository.findPsqlProductDishesByDishId(id);
        this.productDishRepository.deleteAll(productDishList);

        this.dishRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
