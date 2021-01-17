package com.gmail.roadtojob2019.onlinestore.repository;

import com.gmail.roadtojob2019.onlinestore.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "DELETE FROM Comment c WHERE c.user.id IN :usersIds")
    @Modifying
    void deleteCommentsByUsersIds(@Param(value = "usersIds") Collection<Long> usersIds);

}
