package com.example.PSICOLOGO.controle;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AgendamentoRequest {
    private Long userId;
    private LocalDateTime dataHora;
}
