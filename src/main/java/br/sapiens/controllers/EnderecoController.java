package br.sapiens.controllers;

import br.sapiens.Main;
import br.sapiens.daos.EnderecoDao;

import br.sapiens.models.EnderecoModel;
import br.sapiens.models.LogradouroEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EnderecoController {

    public Label id;
    EnderecoDao dao = new EnderecoDao();
    @FXML
    TextField descricao;
    @FXML
    ChoiceBox logradouro;

    @FXML
    TableView table;

    public EnderecoController() throws SQLException {
    }

    @FXML
    public void initialize() throws IOException, SQLException {
        if(logradouro != null){ // estou na tela de cadastro
            ObservableList<LogradouroEnum> list = FXCollections.observableArrayList();
            list.addAll(LogradouroEnum.values());
            logradouro.setItems(list);
        }

        if(table != null){ // estou na tela de listagem
            TableColumn<EnderecoModel, String> idC = new TableColumn("Id");
            idC.setCellValueFactory(new PropertyValueFactory("id"));
            TableColumn<EnderecoModel, String> logradouroC = new TableColumn("Logradouro");
            logradouroC.setCellValueFactory(new PropertyValueFactory("logradouro"));
            TableColumn<EnderecoModel, String> descricaoC = new TableColumn("Descricao");
            descricaoC.setCellValueFactory(new PropertyValueFactory("descricao"));
//            new TableColumn<Endereco,String>("Ação");
            table.getColumns().addAll(List.of(idC,logradouroC,descricaoC));
            table.getItems().addAll(dao.findAll());
        }

    }
    public void salvar() throws SQLException {
        String id = this.id.getText();
        Integer idInt = null;
        if(!id.isEmpty())
            idInt = Integer.valueOf(id);
        EnderecoModel retorno = dao.save(new EnderecoModel(idInt, descricao.getText(), (LogradouroEnum) logradouro.getValue()));
        this.id.setText(retorno.getId().toString());
    }
}