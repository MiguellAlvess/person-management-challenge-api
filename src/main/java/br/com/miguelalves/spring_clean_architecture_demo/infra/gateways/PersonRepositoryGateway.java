package br.com.miguelalves.spring_clean_architecture_demo.infra.gateways;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;

import br.com.miguelalves.spring_clean_architecture_demo.application.gateways.PersonGateway;
import br.com.miguelalves.spring_clean_architecture_demo.domain.entity.Person;
import br.com.miguelalves.spring_clean_architecture_demo.infra.persistence.PersonEntity;
import br.com.miguelalves.spring_clean_architecture_demo.infra.persistence.PersonRepository;
import br.com.miguelalves.spring_clean_architecture_demo.main.exceptions.DuplicateCpfException;
import br.com.miguelalves.spring_clean_architecture_demo.main.exceptions.ResourceNotFoundException;

public class PersonRepositoryGateway implements PersonGateway {

    private final PersonRepository personRepository;
    private final PersonEntityMapper mapper;

    public PersonRepositoryGateway(PersonRepository personRepository, PersonEntityMapper mapper) {
        this.personRepository = personRepository;
        this.mapper = mapper;
    }

    @Override
    public Person create(Person person) {
        if (person.getCpf() != null && personRepository.existsByCpf(person.getCpf())) {
            throw new DuplicateCpfException("CPF already exists");
        }
        try {
            PersonEntity entity = mapper.toEntity(person);
            PersonEntity saved = personRepository.save(entity);
            return mapper.toDomain(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateCpfException("CPF conflict");
        }
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Person> findById(Long id) {
        return personRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Person update(Person person) {
        if (person.getId() == null)
            throw new ResourceNotFoundException("Person id required");
        PersonEntity existing = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        // cpf unique check
        if (person.getCpf() != null && !person.getCpf().equals(existing.getCpf())
                && personRepository.existsByCpf(person.getCpf())) {
            throw new DuplicateCpfException("CPF already exists");
        }
        PersonEntity toSave = mapper.toEntity(person);
        toSave.setId(existing.getId());
        try {
            PersonEntity saved = personRepository.save(toSave);
            return mapper.toDomain(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateCpfException("CPF conflict");
        }
    }

    @Override
    public void delete(Long id) {
        PersonEntity existing = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        personRepository.delete(existing);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return personRepository.existsByCpf(cpf);
    }
}
