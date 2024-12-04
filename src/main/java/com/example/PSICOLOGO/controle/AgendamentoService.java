package com.example.PSICOLOGO.controle;

import com.example.PSICOLOGO.modelos.Agenda;
import com.example.PSICOLOGO.modelos.AgendaRepository;
import com.example.PSICOLOGO.modelos.User;
import com.example.PSICOLOGO.modelos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
@Slf4j
public class AgendamentoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    private final Queue<User> filaUser = new LinkedList<>();
    private AgendamentoService agendamentoService;

    public User cadastrarUser(String firstName, String lastName, String email, String password) {
        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)  // Aqui você pode aplicar hash na senha
                .enabled(true)
                .accountLocked(false)
                .build();
        return userRepository.save(user);
    }

    public Agenda cadastrarAgenda(Long userId, LocalDateTime dataHora) {
        User user = userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (agendaRepository.existsByUserAndDataHora(user, dataHora)) {
            throw new IllegalArgumentException("Já existe um agendamento para este horário.");
        }

        Agenda agenda = new Agenda();
        agenda.setUser(user);
        agenda.setDataHora(dataHora);
        return agendaRepository.save(agenda);
    }

    @Scheduled(cron = "${spring.task.scheduling.cron}")
    public void agendaTarefas() {
        log.info("Agendado e executado em {}", LocalDateTime.now());
    }


    public Agenda agendar(Long userId, LocalDateTime dataHora) {
        return cadastrarAgenda(userId, dataHora);
    }

    public List<Agenda> listarAgendas() {
        return agendaRepository.findAll();
    }

    public void adicionarUserFila(Long userId) {
        User user = userRepository
                .findById(Math.toIntExact(userId))
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        filaUser.offer(user);
        log.info("Paciente adicionado na fila {}", user.getFirstName());
    }



    @Scheduled(cron = "0 0 * * * *")  // Este cron executa a cada hora
    public void agendarAtendimento() {
        agendamentoService.processarFilaUser();
    }

    public void processarFilaUser() {
        if (!filaUser.isEmpty()) {
            User proximoUser = filaUser.poll(); // Remove o primeiro da fila

            log.info("Atendendo paciente: {}", proximoUser.getFirstName());

            // Após 1 hora , agende o próximo atendimento
            LocalDateTime proximaDataHora = LocalDateTime.now().plusHours(1);
            agendar(Long.valueOf(proximoUser.getId()), proximaDataHora); // Crie uma agenda para o próximo atendimento
        } else {
            log.info("Fila de pacientes vazia.");
        }
    }
}

