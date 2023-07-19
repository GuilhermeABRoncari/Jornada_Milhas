package br.com.alura.jornada_milhas.infra.exceptions;

public class TestimonyNotFoundException extends RuntimeException {
    public TestimonyNotFoundException(String message) {
        super(message);
    }
}
