package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.mongo.dish.MongoDish;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDishRepository extends MongoRepository<MongoDish, String> {

}
