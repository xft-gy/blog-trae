package com.blog.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.domain.article.entity.Category;
import com.blog.domain.article.repository.CategoryRepository;
import com.blog.infrastructure.persistence.entity.CategoryDO;
import com.blog.infrastructure.persistence.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryMapper categoryMapper;

    @Override
    public Category save(Category category) {
        CategoryDO categoryDO = new CategoryDO();
        BeanUtils.copyProperties(category, categoryDO);
        if (categoryDO.getId() == null) {
            categoryMapper.insert(categoryDO);
        } else {
            categoryMapper.updateById(categoryDO);
        }
        BeanUtils.copyProperties(categoryDO, category);
        return category;
    }

    @Override
    public Optional<Category> findById(Long id) {
        CategoryDO categoryDO = categoryMapper.selectById(id);
        if (categoryDO == null) {
            return Optional.empty();
        }
        Category category = new Category();
        BeanUtils.copyProperties(categoryDO, category);
        return Optional.of(category);
    }

    @Override
    public Optional<Category> findBySlug(String slug) {
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryDO::getSlug, slug);
        CategoryDO categoryDO = categoryMapper.selectOne(wrapper);
        if (categoryDO == null) {
            return Optional.empty();
        }
        Category category = new Category();
        BeanUtils.copyProperties(categoryDO, category);
        return Optional.of(category);
    }

    @Override
    public List<Category> findAll() {
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(CategoryDO::getSortOrder);
        List<CategoryDO> categoryDOList = categoryMapper.selectList(wrapper);
        return categoryDOList.stream()
                .map(this::toCategory)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }

    private Category toCategory(CategoryDO categoryDO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDO, category);
        return category;
    }
}
