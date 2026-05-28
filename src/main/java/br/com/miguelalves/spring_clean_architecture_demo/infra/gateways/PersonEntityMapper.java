package br.com.miguelalves.spring_clean_architecture_demo.infra.gateways;

import java.util.List;
import java.util.stream.Collectors;

import br.com.miguelalves.spring_clean_architecture_demo.domain.entity.Address;
import br.com.miguelalves.spring_clean_architecture_demo.domain.entity.Person;
import br.com.miguelalves.spring_clean_architecture_demo.infra.persistence.AddressEntity;
import br.com.miguelalves.spring_clean_architecture_demo.infra.persistence.PersonEntity;

public class PersonEntityMapper {

    public PersonEntity toEntity(Person person) {
        if (person == null)
            return null;
        PersonEntity entity = new PersonEntity();
        entity.setId(person.getId());
        entity.setName(person.getName());
        entity.setBirthDate(person.getBirthDate());
        entity.setCpf(person.getCpf());
        if (person.getAddresses() != null) {
            List<AddressEntity> addresses = person.getAddresses().stream().map(a -> {
                AddressEntity ae = new AddressEntity();
                ae.setId(a.getId());
                ae.setStreet(a.getStreet());
                ae.setNumber(a.getNumber());
                ae.setNeighborhood(a.getNeighborhood());
                ae.setCity(a.getCity());
                ae.setState(a.getState());
                ae.setCep(a.getCep());
                ae.setPerson(entity);
                return ae;
            }).collect(Collectors.toList());
            entity.setAddresses(addresses);
        }
        return entity;
    }

    public Person toDomain(PersonEntity entity) {
        if (entity == null)
            return null;
        Person p = new Person();
        p.setId(entity.getId());
        p.setName(entity.getName());
        p.setBirthDate(entity.getBirthDate());
        p.setCpf(entity.getCpf());
        if (entity.getAddresses() != null) {
            List<Address> addresses = entity.getAddresses().stream().map(ae -> {
                Address a = new Address();
                a.setId(ae.getId());
                a.setStreet(ae.getStreet());
                a.setNumber(ae.getNumber());
                a.setNeighborhood(ae.getNeighborhood());
                a.setCity(ae.getCity());
                a.setState(ae.getState());
                a.setCep(ae.getCep());
                return a;
            }).collect(Collectors.toList());
            p.setAddresses(addresses);
        }
        return p;
    }
}
