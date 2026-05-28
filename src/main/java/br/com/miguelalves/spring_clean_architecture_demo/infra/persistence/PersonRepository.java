package br.com.miguelalves.spring_clean_architecture_demo.infra.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    Optional<PersonEntity> findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}
