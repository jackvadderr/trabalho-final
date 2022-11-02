import br.sapiens.Main;
import br.sapiens.configs.ConexaoSingleton;
import com.sun.jdi.connect.spi.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

public class MainTest {

    @Test
    public void test() throws SQLException {
        Assert.assertTrue(new ConexaoSingleton().getConnection() != null);
    }
}