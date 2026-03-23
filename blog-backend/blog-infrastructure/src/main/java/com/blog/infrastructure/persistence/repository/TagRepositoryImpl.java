package com.blog.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.domain.article.entity.Tag;
import com.blog.domain.article.repository.TagRepository;
import com.blog.infrastructure.persistence.entity.TagDO;
import com.blog.infrastructure.persistence.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    private final TagMapper tagMapper;

    @Override
    public Tag save(Tag tag) {
        TagDO tagDO = new TagDO();
        BeanUtils.copyProperties(tag, tagDO);
        if (tagDO.getId() == null) {
            tagMapper.insert(tagDO);
        } else {
            tagMapper.updateById(tagDO);
        }
        BeanUtils.copyProperties(tagDO, tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        TagDO tagDO = tagMapper.selectById(id);
        if (tagDO == null) {
            return Optional.empty();
        }
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagDO, tag);
        return Optional.of(tag);
    }

    @Override
    public Optional<Tag> findBySlug(String slug) {
        LambdaQueryWrapper<TagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TagDO::getSlug, slug);
        TagDO tagDO = tagMapper.selectOne(wrapper);
        if (tagDO == null) {
            return Optional.empty();
        }
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagDO, tag);
        return Optional.of(tag);
    }

    @Override
    public List<Tag> findAll() {
        LambdaQueryWrapper<TagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TagDO::getArticleCount);
        List<TagDO> tagDOList = tagMapper.selectList(wrapper);
        return tagDOList.stream()
                .map(this::toTag)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        tagMapper.deleteById(id);
    }

    private Tag toTag(TagDO tagDO) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagDO, tag);
        return tag;
    }
}
