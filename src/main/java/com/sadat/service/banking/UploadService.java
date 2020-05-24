package com.sadat.service.banking;

import com.sadat.dto.UploadRequest;
import com.sadat.dto.UploadResponse;
import com.sadat.model.Upload;
import java.util.List;

public interface UploadService {

    List<Upload> getUploads();
    UploadResponse getUploadByCustomerId(long id);
    void updateUpload(long id, UploadRequest request);
}
