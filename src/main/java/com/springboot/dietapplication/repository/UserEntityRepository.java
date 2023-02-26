package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    String selectUserQuery = "SELECT " +
            "u.id, ut.name AS user_type, u.name, email, password, image_id, pdf_footer " +
            "FROM users u " +
            "JOIN user_types ut ON u.user_type_id = ut.id ";

    @Query(value = selectUserQuery +
            "WHERE u.name = :userName", nativeQuery = true)
    UserEntity findByName(String userName);
}