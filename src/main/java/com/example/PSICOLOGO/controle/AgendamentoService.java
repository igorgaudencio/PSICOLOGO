package com.example.PSICOLOGO.controle;

import com.example.PSICOLOGO.modelos.Agenda;
import com.example.PSICOLOGO.modelos.AgendaRepository;
import com.example.PSICOLOGO.modelos.User;
import com.example.PSICOLOGO.modelos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final int MAX_VAGAS = 8;
    private final Queue<User> filaUser = new LinkedList<>();


    public User cadastrarUser(String firstName, String lastName, String email, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setEnabled(true);
        user.setAccountLocked(false);

        return userRepository.save(user);
    }


    public Agenda cadastrarAgenda(Integer userId, LocalDateTime dataHora) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        if (agendaRepository.existsByUserAndDataHora(user, dataHora)) {
            throw new IllegalArgumentException("Usuário já possui uma vaga reservada para este horário.");
        }

        long vagasOcupadas = agendaRepository.countByDataHora(dataHora);
        if (vagasOcupadas >= MAX_VAGAS) {
            throw new IllegalArgumentException("Todas as vagas para este horário estão ocupadas.");
        }

        Agenda agenda = new Agenda();
        agenda.setUser(user);
        agenda.setDataHora(dataHora);
        agenda.setVaga((int) (vagasOcupadas + 1));
        agenda.setCheckIn(false);

        return agendaRepository.save(agenda);
    }


    public List<Agenda> listarAgendas() {
        return agendaRepository.findAll();
    }


    public void adicionarUserFila(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        filaUser.offer(user);
        log.info("Paciente adicionado na fila: {}", user.getFirstName());
    }


    public List<Agenda> listarAgendas(LocalDateTime dataHora) {
        return agendaRepository.findByDataHora(dataHora);
    }


    public void realizarCheckIn(Long agendaId) {
        Agenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada."));

        if (agenda.isCheckIn()) {
            throw new IllegalArgumentException("Check-in já realizado para esta vaga.");
        }

        agenda.setCheckIn(true);
        agendaRepository.save(agenda);

        log.info("Check-in realizado com sucesso para o usuário: {}", agenda.getUser().getFirstName());
    }
}
