package br.sapiens.models;

public class AlunoModel {
    private int id;
    private String nome;
    private String date;
    private CursoEnum curso;

    public AlunoModel(int id, String nome, String date, CursoEnum curso) {
        this.id = id;
        this.nome = nome;
        this.date = date;
        this.curso = curso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CursoEnum getCurso() {
        return curso;
    }

    public void setCurso(CursoEnum curso) {
        this.curso = curso;
    }
}
