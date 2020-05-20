package com.sadat.repository;

import com.sadat.model.Menu;
import com.sadat.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Transactional
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(String role);

    @Modifying
    @Query("UPDATE Role r SET r.menus = :menus WHERE r.id = :id")
    void updateMenu(long id, Set<Menu> menus);
}
