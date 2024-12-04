package com.example.PSICOLOGO.modelos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    boolean existsByUserAndDataHora(User user, LocalDateTime dataHora);
}
