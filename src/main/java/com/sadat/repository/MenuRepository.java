package com.sadat.repository;

import com.sadat.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Menu findByUserInterface(String userInterface);
}
