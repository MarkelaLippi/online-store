package com.gmail.roadtojob2019.onlinestore.repository;

import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "DELETE FROM User u WHERE u.id IN :ids")
    @Modifying
    void deleteUsersByIds(@Param(value = "ids") Collection<Long> usersIds);

    User findUserByEmail(String email);
}
