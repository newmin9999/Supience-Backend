package com.supience.controller;

import com.supience.dto.ApiResponse;
import com.supience.dto.FileUploadResponse;
import com.supience.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/upload")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<FileUploadResponse>> uploadFile(
            @RequestParam("file") MultipartFile file) {
        String fileUrl = fileUploadService.uploadFile(file);
        FileUploadResponse response = FileUploadResponse.builder()
                .fileName(file.getOriginalFilename())
                .fileUrl(fileUrl)
                .fileType(file.getContentType())
                .size(file.getSize())
                .build();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/multiple")
    public ResponseEntity<ApiResponse<List<FileUploadResponse>>> uploadMultipleFiles(
            @RequestParam("files") List<MultipartFile> files) {
        List<String> fileUrls = fileUploadService.uploadMultipleFiles(files);
        List<FileUploadResponse> responses = files.stream()
                .map(file -> FileUploadResponse.builder()
                        .fileName(file.getOriginalFilename())
                        .fileUrl(fileUrls.get(files.indexOf(file)))
                        .fileType(file.getContentType())
                        .size(file.getSize())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses));
    }
} 