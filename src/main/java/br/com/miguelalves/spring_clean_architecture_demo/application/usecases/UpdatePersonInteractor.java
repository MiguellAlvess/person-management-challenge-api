package br.com.miguelalves.spring_clean_architecture_demo.application.usecases;

import br.com.miguelalves.spring_clean_architecture_demo.application.gateways.PersonGateway;
import br.com.miguelalves.spring_clean_architecture_demo.domain.entity.Person;

public class UpdatePersonInteractor {
    private final PersonGateway personGateway;

    public UpdatePersonInteractor(PersonGateway personGateway) {
        this.personGateway = personGateway;
    }

    public Person update(Person person) {
        return personGateway.update(person);
    }
}
