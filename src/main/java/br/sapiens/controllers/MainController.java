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
    public void cadastrarEndereco() throws IOException {
        System.out.println("funcionou");
        FXMLLoader fxmlLoader =
                new FXMLLoader(Main.class.getResource("/endereco/cadastro.fxml"));
        pane.setCenter(fxmlLoader.load());
    }
    public void initialize() throws IOException {
        var label = new Label("Sapiens");
    }
}