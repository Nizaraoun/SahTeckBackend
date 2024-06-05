package com.nizar.SahTech.mobileapp.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileController {

    private final String rootDir = "E:/PfeProject/BackendSahTech/SahTech/uploads/";

    @GetMapping("/files/{role}/{documentName}/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String role,
                                                 @PathVariable String documentName,
                                                 @PathVariable String filename) {
        return downloadFileInternal(role, documentName, null, filename);
    }

    @GetMapping("/files/{role}/{documentName}/{documentName2}/{filename:.+}")
    public ResponseEntity<Resource> downloadFileWithTwoDocumentNames(@PathVariable String role,
                                                                     @PathVariable String documentName,
                                                                     @PathVariable String documentName2,
                                                                     @PathVariable String filename) {
        return downloadFileInternal(role, documentName, documentName2, filename);
    }

    private ResponseEntity<Resource> downloadFileInternal(String role,
                                                          String documentName,
                                                          String documentName2,
                                                          String filename) {
        try {
            Path filePath;
            if (documentName2 != null) {
                filePath = Paths.get(rootDir).resolve(role).resolve(documentName).resolve(documentName2).resolve(filename).normalize();
            } else {
                filePath = Paths.get(rootDir).resolve(role).resolve(documentName).resolve(filename).normalize();
            }
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("File not found " + filename);
            }
        } catch (Exception e) {
            throw new RuntimeException("File not found " + filename, e);
        }
    }
}
