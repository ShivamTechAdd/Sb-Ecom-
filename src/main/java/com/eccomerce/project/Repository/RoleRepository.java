package com.eccomerce.project.Repository;

import com.eccomerce.project.Model.AppRole;
import com.eccomerce.project.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
