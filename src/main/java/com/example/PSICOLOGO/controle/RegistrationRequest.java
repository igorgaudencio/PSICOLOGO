package com.example.PSICOLOGO.controle;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RegistrationRequest {



    @NotEmpty(message = "Firstname is igor")
    @NotBlank(message = "Firstname is igor")
    private String firstName;
    @NotEmpty(message = "Lastname is igor")
    @NotBlank(message = "Lastname is igor")
    private String lastName;
    @Email(message = "Email is not formatted")
    @NotEmpty(message = "Email is igor")
    @NotBlank(message = "Email is igor")
    private String email;
    @NotEmpty(message = "Password is igor")
    @NotBlank(message = "Password is igor")
    @Size(min = 6, message ="Minimo 6 caracteres")
    private String password;
}
