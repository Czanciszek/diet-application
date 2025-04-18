package com.springboot.dietapplication;

import com.springboot.dietapplication.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class DietApplication {

    public static void main(String[] args) {
        SpringApplication.run(DietApplication.class, args);
    }

    @Autowired
    MongoProductRunner mongoProductRunner;

    @Autowired
    MongoDishRunner mongoDishRunner;

    @Autowired
    MongoPatientRunner mongoPatientRunner;

    @Autowired
    MongoMenuRunner mongoMenuRunner;

    @Autowired
    MongoWeekMenuRunner mongoWeekMenuRunner;

    @Autowired
    MongoUserRunner mongoUserRunner;

    @Autowired
    MongoClinicRunner mongoClinicRunner;

    @Autowired
    MongoEmployeeRunner mongoEmployeeRunner;

    @Bean
    CommandLineRunner runner() {
        return args -> {
            try {

//                mongoProductRunner.reloadProductsPSQLtoMongo();
//
//                mongoDishRunner.reloadDishesPSQLtoMongo();
//
//                mongoPatientRunner.reloadPatientsPSQLtoMongo();
//
//                mongoMenuRunner.reloadMenusPSQLtoMongo();
//
//                mongoWeekMenuRunner.reloadMenusPSQLtoMongo();
//
//                mongoUserRunner.reloadUsersPSQLToMongo();
//
//                mongoClinicRunner.updatePatients();
//
//                mongoEmployeeRunner.createEmployees();
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
    }

}
