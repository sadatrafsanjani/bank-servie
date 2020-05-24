package com.sadat.controller;

import com.sadat.dto.UploadRequest;
import com.sadat.service.banking.CustomerService;
import com.sadat.service.banking.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;

@RestController
@RequestMapping("/api/uploads")
@Slf4j
public class UploadController {

    private Logger logger = LoggerFactory.getLogger(UploadController.class);
    private UploadService uploadService;
    private CustomerService customerService;

    @Autowired
    public UploadController(UploadService uploadService,
                            CustomerService customerService) {
        this.uploadService = uploadService;
        this.customerService = customerService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id")long id,
                                    @RequestParam("nid") MultipartFile nidFile,
                                    @RequestParam("picture") MultipartFile pictureFile){

        if((customerService.getCustomer(id) != null) && (uploadService.getByCustomerId(id) != null)){

            try{
                UploadRequest request = UploadRequest.builder()
                        .nid(compressBytes(nidFile.getBytes()))
                        .picture(compressBytes(pictureFile.getBytes()))
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

    private byte[] compressBytes(byte[] data) {

        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];

        while(!deflater.finished()){

            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }

        try{
            outputStream.close();
        }
        catch(IOException e){
            logger.error(e.getMessage());
        }

        return outputStream.toByteArray();
    }
}
