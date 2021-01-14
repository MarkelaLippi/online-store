package com.gmail.roadtojob2019.onlinestore.repository;

import com.gmail.roadtojob2019.onlinestore.repository.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
