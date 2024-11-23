package com.example.PSICOLOGO.security;

import com.example.PSICOLOGO.modelos.PacientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor

public class UserDetailsServiceImpl implements UserDetailsService {

    private final PacientRepository pacientRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return pacientRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário Não Encontrado"));
    }
}
