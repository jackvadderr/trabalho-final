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
    public void save() throws SQLException {
        EnderecoModel enderecoModel = new EnderecoModel(null,"Descrição", LogradouroEnum.Rua);
        EnderecoModel enderecoModelSalvo = enderecoDao.save(enderecoModel);
        Assert.assertTrue(enderecoModelSalvo.getId() != null);
    }


    @Test
    public void saveAll() throws SQLException {
        EnderecoModel enderecoModel1 = new EnderecoModel(null,"Descrição - 1", LogradouroEnum.Rua);
        EnderecoModel enderecoModel2 = new EnderecoModel(null,"Descrição - 2", LogradouroEnum.Rua);
        Iterable<EnderecoModel> enderecoSalvo = enderecoDao.saveAll(List.of(enderecoModel1, enderecoModel2));
        enderecoSalvo.forEach(it -> Assert.assertTrue(it.getId() != null));
    }

    @Test
    public void findById() throws SQLException {
        EnderecoModel enderecoModel = new EnderecoModel(null,"Descrição", LogradouroEnum.Rua);
        EnderecoModel enderecoModelSalvo = enderecoDao.save(enderecoModel);
        Assert.assertTrue(enderecoModelSalvo.getId() != null);
        Integer id = enderecoModelSalvo.getId();
        EnderecoModel enderecoModelBanco = enderecoDao.findById(id).get();
        Assert.assertTrue(enderecoModel != enderecoModelBanco);
        Assert.assertTrue( enderecoModelBanco.getId() == id);
    }


    @Test
    public void update() throws SQLException {
        EnderecoModel enderecoModel = new EnderecoModel(null,"Descrição", LogradouroEnum.Rua);
        EnderecoModel enderecoModelSalvo = enderecoDao.save(enderecoModel);
        Integer id = enderecoModelSalvo.getId();
        enderecoModelSalvo.setDescricao("Outra descricao");
        enderecoDao.save(enderecoModel);
        EnderecoModel enderecoModelBanco = enderecoDao.findById(id).get();
        Assert.assertTrue(enderecoModelBanco.getDescricao().equals("Outra descricao"));
        Assert.assertTrue(enderecoModelBanco != enderecoModel);
    }

    @Test
    public void delete() throws SQLException{

    }

    @Test
    public void deleteById() throws SQLException {
        EnderecoModel endereco1 = new EnderecoModel(null,"Descrição", LogradouroEnum.Rua);
        EnderecoModel enderecoModelSalvo = enderecoDao.save(endereco1);
        Assert.assertTrue(enderecoModelSalvo.getId() != null);
        Integer id = enderecoModelSalvo.getId(); // Salvando o id
        enderecoDao.deleteById(enderecoModelSalvo.getId()); // Apagando a disciplina
        EnderecoModel enderecoModelBanco = enderecoDao.findById(id).get();
        Assert.assertTrue(endereco1 != enderecoModelBanco);
        Assert.assertFalse( enderecoModelBanco.getId() == id);

    }

    @Test
    public void deleteAll() throws SQLException {

    }


}