package com.blog.interfaces.api;

import com.blog.application.article.dto.CreateTagRequest;
import com.blog.application.article.dto.TagDTO;
import com.blog.application.article.service.TagService;
import com.blog.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tag", description = "Tag management APIs")
@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @Operation(summary = "Get all tags")
    @GetMapping
    public Result<List<TagDTO>> getAllTags() {
        List<TagDTO> tags = tagService.getAllTags();
        return Result.success(tags);
    }

    @Operation(summary = "Get tag by ID")
    @GetMapping("/{id}")
    public Result<TagDTO> getTagById(@PathVariable Long id) {
        TagDTO tag = tagService.getTagById(id);
        return Result.success(tag);
    }

    @Operation(summary = "Get tag by slug")
    @GetMapping("/slug/{slug}")
    public Result<TagDTO> getTagBySlug(@PathVariable String slug) {
        TagDTO tag = tagService.getTagBySlug(slug);
        return Result.success(tag);
    }

    @Operation(summary = "Create tag")
    @PostMapping
    public Result<TagDTO> createTag(@Valid @RequestBody CreateTagRequest request) {
        TagDTO tag = tagService.createTag(request);
        return Result.success(tag);
    }

    @Operation(summary = "Update tag")
    @PutMapping("/{id}")
    public Result<TagDTO> updateTag(
            @PathVariable Long id,
            @Valid @RequestBody CreateTagRequest request) {
        TagDTO tag = tagService.updateTag(id, request);
        return Result.success(tag);
    }

    @Operation(summary = "Delete tag")
    @DeleteMapping("/{id}")
    public Result<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return Result.success();
    }
}
