package com.example.PSICOLOGO.controle;

import com.example.PSICOLOGO.modelos.Agenda;
import com.example.PSICOLOGO.modelos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping("/users")
    public User cadastrarUser(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password) {
        return agendamentoService.cadastrarUser(firstName, lastName, email, password);
    }

    @PostMapping("/agendas")
    public Agenda cadastrarAgenda(@RequestParam Long userId, @RequestParam String dataHora) {
        LocalDateTime dateTime = LocalDateTime.parse(dataHora);
        return agendamentoService.cadastrarAgenda(userId, dateTime);
    }

    @GetMapping("/agendas")
    public List<Agenda> listarAgendas() {
        return agendamentoService.listarAgendas();
    }
}
