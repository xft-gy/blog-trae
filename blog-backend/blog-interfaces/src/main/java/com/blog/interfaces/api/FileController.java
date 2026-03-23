package com.blog.interfaces.api;

import com.blog.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Tag(name = "File", description = "File upload APIs")
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    @Value("${file.base-url:http://localhost:8080}")
    private String baseUrl;

    @Operation(summary = "Upload file")
    @PostMapping("/upload")
    public Result<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error(400, "File is empty");
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null ? 
                originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            
            String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String newFilename = UUID.randomUUID().toString() + extension;
            
            String relativePath = datePath + "/" + newFilename;
            Path fullPath = Paths.get(uploadDir, relativePath);
            
            Files.createDirectories(fullPath.getParent());
            file.transferTo(fullPath.toFile());
            
            String fileUrl = baseUrl + "/uploads/" + relativePath;
            
            Map<String, String> result = new HashMap<>();
            result.put("url", fileUrl);
            result.put("filename", originalFilename);
            result.put("path", relativePath);
            
            return Result.success(result);
        } catch (IOException e) {
            return Result.error(500, "File upload failed: " + e.getMessage());
        }
    }

    @Operation(summary = "Upload image")
    @PostMapping("/upload/image")
    public Result<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return Result.error(400, "Only image files are allowed");
        }
        
        long maxSize = 5 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            return Result.error(400, "Image size must be less than 5MB");
        }
        
        return uploadFile(file);
    }
}
