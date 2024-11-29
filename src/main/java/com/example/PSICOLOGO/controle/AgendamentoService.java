package com.example.PSICOLOGO.controle;

import com.example.PSICOLOGO.modelos.Agenda;
import com.example.PSICOLOGO.modelos.AgendaRepository;
import com.example.PSICOLOGO.modelos.User;
import com.example.PSICOLOGO.modelos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgendaRepository agendaRepository;

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
        User user = userRepository.findById(userId.intValue())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Agenda agenda = new Agenda();
        agenda.setUser(user);
        agenda.setDataHora(dataHora);
        return agendaRepository.save(agenda);
    }

    public void cadastrarHorario(Long userId, LocalDateTime dataHora) {
        cadastrarAgenda(userId, dataHora);
    }

    public Agenda agendar(Long userId, LocalDateTime dataHora) {
        return cadastrarAgenda(userId, dataHora);
    }

    public List<Agenda> listarAgendas() {
        return agendaRepository.findAll();
    }
}
