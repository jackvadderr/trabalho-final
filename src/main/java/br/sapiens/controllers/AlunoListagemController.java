package br.sapiens.controllers;


import br.sapiens.daos.AlunoDao;
import br.sapiens.models.AlunoModel;
import br.sapiens.models.CursoEnum;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class AlunoListagemController {

        AlunoDao alunoDao = new AlunoDao();

        public AlunoListagemController() throws SQLException {
        }

        @FXML
        private TableView table;

        @FXML
        private Button windson;

        //final ObservableList<AlunoModel> data = FXCollections.observableArrayList();



        @FXML
        public void initialize() throws SQLException {
            Iterable<AlunoModel> alunos = (Iterable<AlunoModel>) alunoDao.findAll();

            List<AlunoModel> listaAlunos = new ArrayList<>();

            while (alunos.iterator().hasNext()) {
                listaAlunos.add(alunos.iterator().next());
            }

            table.setEditable(true);

            // ID
            TableColumn idCol = new TableColumn("ID");
            idCol.setMinWidth(100);
            idCol.setCellValueFactory(
                    new PropertyValueFactory<AlunoModel, Integer>("id")
                    );

            // Nome
            TableColumn nomeCol = new TableColumn("First Name");
            nomeCol.setMinWidth(100);
            nomeCol.setCellValueFactory(
                    new PropertyValueFactory<AlunoModel, String>("nome"));

            // Data Nascimento
            TableColumn nascCol = new TableColumn("Email");
            nascCol.setMinWidth(100);
            nascCol.setCellValueFactory(
                    new PropertyValueFactory<AlunoModel, String>("email"));

            // Data Nascimento
            TableColumn cursoCol = new TableColumn("Curso");
            nascCol.setMinWidth(100);
            nascCol.setCellValueFactory(
                    new PropertyValueFactory<AlunoModel, CursoEnum>("curso"));

            table.setItems((ObservableList) listaAlunos);
            table.getColumns().addAll(idCol, nomeCol,nomeCol,nascCol);
            table.setEditable(false);
        }
}