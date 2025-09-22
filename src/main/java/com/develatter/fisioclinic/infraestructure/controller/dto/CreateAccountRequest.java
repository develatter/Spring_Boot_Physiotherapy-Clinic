package com.develatter.fisioclinic.infraestructure.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;

@Builder
public record CreateAccountRequest(

        @NotBlank @Email
        String email,

        @NotBlank
        String password,

        @NotNull
        Set<String> roles
) {
}
