package br.sapiens.controllers;

import br.sapiens.Main;
import br.sapiens.daos.AlunoDao;
import br.sapiens.daos.EnderecoDao;
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

public class AlunoController {

    public Label id;
    AlunoDao dao = new AlunoDao();
    @FXML
    TextField nome;
    @FXML
    ChoiceBox curso;

    @FXML
    TableView table;

    @FXML
    Pane painelVinculo;

    @FXML
    DatePicker dataJf;

    public AlunoController() throws SQLException {
    }

    @FXML
    public void initialize() throws IOException, SQLException {
        if(curso != null){ // estou na tela de cadastro
            ObservableList<CursoEnum> list = FXCollections.observableArrayList();
            list.addAll(CursoEnum.values());
            curso.setItems(list);
        }
        if(table != null){
            TableColumn<AlunoModel, String> idC = new TableColumn("Id");
            idC.setCellValueFactory(new PropertyValueFactory("id"));
            TableColumn<AlunoModel, String> nomeC = new TableColumn("Nome");
            nomeC.setCellValueFactory(new PropertyValueFactory("nome"));
            TableColumn<AlunoModel, String> cursoC = new TableColumn("Curso");
            cursoC.setCellValueFactory(new PropertyValueFactory("curso"));

            TableColumn<AlunoModel, String> data = new TableColumn("Data");
            data.setCellValueFactory(new PropertyValueFactory("dataNascimento"));

            TableColumn action = new TableColumn("Ação");
            action.setCellFactory(criaAcao());
            table.getColumns().addAll(List.of(idC,nomeC, cursoC,data,action));
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
                                                new FXMLLoader(Main.class.getResource("/aluno/vinculo.fxml"));
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
        System.out.print(id);
        Integer idInt = null;
        if(!id.isEmpty()) {
            idInt = Integer.valueOf(id);
        }

        LocalDate localDate = dataJf.getValue();
        Date date = new DateParse().parse(localDate);
        AlunoModel retorno = dao.save(new AlunoModel(idInt, nome.getText(), date, (CursoEnum) curso.getValue()));
        this.id.setText(retorno.getId().toString());
    }
}