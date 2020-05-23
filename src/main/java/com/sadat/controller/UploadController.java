package com.sadat.controller;

import com.sadat.dto.UploadRequest;
import com.sadat.service.banking.CustomerService;
import com.sadat.service.banking.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {

    private UploadService uploadService;
    private CustomerService customerService;

    @Autowired
    public UploadController(UploadService uploadService,
                            CustomerService customerService) {
        this.uploadService = uploadService;
        this.customerService = customerService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id")long id, @Valid @RequestBody UploadRequest request){

        if((customerService.getCustomer(id) != null) && (uploadService.getByCustomerId(id) != null)){

            uploadService.updateUpload(id, request);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
