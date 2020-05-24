package com.sadat.controller;

import com.sadat.dto.UploadRequest;
import com.sadat.dto.UploadResponse;
import com.sadat.service.banking.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/uploads")
@Slf4j
public class UploadController {

    private Logger logger = LoggerFactory.getLogger(UploadController.class);
    private UploadService uploadService;

    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UploadResponse> getUpload(@PathVariable("id") long id){

        UploadResponse response = uploadService.getUploadByCustomerId(id);

        if(response != null){

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id,
                                    @RequestParam("nid") MultipartFile nidFile,
                                    @RequestParam("picture") MultipartFile pictureFile){

        if(uploadService.getUploadByCustomerId(id) != null){

            try{
                UploadRequest request = UploadRequest.builder()
                        .nid(nidFile.getBytes())
                        .picture(pictureFile.getBytes())
                        .build();

                uploadService.updateUpload(id, request);

                return ResponseEntity.noContent().build();
            }
            catch(IOException e){
                logger.error(e.getMessage());
            }
        }

        return ResponseEntity.notFound().build();
    }
}
