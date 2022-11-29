package br.sapiens.controllers;

import br.sapiens.Main;
import br.sapiens.daos.DisciplinaDao;

import br.sapiens.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class DisciplinaController {

    public Label id;
    DisciplinaDao dao = new DisciplinaDao();
    @FXML
    TextField descricao;
    @FXML
    ChoiceBox curso;

    @FXML
    TableView table;

    @FXML
    Pane painelVinculo;

    public DisciplinaController() throws SQLException {
    }

    @FXML
    public void initialize() throws IOException, SQLException {
        if(curso != null){ // estou na tela de cadastro
            ObservableList<CursoEnum> list = FXCollections.observableArrayList();
            list.addAll(CursoEnum.values());
            curso.setItems(list);
        }
    }

    @FXML
    public void tab() throws SQLException {
        mostrarTabela();
    }


    public void mostrarTabela() throws SQLException {
        if(table != null){
            TableColumn<DisciplinaModel, String> idC = new TableColumn("Id");
            idC.setCellValueFactory(new PropertyValueFactory("id"));
            TableColumn<DisciplinaModel, String> descricaoC = new TableColumn("Descricao");
            descricaoC.setCellValueFactory(new PropertyValueFactory("descricao"));
            TableColumn<DisciplinaModel, String> cursoC = new TableColumn("Curso");
            cursoC.setCellValueFactory(new PropertyValueFactory("curso"));
            TableColumn action = new TableColumn("Ação");
            table.getColumns().addAll(List.of(idC,descricaoC, cursoC, action));
            table.getItems().addAll(dao.findAll());
        }
    }

    public void salvar() throws SQLException {
        String id = this.id.getText();
        Integer idInt = null;
        if(!id.isEmpty())
            idInt = Integer.valueOf(id);
        //LocalDate localDate = dataJf.getValue();
        //Date date = new DateParse().parse(localDate);
        DisciplinaModel retorno = dao.save(new DisciplinaModel(idInt, descricao.getText(), (CursoEnum) curso.getValue()));
        this.id.setText(retorno.getId().toString());
    }
}