
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
import java.time.Instant;
import java.util.Date;

public class MainTest {

    @Test
    public void test() throws SQLException {
        Date data = new Date();
        System.out.println(data);
        System.out.println(data.getTime());
    }

    @Test
    public void test2(){
        // Tue Nov 08 20:51:08 AMT 2022
        // 1667955068655
        Date data = new Date(1667955068655L);
        System.out.println(data);
        Assert.assertTrue(data.toString().equals("Tue Nov 08 20:51:08 AMT 2022"));
    }

    @Test
    public void javaToSqlDate(){
        Date dateUtil = new Date();
        java.sql.Date dateSql = new java.sql.Date(dateUtil.getTime());
    }
}