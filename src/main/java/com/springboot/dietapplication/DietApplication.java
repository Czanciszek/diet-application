package com.springboot.dietapplication;

import com.springboot.dietapplication.utils.MongoDishRunner;
import com.springboot.dietapplication.utils.MongoPatientRunner;
import com.springboot.dietapplication.utils.MongoProductRunner;
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

    @Bean
    CommandLineRunner runner() {
        return args -> {
            try {

//                mongoProductRunner.reloadProductsPSQLtoMongo();

//                mongoDishRunner.reloadDishesPSQLtoMongo();

//                mongoPatientRunner.reloadPatientsPSQLtoMongo();

            } catch (Exception e) {
                e.printStackTrace();
            }

        };
    }

}
