package daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.configs.CriaEntidades;
import br.sapiens.daos.EnderecoDao;
import br.sapiens.models.EnderecoModel;
import br.sapiens.models.LogradouroEnum;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EnderecoDaoTest {


    EnderecoDao enderecoDao = new EnderecoDao();

    public EnderecoDaoTest() throws SQLException {

    }

    @BeforeAll
    public static void init() throws SQLException {
        new CriaEntidades(new ConexaoSingleton().getConnection());
    }


    @Test
    public void save() throws SQLException, ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000");
        EnderecoModel endereco = new EnderecoModel(null,"Descrição", LogradouroEnum.Rua, date);
        EnderecoModel enderecoSalvo = enderecoDao.save(endereco);
        Assert.assertTrue(enderecoSalvo.getId() != null);
        Assert.assertTrue(enderecoSalvo.getData().getTime() == date.getTime());
    }


    @Test
    public void saveAll() throws SQLException, ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000");
        EnderecoModel endereco1 = new EnderecoModel(null,"Descrição - 1", LogradouroEnum.Rua, date);
        EnderecoModel endereco2 = new EnderecoModel(null,"Descrição - 2", LogradouroEnum.Rua, date);
        Iterable<EnderecoModel> enderecoSalvo = enderecoDao.saveAll(List.of(endereco1, endereco2));
        enderecoSalvo.forEach(it -> {
            Assert.assertTrue(it.getId() != null);
            Assert.assertTrue(it.getData().getTime() == date.getTime());
        });
    }

    @Test
    public void findById() throws SQLException {
        EnderecoModel endereco = new EnderecoModel(null,"Descrição", LogradouroEnum.Rua, new Date());
        EnderecoModel enderecoSalvo = enderecoDao.save(endereco);
        Assert.assertTrue(enderecoSalvo.getId() != null);
        Integer id = enderecoSalvo.getId();
        EnderecoModel enderecoBanco = enderecoDao.findById(id).get();
        Assert.assertTrue(endereco != enderecoBanco);
        Assert.assertTrue( enderecoBanco.getId() == id);
    }


    @Test
    public void update() throws SQLException {
        EnderecoModel endereco = new EnderecoModel(null,"Descrição", LogradouroEnum.Rua, new Date());
        EnderecoModel enderecoSalvo = enderecoDao.save(endereco);
        Integer id = endereco.getId();
        enderecoSalvo.setDescricao("Outra descricao");
        enderecoDao.save(endereco);
        EnderecoModel enderecoBanco = enderecoDao.findById(id).get();
        Assert.assertTrue(enderecoBanco.getDescricao().equals("Outra descricao"));
        Assert.assertTrue(enderecoBanco != endereco);
    }
}
