package br.sapiens.models;

public class DisciplinaModel {

    private Integer id;
    private String descricao;
    private int creditos;
    private CursoEnum curso;


    public DisciplinaModel(Integer id, String descricao, CursoEnum curso) {
        this.id = id;
        this.descricao = descricao;
        this.curso = curso;
    }

    public DisciplinaModel(String descricao, CursoEnum curso) {
        this.descricao = descricao;
        this.curso = curso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public CursoEnum getCurso() {
        return curso;
    }

    public void setCurso(CursoEnum curso) {
        this.curso = curso;
    }
}
