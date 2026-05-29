package br.com.miguelalves.spring_clean_architecture_demo.infra.gateways;

import java.util.List;
import java.util.Optional;

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
        validateCpfDoesNotExist(person.getCpf());
        try {
            PersonEntity entity = mapper.toEntity(person);
            PersonEntity savedEntity = personRepository.save(entity);
            return mapper.toDomain(savedEntity);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateCpfException("Cpf already exists");
        }
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Person> findById(Long id) {
        return personRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Person update(Person person) {
        PersonEntity existingEntity = findEntityByIdOrThrow(person.getId());
        validateCpfForUpdate(person.getCpf(), existingEntity.getCpf());
        try {
            PersonEntity entityToSave = mapper.toEntity(person);
            entityToSave.setId(existingEntity.getId());
            PersonEntity savedEntity = personRepository.save(entityToSave);
            return mapper.toDomain(savedEntity);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateCpfException("CPF already exists");
        }
    }

    @Override
    public void delete(Long id) {
        PersonEntity existingEntity = findEntityByIdOrThrow(id);
        personRepository.delete(existingEntity);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return personRepository.existsByCpf(cpf);
    }

    private PersonEntity findEntityByIdOrThrow(Long id) {
        if (id == null) {
            throw new ResourceNotFoundException("Person id required");
        }
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));
    }

    private void validateCpfDoesNotExist(String cpf) {
        if (cpf != null && personRepository.existsByCpf(cpf)) {
            throw new DuplicateCpfException("CPF already exists");
        }
    }

    private void validateCpfForUpdate(String newCpf, String currentCpf) {
        boolean cpfHasChanged = newCpf != null && !newCpf.equals(currentCpf);
        if (cpfHasChanged && personRepository.existsByCpf(newCpf)) {
            throw new DuplicateCpfException("CPF already exists");
        }
    }
}