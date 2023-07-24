package br.com.alura.jornada_milhas.infra.exceptions;

public class InternalEntityNotFoundException extends RuntimeException {
    public InternalEntityNotFoundException(String message) {
        super(message);
    }
}
