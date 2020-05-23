package com.sadat.repository;

import com.sadat.model.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface UploadRepository extends JpaRepository<Upload, Long> {

    Upload findByCustomer_Id(Long id);

    @Modifying
    @Query("UPDATE Upload u SET u.nid = :nid, u.picture = :picture WHERE u.customer.id = :id")
    void update(Long id, byte[] nid, byte[] picture);
}
