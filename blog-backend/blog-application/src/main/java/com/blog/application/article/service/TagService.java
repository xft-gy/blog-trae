package com.blog.application.article.service;

import com.blog.application.article.dto.CreateTagRequest;
import com.blog.application.article.dto.TagDTO;
import com.blog.common.exception.BusinessException;
import com.blog.domain.article.entity.Tag;
import com.blog.domain.article.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<TagDTO> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TagDTO getTagById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Tag not found"));
        return toDTO(tag);
    }

    public TagDTO getTagBySlug(String slug) {
        Tag tag = tagRepository.findBySlug(slug)
                .orElseThrow(() -> new BusinessException("Tag not found"));
        return toDTO(tag);
    }

    @Transactional
    public TagDTO createTag(CreateTagRequest request) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(request, tag);
        tag.setArticleCount(0);
        Tag savedTag = tagRepository.save(tag);
        return toDTO(savedTag);
    }

    @Transactional
    public TagDTO updateTag(Long id, CreateTagRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Tag not found"));
        BeanUtils.copyProperties(request, tag, "id", "articleCount", "createdTime");
        Tag updatedTag = tagRepository.save(tag);
        return toDTO(updatedTag);
    }

    @Transactional
    public void deleteTag(Long id) {
        tagRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Tag not found"));
        tagRepository.deleteById(id);
    }

    private TagDTO toDTO(Tag tag) {
        TagDTO dto = new TagDTO();
        BeanUtils.copyProperties(tag, dto);
        return dto;
    }
}
