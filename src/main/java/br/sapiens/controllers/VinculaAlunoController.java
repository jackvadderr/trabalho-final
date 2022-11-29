package br.sapiens.controllers;

import br.sapiens.Main;
import br.sapiens.daos.AlunoDao;
import br.sapiens.daos.DisciplinaDao;
import br.sapiens.models.AlunoModel;
import br.sapiens.models.DisciplinaModel;
import br.sapiens.models.MatriculaModel;
import br.sapiens.models.PeriodoEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class VinculaAlunoController {

    private AlunoModel aluno;

    private AlunoDao alunoDao;
    private DisciplinaDao disciplinaDao = new DisciplinaDao();

    //public final List<DisciplinaModel> resultados = disciplinaDao.findAll();
    @FXML
    Label idLabel;

    @FXML
    Label nomeLabel;

    @FXML
    Label cursoLabel;

    @FXML
    Label dataLabel;

    @FXML
    ChoiceBox vinculo;

    @FXML
    ChoiceBox vinculo2;

    @FXML
    TableView table;

    public VinculaAlunoController() throws SQLException {
    }

    public void recebeAluno(AlunoModel aluno) throws SQLException {
        this.aluno = aluno;
        idLabel.setText(aluno.getId().toString());
        nomeLabel.setText(aluno.getNome());
        cursoLabel.setText(aluno.getCurso().toString());
        dataLabel.setText(aluno.getDataNascimento().toString());
    }

    @FXML
    public void initialize() throws IOException, SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll(List.of("Disciplina"));
        vinculo.setItems(list);
        ObservableList<PeriodoEnum> periodos = FXCollections.observableArrayList();
        periodos.addAll(List.of(PeriodoEnum.values()));
        vinculo2.setItems(periodos);
    }

    public void salvar() throws SQLException {
        //Salvar matricula no banco de dados
        if(vinculo.getValue() == "Disciplina"){
            System.out.print("Debug 1");
            carregaMatriculas();
        }
        System.out.println("Salvando");
        vinculo.valueProperty().set(null);

    }

    public void carregaMatriculas() throws SQLException {
        if(table != null){
            List<DisciplinaModel> resultados = disciplinaDao.findAll();
            resultados.removeAll(table.getItems());
            TableColumn<DisciplinaModel, String> idC = new TableColumn("Id");
            idC.setCellValueFactory(new PropertyValueFactory("id"));
            TableColumn<DisciplinaModel, String> descricaoC = new TableColumn("Descrição");
            descricaoC.setCellValueFactory(new PropertyValueFactory("descricao"));
            TableColumn<DisciplinaModel, String> cursoC = new TableColumn("Curso");
            cursoC.setCellValueFactory(new PropertyValueFactory("curso"));
            table.getColumns().addAll(List.of(idC, descricaoC, cursoC));
            table.getItems().addAll(resultados);
            table.getSelectionModel().getSelectedItem();

            MatriculaModel matriculaModel = new MatriculaModel(resultados.get(0), aluno, (PeriodoEnum) vinculo2.getValue());
            System.out.print(matriculaModel.getId());
        }
    }
}