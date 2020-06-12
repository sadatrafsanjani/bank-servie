package com.sadat.service.banking;

import com.sadat.dto.general.UploadRequest;
import com.sadat.dto.general.UploadResponse;
import com.sadat.model.Upload;
import com.sadat.repository.UploadRepository;
import com.sadat.utility.Image;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
                    .nid(upload.getNid() != null? Image.decompressBytes(upload.getNid()) : null)
                    .picture(upload.getPicture() != null ? Image.decompressBytes(upload.getPicture()) : null)
                    .build();
        }

        return null;
    }

    @Override
    public void updateUpload(long id, UploadRequest request) {

        uploadRepository.update(id, Image.compressBytes(request.getNid()), Image.compressBytes(request.getPicture()));
    }

    @Override
    public void updateNid(long id, UploadRequest request) {

        uploadRepository.updateNid(id, Image.compressBytes(request.getNid()));
    }

    @Override
    public void updatePicture(long id, UploadRequest request) {

        uploadRepository.updatePicture(id, Image.compressBytes(request.getPicture()));
    }
}
