package com.springboot.dietapplication.utils;

import com.springboot.dietapplication.model.mongo.user.MongoUserEntity;
import com.springboot.dietapplication.model.psql.user.PsqlUser;
import com.springboot.dietapplication.model.psql.user.PsqlUserType;
import com.springboot.dietapplication.repository.UserRepository;
import com.springboot.dietapplication.repository.UserTypeRepository;
import com.springboot.dietapplication.repository.mongo.MongoUserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MongoUserRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTypeRepository userTypeRepository;

    @Autowired
    MongoUserEntityRepository mongoUserEntityRepository;

    public void reloadUsersPSQLToMongo() {

        mongoUserEntityRepository.deleteAll();

        List<PsqlUser> userEntities = userRepository.findAll();

        List<PsqlUserType> userTypes = userTypeRepository.findAll();

        List<MongoUserEntity> mongoUserEntities = userEntities.stream()
                .map(psqlUser -> {
                    MongoUserEntity mongoUserEntity = new MongoUserEntity(psqlUser);

                    Optional<String> optionalType = userTypes.stream()
                            .map(PsqlUserType::getName)
                            .findFirst();
                    optionalType.ifPresent(mongoUserEntity::setUserType);

                    return mongoUserEntity;
                })
                .collect(Collectors.toList());

        mongoUserEntityRepository.saveAll(mongoUserEntities);
    }
}
