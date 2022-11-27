package br.sapiens.models;

public enum PeriodoEnum {

    PRIMEIRO("2022"),
    SEGUNDO("2023");

    public final String periodo;

    PeriodoEnum(String periodo){
        this.periodo = periodo;
    }
}
