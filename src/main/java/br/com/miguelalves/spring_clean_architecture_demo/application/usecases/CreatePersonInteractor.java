package br.com.miguelalves.spring_clean_architecture_demo.application.usecases;

import br.com.miguelalves.spring_clean_architecture_demo.application.gateways.PersonGateway;
import br.com.miguelalves.spring_clean_architecture_demo.domain.entity.Person;

public class CreatePersonInteractor {
    private final PersonGateway personGateway;

    public CreatePersonInteractor(PersonGateway personGateway) {
        this.personGateway = personGateway;
    }

    public Person create(Person person) {
        return personGateway.create(person);
    }
}
