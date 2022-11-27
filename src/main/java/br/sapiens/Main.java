package br.sapiens;


import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.configs.CriaEntidades;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(Main.class.getResource("/layout/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMaximized(true);
        stage.setTitle("Index");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        new CriaEntidades(new ConexaoSingleton().getConnection());
        launch();
    }

}