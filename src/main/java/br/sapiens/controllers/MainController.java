package br.sapiens.controllers;

import br.sapiens.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

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
                new FXMLLoader(Main.class.getResource("/aluno/cadastro.fxml"));
        pane.setCenter(fxmlLoader.load());
    }

    public void listaALuno() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(Main.class.getResource("/aluno/listaAluno.fxml"));
        pane.setCenter(fxmlLoader.load());
    }
    // Disciplina
    public void cadastrarDisciplina() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(Main.class.getResource("/disciplina/cadastro.fxml"));
        pane.setCenter(fxmlLoader.load());
    }

    public void listaDisciplina() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(Main.class.getResource("/disciplina/listaDisciplina.fxml"));
        pane.setCenter(fxmlLoader.load());
    }

    public void initialize() throws IOException {
        var label = new Label("Sapiens");
    }
}