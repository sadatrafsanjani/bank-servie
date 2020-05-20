package com.sadat.repository;

import com.sadat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.email = :email WHERE u.id = :id")
    void updateEmail(String email, Long id);

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
    void updatePassword(String password, Long id);

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.email = :email")
    void resetPassword(String password, String email);

    @Modifying
    @Query("UPDATE User u SET u.status = TRUE WHERE u.id = :id")
    void activateUser(Long id);

    @Modifying
    @Query("UPDATE User u SET u.status = FALSE WHERE u.id = :id")
    void deactivateUser(Long id);
}
