package com.sadat.service.banking;

import com.sadat.dto.UploadRequest;
import com.sadat.model.Upload;
import java.util.List;

public interface UploadService {

    List<Upload> getUploads();
    Upload getUpload(long id);
    Upload getByCustomerId(long id);
    void updateUpload(long id, UploadRequest request);
}
