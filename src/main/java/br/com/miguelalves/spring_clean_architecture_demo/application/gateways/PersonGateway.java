package br.com.miguelalves.spring_clean_architecture_demo.application.gateways;

import java.util.List;
import java.util.Optional;

import br.com.miguelalves.spring_clean_architecture_demo.domain.entity.Person;

public interface PersonGateway {
    Person create(Person person);

    List<Person> findAll();

    Optional<Person> findById(Long id);

    Person update(Person person);

    void delete(Long id);

    boolean existsByCpf(String cpf);
}
