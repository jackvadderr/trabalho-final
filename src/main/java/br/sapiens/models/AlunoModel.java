package br.sapiens.models;

import java.util.Date;
import java.util.List;

public class AlunoModel {

    private Integer id;
    private String nome;
    private Date dataNascimento;
    private List<MatriculaModel> matriculado;
    private CursoEnum curso;


    public AlunoModel(Integer id, String nome, Date dataNascimento,CursoEnum curso) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.curso = curso;
    }

    public AlunoModel(String nome, Date dataNascimento,CursoEnum curso) {
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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public CursoEnum getCurso() {
        return curso;
    }

    public void setCurso(CursoEnum curso) {
        this.curso = curso;
    }

}
