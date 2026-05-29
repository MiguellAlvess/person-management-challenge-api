package br.com.miguelalves.spring_clean_architecture_demo.utils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TestPersonFactory {

    private TestPersonFactory() {
    }

    public static Map<String, Object> createPersonWithAddressesRequest(String name, LocalDate birthDate, String cpf) {
        return createPersonRequest(name, birthDate, cpf, List.of(
                address("Rua das Flores", "123", "Centro", "São Paulo", "SP", "01001-000"),
                address("Avenida Paulista", "456", "Bela Vista", "São Paulo", "SP", "01310-000")));
    }

    public static Map<String, Object> createPersonWithoutAddressesRequest(String name, LocalDate birthDate,
            String cpf) {
        return createPersonRequest(name, birthDate, cpf, List.of());
    }

    public static Map<String, Object> updatePersonRequest(String name, LocalDate birthDate, String cpf,
            List<Map<String, Object>> addresses) {
        return createPersonRequest(name, birthDate, cpf, addresses);
    }

    public static Map<String, Object> address(String street, String number, String neighborhood, String city,
            String state, String cep) {
        Map<String, Object> address = new HashMap<>();
        address.put("street", street);
        address.put("number", number);
        address.put("neighborhood", neighborhood);
        address.put("city", city);
        address.put("state", state);
        address.put("cep", cep);
        return address;
    }

    private static Map<String, Object> createPersonRequest(String name, LocalDate birthDate, String cpf,
            List<Map<String, Object>> addresses) {
        Map<String, Object> request = new HashMap<>();
        request.put("name", name);
        request.put("birthDate", birthDate.toString());
        request.put("cpf", cpf);
        request.put("addresses", addresses);
        return request;
    }
}
