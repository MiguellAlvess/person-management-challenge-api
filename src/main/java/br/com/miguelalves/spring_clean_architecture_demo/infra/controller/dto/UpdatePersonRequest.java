package br.com.miguelalves.spring_clean_architecture_demo.infra.controller.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record UpdatePersonRequest(
        @NotBlank String name,
        LocalDate birthDate,
        @NotBlank String cpf,
        @Valid List<AddressRequest> addresses) {
}
