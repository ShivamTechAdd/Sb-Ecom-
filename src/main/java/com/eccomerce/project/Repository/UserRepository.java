package com.eccomerce.project.Repository;

import com.eccomerce.project.Model.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUserName(String username);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
}
