package br.sapiens.controllers;

import br.sapiens.Main;
import br.sapiens.daos.EnderecoDao;
import br.sapiens.models.DateParse;
import br.sapiens.models.EnderecoModel;
import br.sapiens.models.LogradouroEnum;
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

public class EnderecoController {

    public Label id;
    EnderecoDao dao = new EnderecoDao();
    @FXML
    TextField descricao;
    @FXML
    ChoiceBox logradouro;

    @FXML
    TableView table;

    @FXML
    Pane painelVinculo;

    @FXML
    DatePicker dataJf;

    public EnderecoController() throws SQLException {
    }

    @FXML
    public void initialize() throws IOException, SQLException {
        if(logradouro != null){ // estou na tela de cadastro
            ObservableList<LogradouroEnum> list = FXCollections.observableArrayList();
            list.addAll(LogradouroEnum.values());
            logradouro.setItems(list);
        }
        if(table != null){
            TableColumn<EnderecoModel, String> idC = new TableColumn("Id");
            idC.setCellValueFactory(new PropertyValueFactory("id"));
            TableColumn<EnderecoModel, String> logradouroC = new TableColumn("Logradouro");
            logradouroC.setCellValueFactory(new PropertyValueFactory("logradouro"));
            TableColumn<EnderecoModel, String> descricaoC = new TableColumn("Descricao");
            descricaoC.setCellValueFactory(new PropertyValueFactory("descricao"));

            TableColumn<EnderecoModel, String> data = new TableColumn("Data");
            data.setCellValueFactory(new PropertyValueFactory("dataFormato"));

            TableColumn action = new TableColumn("Ação");
            action.setCellFactory(criaAcao());
            table.getColumns().addAll(List.of(idC,logradouroC,descricaoC,data,action));
            table.getItems().addAll(dao.findAll());
        }

    }

    private Callback<TableColumn<EnderecoModel, String>, TableCell<EnderecoModel, String>> criaAcao() {
       return
                new Callback<TableColumn<EnderecoModel, String>, TableCell<EnderecoModel, String>>() {
                    @Override
                    public TableCell call(final TableColumn<EnderecoModel, String> param) {
                        final TableCell<EnderecoModel, String> cell = new TableCell<EnderecoModel, String>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                setGraphic(null);
                                setText(null);
                                if (!empty) {
                                    Button btn = new Button("Vincular");
                                    btn.setOnAction(event -> {
                                        FXMLLoader fxmlLoader =
                                                new FXMLLoader(Main.class.getResource("/endereco/vinculo.fxml"));
                                        try {
                                            painelVinculo.getChildren().add(fxmlLoader.load());
                                            VinculaEnderecoController controller = fxmlLoader.getController();
                                            EnderecoModel endereco = this.getTableRow().getItem();
                                            controller.recebeEndereco(endereco);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });
                                    setGraphic(btn);
                                }
                            }
                        };
                        return cell;
                    }
                };
    }

    public void salvar() throws SQLException {
        String id = this.id.getText();
        Integer idInt = null;
        if(!id.isEmpty())
            idInt = Integer.valueOf(id);
        LocalDate localDate = dataJf.getValue();
        Date date = new DateParse().parse(localDate);
        EnderecoModel retorno = dao.save(new EnderecoModel(idInt, descricao.getText(), (LogradouroEnum) logradouro.getValue(),date));
        this.id.setText(retorno.getId().toString());
    }
}