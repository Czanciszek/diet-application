package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.mongo.menu.MongoMenu;
import com.springboot.dietapplication.model.mongo.menu.MongoWeekMenu;
import com.springboot.dietapplication.model.type.WeekMenuType;
import com.springboot.dietapplication.repository.mongo.MongoMenuRepository;
import com.springboot.dietapplication.repository.mongo.MongoWeekMenuRepository;
import com.springboot.dietapplication.utils.DateFormatter;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeekMealServiceV2 {

    @Autowired
    MongoWeekMenuRepository mongoWeekMenuRepository;

    @Autowired
    MongoMenuRepository mongoMenuRepository;

    public List<WeekMenuType> getWeekMealsByMenuId(String menuId) {
        List<MongoWeekMenu> weekMealList = this.mongoWeekMenuRepository.findByMenuId(menuId);

        return weekMealList
                .stream()
                .filter(p -> StringUtils.isEmpty(p.getDeletionDate()))
                .map(WeekMenuType::new)
                .collect(Collectors.toList());
    }

    public void delete(String id) {

        MongoWeekMenu mongoWeekMenu = mongoWeekMenuRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Week menu does not exist"));

        // TODO: Allow delete menu only for authorized users
//        UserEntity user = userDetailsService.getCurrentUser();
//        if (!user.getUserType().equals(UserType.ADMIN.name) && !product.get().getUserId().equals(user.getId()))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized deleting product attempt");

        DateFormat dateFormat = DateFormatter.getInstance().getIso8601Formatter();
        String currentDate = dateFormat.format(new Date());
        mongoWeekMenu.setDeletionDate(currentDate);

        mongoWeekMenuRepository.save(mongoWeekMenu);

        // Update Menu reference
        String menuId = mongoWeekMenu.getMenu().getId();
        Optional<MongoMenu> mongoMenu = mongoMenuRepository.findById(menuId);
        if (mongoMenu.isEmpty()) return;
        mongoMenu.get().getWeekMenus().removeIf(weekMenu -> weekMenu.equals(id));
        mongoMenu.get().setUpdateDate(currentDate);
        mongoMenuRepository.save(mongoMenu.get());
    }

}
