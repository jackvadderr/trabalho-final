package br.sapiens.models;

public enum CursoEnum {
    SISTEMA("Sistema de informação", 3500),
    DIREITO("Direito", 4000);

    public final String label;
    public final Integer carga;

    CursoEnum(String label, Integer carga){
        this.label = label;
        this.carga = carga;
    }
}
