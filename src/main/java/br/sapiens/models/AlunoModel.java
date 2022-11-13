package br.sapiens.models;

import java.util.Date;

public class AlunoModel {
    private Integer id;
    private String nome;
    private String dataNascimento;
    private CursoEnum curso;

    public AlunoModel(Integer id, String nome, String dataNascimento,CursoEnum curso) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.curso = curso;
    }

    public AlunoModel(String nome, String dataNascimento,CursoEnum curso) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.curso = curso;
    }

    public Integer getId() {
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

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public CursoEnum getCurso() {
        return curso;
    }

    public void setCurso(CursoEnum curso) {
        this.curso = curso;
    }
}
