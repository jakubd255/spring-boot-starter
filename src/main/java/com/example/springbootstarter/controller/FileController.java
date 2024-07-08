package com.example.springbootstarter.controller;

import com.example.springbootstarter.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(fileService.uploadFile(file));
    }

    @GetMapping("/{name}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String name) throws MalformedURLException {
        Resource file = fileService.downloadFile(name);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteFile(@PathVariable String name) throws IOException {
        fileService.deleteFile(name);
        return ResponseEntity.ok("Successfully deleted file");
    }
}