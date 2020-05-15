package com.sadat.controller;

import com.sadat.dto.UploadRequest;
import com.sadat.service.banking.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {

    private UploadService uploadService;

    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody UploadRequest request){

        if(uploadService.saveUpload(request) != null){

            return ResponseEntity.ok("File Uploaded Successfully!");
        }

        return ResponseEntity.badRequest().build();
    }
}
