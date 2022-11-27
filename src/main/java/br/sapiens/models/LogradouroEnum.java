package br.sapiens.models;

public enum LogradouroEnum {
    Rua("rua"),
    Avenida("avenida");


    private final String logradouro;

    LogradouroEnum(String logradouro) {
        this.logradouro = logradouro;
    }
}
