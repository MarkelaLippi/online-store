package com.gmail.roadtojob2019.onlinestore.repository;

import com.gmail.roadtojob2019.onlinestore.repository.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "DELETE FROM Article a WHERE a.user.id IN :usersIds")
    @Modifying
    void deleteArticlesByUsersIds(@Param(value = "usersIds") Collection<Long> usersIds);

    @Query(value = "SELECT a FROM Article a WHERE a.user.id IN :usersIds")
    List<Article> getArticlesByUserIds(@Param(value = "usersIds") Collection<Long> usersIds);

    @Query(value = "DELETE FROM Article a WHERE a.id IN :articlesIds")
    @Modifying
    void deleteArticlesByIds(@Param(value = "articlesIds") Collection<Long> articlesIds);
}
