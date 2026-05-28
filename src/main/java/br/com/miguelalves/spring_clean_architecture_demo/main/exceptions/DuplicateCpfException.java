package br.com.miguelalves.spring_clean_architecture_demo.main.exceptions;

public class DuplicateCpfException extends RuntimeException {
    public DuplicateCpfException(String message) {
        super(message);
    }
}
