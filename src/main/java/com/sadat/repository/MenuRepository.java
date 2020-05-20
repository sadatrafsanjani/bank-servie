package com.sadat.repository;

import com.sadat.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Menu findByUserInterface(String userInterface);

    @Query("SELECT m FROM Menu m WHERE m.id > 4")
    List<Menu> findUserMenus();
}
