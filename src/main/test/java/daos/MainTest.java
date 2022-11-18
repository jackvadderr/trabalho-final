package daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.configs.CriaEntidades;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class MainTest {
    @Test
    public void test() throws SQLException {
        Assert.assertTrue(new ConexaoSingleton().getConnection() != null);
    }
}