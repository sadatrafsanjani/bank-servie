package com.sadat.service.banking;

import com.sadat.dto.UploadRequest;
import com.sadat.model.Upload;
import com.sadat.repository.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {

    private UploadRepository uploadRepository;

    @Autowired
    public UploadServiceImpl(UploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    @Override
    public List<Upload> getUploads(){

        return uploadRepository.findAll();
    }

    @Override
    public Upload getUpload(long id){

        return uploadRepository.getOne(id);
    }

    @Override
    public Upload getByCustomerId(long id){

        return uploadRepository.findByCustomer_Id(id);
    }

    @Override
    public void updateUpload(long id, UploadRequest request) {

        Upload upload = new Upload();
        upload.setNid(request.getNid());
        upload.setPicture(request.getPicture());

        uploadRepository.update(id, request.getNid(), request.getPicture());
    }
}
