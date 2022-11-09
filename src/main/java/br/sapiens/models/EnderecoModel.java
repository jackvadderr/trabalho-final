package br.sapiens.models;

public class EnderecoModel {


    private String descricao;
    private LogradouroEnum logradouro;

    private Integer id;

    public EnderecoModel(Integer id, String descricao, LogradouroEnum logradouro) {
        this.id = id;
        this.descricao = descricao;
        this.logradouro = logradouro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LogradouroEnum getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(LogradouroEnum logradouro) {
        this.logradouro = logradouro;
    }
}