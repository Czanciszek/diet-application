package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.mongo.user.MongoUserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoUserEntityRepository extends MongoRepository<MongoUserEntity, String> {

    MongoUserEntity findByName(String userName);
}