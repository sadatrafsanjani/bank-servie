package com.sadat.repository;

import com.sadat.model.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface UploadRepository extends JpaRepository<Upload, Long> {

    Upload findByCustomer_Id(Long id);
}
