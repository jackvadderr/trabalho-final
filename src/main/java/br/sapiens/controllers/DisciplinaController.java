package br.sapiens.controllers;

import br.sapiens.Main;
import br.sapiens.daos.DisciplinaDao;
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

//    @FXML
//    DatePicker dataJf;

    public DisciplinaController() throws SQLException {
    }

    @FXML
    public void initialize() throws IOException, SQLException {
        if(curso != null){ // estou na tela de cadastro
            ObservableList<CursoEnum> list = FXCollections.observableArrayList();
            list.addAll(CursoEnum.values());
            curso.setItems(list);
        }
        if(table != null){
            TableColumn<DisciplinaModel, String> idC = new TableColumn("Id");
            idC.setCellValueFactory(new PropertyValueFactory("id"));
            TableColumn<DisciplinaModel, String> descricaoC = new TableColumn("Descricao");
            descricaoC.setCellValueFactory(new PropertyValueFactory("descricao"));
            TableColumn<DisciplinaModel, String> cursoC = new TableColumn("Curso");
            cursoC.setCellValueFactory(new PropertyValueFactory("curso"));

//            TableColumn<DisciplinaModel, String> data = new TableColumn("Data");
//            data.setCellValueFactory(new PropertyValueFactory("dataFormato"));

            TableColumn action = new TableColumn("Ação");
            action.setCellFactory(criaAcao());
            table.getColumns().addAll(List.of(idC,descricaoC, cursoC, action));
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
        //LocalDate localDate = dataJf.getValue();
        //Date date = new DateParse().parse(localDate);
        DisciplinaModel retorno = dao.save(new DisciplinaModel(idInt, descricao.getText(), (CursoEnum) curso.getValue()));
        this.id.setText(retorno.getId().toString());
    }
}