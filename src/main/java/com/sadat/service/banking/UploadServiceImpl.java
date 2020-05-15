package com.sadat.service.banking;

import com.sadat.dto.UploadRequest;
import com.sadat.model.Upload;
import com.sadat.repository.CustomerRepository;
import com.sadat.repository.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {

    private UploadRepository uploadRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public UploadServiceImpl(UploadRepository uploadRepository,
                             CustomerRepository customerRepository) {
        this.uploadRepository = uploadRepository;
        this.customerRepository = customerRepository;
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
    public Upload saveUpload(UploadRequest request) {

        Upload upload = new Upload();
        upload.setCustomer(customerRepository.getOne(request.getCustomerId()));
        upload.setNid(request.getNid());
        upload.setPicture(request.getPicture());

        return uploadRepository.save(upload);
    }
}
