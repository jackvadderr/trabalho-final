package br.sapiens.controllers;

import br.sapiens.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {

    public BorderPane pane;
    // Endereço
    public void cadastrarEndereco() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(Main.class.getResource("/endereco/cadastro.fxml"));
        pane.setCenter(fxmlLoader.load());
    }

    public void listaEndereco() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(Main.class.getResource("/endereco/listaEndereco.fxml"));
        pane.setCenter(fxmlLoader.load());
    }
    // Aluno
    public void cadastrarAluno() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(Main.class.getResource("/aluno/CadListar.fxml"));
        pane.setCenter(fxmlLoader.load());
    }


    // Disciplina
    public void cadastrarDisciplina() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(Main.class.getResource("/disciplina/CadListar.fxml"));
        pane.setCenter(fxmlLoader.load());
    }
    public void initialize() throws IOException {
        var label = new Label("Sapiens");
    }
}