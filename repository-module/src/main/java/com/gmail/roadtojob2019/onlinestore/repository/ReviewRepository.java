package com.gmail.roadtojob2019.onlinestore.repository;

import com.gmail.roadtojob2019.onlinestore.repository.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "DELETE FROM Review r WHERE r.id IN :reviewsIds")
    @Modifying
    void deleteReviewsByIds(@Param(value = "reviewsIds") Collection<Long> reviewsIds);

    @Query(value = "DELETE FROM Review r WHERE r.user.id IN :usersIds")
    @Modifying
    void deleteReviewsByUsersIds(@Param(value = "usersIds") Collection<Long> usersIds);
}
