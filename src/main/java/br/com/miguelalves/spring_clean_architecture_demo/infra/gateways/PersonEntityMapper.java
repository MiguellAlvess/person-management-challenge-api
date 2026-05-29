package br.com.miguelalves.spring_clean_architecture_demo.infra.gateways;

import java.util.List;

import br.com.miguelalves.spring_clean_architecture_demo.domain.entity.Address;
import br.com.miguelalves.spring_clean_architecture_demo.domain.entity.Person;
import br.com.miguelalves.spring_clean_architecture_demo.infra.persistence.AddressEntity;
import br.com.miguelalves.spring_clean_architecture_demo.infra.persistence.PersonEntity;

public class PersonEntityMapper {

    public PersonEntity toEntity(Person person) {
        if (person == null) {
            return null;
        }
        PersonEntity entity = new PersonEntity();
        entity.setId(person.getId());
        entity.setName(person.getName());
        entity.setBirthDate(person.getBirthDate());
        entity.setCpf(person.getCpf());
        entity.setAddresses(toAddressEntities(person.getAddresses(), entity));
        return entity;
    }

    public Person toDomain(PersonEntity entity) {
        if (entity == null) {
            return null;
        }
        Person person = new Person();
        person.setId(entity.getId());
        person.setName(entity.getName());
        person.setBirthDate(entity.getBirthDate());
        person.setCpf(entity.getCpf());
        person.setAddresses(toAddresses(entity.getAddresses()));
        return person;
    }

    private List<AddressEntity> toAddressEntities(List<Address> addresses, PersonEntity personEntity) {
        if (addresses == null) {
            return List.of();
        }
        return addresses.stream()
                .map(address -> toAddressEntity(address, personEntity))
                .toList();
    }

    private AddressEntity toAddressEntity(Address address, PersonEntity personEntity) {
        AddressEntity entity = new AddressEntity();
        entity.setId(address.getId());
        entity.setStreet(address.getStreet());
        entity.setNumber(address.getNumber());
        entity.setNeighborhood(address.getNeighborhood());
        entity.setCity(address.getCity());
        entity.setState(address.getState());
        entity.setCep(address.getCep());
        entity.setPerson(personEntity);
        return entity;
    }

    private List<Address> toAddresses(List<AddressEntity> addressEntities) {
        if (addressEntities == null) {
            return List.of();
        }
        return addressEntities.stream()
                .map(this::toAddress)
                .toList();
    }

    private Address toAddress(AddressEntity entity) {
        Address address = new Address();
        address.setId(entity.getId());
        address.setStreet(entity.getStreet());
        address.setNumber(entity.getNumber());
        address.setNeighborhood(entity.getNeighborhood());
        address.setCity(entity.getCity());
        address.setState(entity.getState());
        address.setCep(entity.getCep());
        return address;
    }
}