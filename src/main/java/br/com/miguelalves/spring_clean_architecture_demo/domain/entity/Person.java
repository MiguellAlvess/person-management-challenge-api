package br.com.miguelalves.spring_clean_architecture_demo.domain.entity;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class Person {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String cpf;
    private List<Address> addresses;

    public Person() {
    }

    public Person(Long id, String name, LocalDate birthDate, String cpf, List<Address> addresses) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.cpf = cpf;
        this.addresses = addresses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Integer getAge() {
        if (birthDate == null)
            return null;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
