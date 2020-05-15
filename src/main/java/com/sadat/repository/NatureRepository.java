package com.sadat.repository;

import com.sadat.model.Nature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface NatureRepository extends JpaRepository<Nature, Long> {
}
