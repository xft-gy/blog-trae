package com.blog.domain.article.repository;

import com.blog.domain.article.entity.Tag;
import java.util.List;
import java.util.Optional;

public interface TagRepository {

    Tag save(Tag tag);

    Optional<Tag> findById(Long id);

    Optional<Tag> findBySlug(String slug);

    List<Tag> findAll();

    void deleteById(Long id);
}
