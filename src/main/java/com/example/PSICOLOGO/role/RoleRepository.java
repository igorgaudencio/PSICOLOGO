package com.example.PSICOLOGO.role;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.management.openmbean.OpenMBeanInfo;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String role);
}
