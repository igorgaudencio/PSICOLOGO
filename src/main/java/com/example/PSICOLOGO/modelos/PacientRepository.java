package com.example.PSICOLOGO.modelos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacientRepository extends JpaRepository<Pacient, Integer> {
    Optional<Pacient> findByEmail(String email);

}
