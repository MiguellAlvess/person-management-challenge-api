package br.com.miguelalves.spring_clean_architecture_demo.infra.controller.dto;

import java.time.LocalDate;
import java.util.List;

public record PersonResponse(
        Long id,
        String name,
        LocalDate birthDate,
        String cpf,
        Integer age,
        List<AddressResponse> addresses) {
}
