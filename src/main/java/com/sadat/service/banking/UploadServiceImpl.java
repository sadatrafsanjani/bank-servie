package com.sadat.service.banking;

import com.sadat.dto.UploadRequest;
import com.sadat.dto.UploadResponse;
import com.sadat.model.Upload;
import com.sadat.repository.UploadRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Slf4j
@Service
public class UploadServiceImpl implements UploadService {

    private Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);
    private UploadRepository uploadRepository;

    @Autowired
    public UploadServiceImpl(UploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    @Override
    public boolean checkCustomer(long id){

        return uploadRepository.findByCustomer_Id(id) != null;
    }

    @Override
    public List<Upload> getUploads(){

        return uploadRepository.findAll();
    }

    @Override
    public UploadResponse getUploadByCustomerId(long id){

        Upload upload = uploadRepository.findByCustomer_Id(id);

        if(upload != null){

            return UploadResponse.builder()
                    .nid(upload.getNid() != null? decompressBytes(upload.getNid()) : null)
                    .picture(upload.getPicture() != null ? decompressBytes(upload.getPicture()) : null)
                    .build();
        }

        return null;
    }

    @Override
    public void updateUpload(long id, UploadRequest request) {

        Upload upload = new Upload();
        upload.setNid(compressBytes(request.getNid()));
        upload.setPicture(compressBytes(request.getPicture()));

        uploadRepository.update(id, upload.getNid(), upload.getPicture());
    }

    @Override
    public void updateNid(long id, UploadRequest request) {

        Upload upload = new Upload();
        upload.setNid(compressBytes(request.getNid()));

        uploadRepository.updateNid(id, upload.getNid());
    }

    @Override
    public void updatePicture(long id, UploadRequest request) {

        Upload upload = new Upload();
        upload.setPicture(compressBytes(request.getPicture()));

        uploadRepository.updatePicture(id, upload.getPicture());
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

    private byte[] decompressBytes(byte[] data){

        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];

        try{
            while(!inflater.finished()){

                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        }
        catch(IOException | DataFormatException e){
            logger.error(e.getMessage());
        }

        return outputStream.toByteArray();
    }
}
