package br.com.miguelalves.spring_clean_architecture_demo.infra.controller.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePersonRequest(
        @NotBlank String name,
        @NotNull LocalDate birthDate,
        @NotBlank String cpf,
        @Valid List<AddressRequest> addresses) {
}
