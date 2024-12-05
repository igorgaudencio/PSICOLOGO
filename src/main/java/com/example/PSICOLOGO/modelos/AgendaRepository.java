package com.example.PSICOLOGO.modelos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    boolean existsByUserAndDataHora(User user, LocalDateTime dataHora);
    List<Agenda> findByDataHora(LocalDateTime dataHora);
    long countByDataHora(LocalDateTime dataHora);
}
