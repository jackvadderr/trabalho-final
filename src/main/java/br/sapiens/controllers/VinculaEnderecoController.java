package br.sapiens.controllers;

import br.sapiens.models.EnderecoModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class VinculaEnderecoController {

    private EnderecoModel endereco;

    @FXML
    Label idLabel;

    @FXML
    Label descricaoLabel;

    @FXML
    ChoiceBox vinculo;

    @FXML
    TableView table;

    public void recebeEndereco(EnderecoModel endereco){
        this.endereco = endereco;
        idLabel.setText(endereco.getId().toString());
        descricaoLabel.setText(endereco.getDescricao());
        carregaMatriculas();
    }

    @FXML
    public void initialize() throws IOException, SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll(List.of("opcao1","opcao2"));
        vinculo.setItems(list);

    }

    public void salvar(){
        //Salvar matricula no banco de dados
        System.out.println("salvando");
        vinculo.valueProperty().set(null);
        carregaMatriculas();
    }


    public void carregaMatriculas(){
        if(table != null){

        }
    }

}