package com.example.PSICOLOGO.controle;

import com.example.PSICOLOGO.modelos.Agenda;
import com.example.PSICOLOGO.modelos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> cadastrarAgenda(
            @RequestParam Integer userId,
            @RequestParam String dataHora) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dataHora);
            Agenda agenda = agendamentoService.cadastrarAgenda(userId, dateTime);
            return ResponseEntity.ok(agenda);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/agendas")
    public List<Agenda> listarAgendas() {
        return agendamentoService.listarAgendas();
    }


    @PostMapping("/adicionar/{userId}")
    public ResponseEntity<String> adicionarUserFila(@PathVariable Integer userId) {
        try {
            agendamentoService.adicionarUserFila(userId);
            return ResponseEntity.ok("Paciente adicionado à fila.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/agendas/horario")
    public ResponseEntity<List<Agenda>> listarAgendas(@RequestParam String dataHora) {
        LocalDateTime dateTime = LocalDateTime.parse(dataHora);
        List<Agenda> agendas = agendamentoService.listarAgendas(dateTime);
        return ResponseEntity.ok(agendas);
    }


    @PostMapping("/agendas/checkin/{agendaId}")
    public ResponseEntity<String> realizarCheckIn(@PathVariable Long agendaId) {
        try {
            agendamentoService.realizarCheckIn(agendaId);
            return ResponseEntity.ok("Check-in realizado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
