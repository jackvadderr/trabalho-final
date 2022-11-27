package br.sapiens.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EnderecoModel {
    private String descricao;
    private LogradouroEnum logradouro;

    private Integer id;

    private Date data;

    String dataFormato = "";

    public EnderecoModel(Integer id, String descricao, LogradouroEnum logradouro, Date data) {
        //java.util.Date --> Modelo esta usando essa data
        //sql.Date -> banco de dados usa essa data
        //java.time.LocalDate -> JavaFx
        this.id = id;
        this.descricao = descricao;
        this.logradouro = logradouro;
        this.data = data;
        if(data != null)
            dataFormato = new SimpleDateFormat("dd/MM/yyyy").format(data);
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

    public Date getData() {
        if(data != null)
            dataFormato = new SimpleDateFormat("dd/MM/yyyy").format(data);
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDataFormato() {
        return dataFormato;
    }

    public void setDataFormato(String dataFormato) {
        this.dataFormato = dataFormato;
    }
}
