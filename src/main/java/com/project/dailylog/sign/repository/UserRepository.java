package com.project.dailylog.sign.repository;

import com.project.dailylog.sign.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.password = :password WHERE u.userId = :userId AND u.email = :email AND u.name = :name")
    void updatePasswordByUserId(@Param("password") String password, @Param("email") String email, @Param("name") String name, @Param("userId") long userId);

    Optional<User> findByNameAndEmail(String name, String email);

    User findByNickname(String nickname);
}
