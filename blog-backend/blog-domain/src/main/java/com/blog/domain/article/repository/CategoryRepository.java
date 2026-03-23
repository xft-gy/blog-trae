package com.blog.domain.article.repository;

import com.blog.domain.article.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Category save(Category category);

    Optional<Category> findById(Long id);

    Optional<Category> findBySlug(String slug);

    List<Category> findAll();

    void deleteById(Long id);
}
