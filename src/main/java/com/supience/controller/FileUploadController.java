package com.supience.controller;

import com.supience.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/upload")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/")
    public ResponseEntity<Map<String, String>> uploadFile(
            @RequestParam("file") MultipartFile file) {
        String fileUrl = fileUploadService.uploadFile(file);
        return ResponseEntity.ok(Map.of("fileUrl", fileUrl));
    }

    @PostMapping("/multiple")
    public ResponseEntity<Map<String, List<String>>> uploadMultipleFiles(
            @RequestParam("files") List<MultipartFile> files) {
        List<String> fileUrls = fileUploadService.uploadMultipleFiles(files);
        return ResponseEntity.ok(Map.of("fileUrls", fileUrls));
    }
} 