package java.daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.configs.CriaEntidades;
import br.sapiens.daos.EnderecoDao;
import br.sapiens.models.AlunoModel;
import br.sapiens.models.CursoEnum;
import br.sapiens.models.EnderecoModel;
import br.sapiens.models.LogradouroEnum;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        // Enderecos
        EnderecoModel endereco1 = new EnderecoModel("Descrição", LogradouroEnum.Rua);
        // Salvando os dados
        EnderecoModel enderecoSalvo = enderecoDao.save(endereco1);
        // Testes
        Assert.assertTrue(enderecoSalvo.getId() != null);
    }

    @Test
    public void saveAll() throws SQLException {
        EnderecoModel endereco1 = new EnderecoModel(null,"Descrição - 1", LogradouroEnum.Rua);
        EnderecoModel endereco2 = new EnderecoModel(null,"Descrição - 2", LogradouroEnum.Rua);
        Iterable<EnderecoModel> enderecoSalvo = enderecoDao.saveAll(List.of(endereco1, endereco2));
        enderecoSalvo.forEach(it -> Assert.assertTrue(it.getId() != null));
    }

    @Test
    public void findAll() throws SQLException{
        // Endereco
        EnderecoModel endereco1 = new EnderecoModel("Descrição", LogradouroEnum.Rua);
        EnderecoModel endereco2 = new EnderecoModel("Descrição", LogradouroEnum.Avenida);
        // Salvando os dados
        Iterable<EnderecoModel> alunoSalvo = enderecoDao.saveAll(List.of(endereco1, endereco2));
        // Banco
        Iterator<EnderecoModel> alunoBanco = alunoSalvo.iterator();
        // Fazendo listagem de todos as matriculas salvas no banco
        Iterator<EnderecoModel> resultados = enderecoDao.findAll();
        while (resultados.hasNext()) {
            EnderecoModel registro = resultados.next();
            System.out.print("\n" +
                    registro.getId() + " | " +
                    registro.getDescricao()+ " | " +
                    registro.getLogradouro());
        }
        // TODO: Testes
        // O teste é o seguinte: comparar os itens
        // usando asserts com lambda
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
        Assert.assertEquals("Outra descricao", enderecoModelBanco.getDescricao());
        Assert.assertNotSame(enderecoModelBanco, enderecoModel);
    }

    @Test
    public void delete() throws SQLException {
        // Aluno
        EnderecoModel endereco1 = new EnderecoModel("Jack", LogradouroEnum.Rua);
        // Salvando os dados
        EnderecoModel alunoSalvo = enderecoDao.save(endereco1);
        // Validando os dados
        Assert.assertNotNull(alunoSalvo.getId());
        // Salvando o id antes de apagar
        Integer enderecoId = alunoSalvo.getId();
        // Apagando o registro
        enderecoDao.delete(alunoSalvo);
        // Testes
        assertThrows(SQLException.class, () -> enderecoDao.findById(enderecoId).orElse(null));
    }

    @Test
    public void deleteById() throws SQLException {
        // Endereço
        EnderecoModel endereco1 = new EnderecoModel("Jack", LogradouroEnum.Rua);
        // Salvando os dados
        EnderecoModel enderecoSalvo = enderecoDao.save(endereco1);
        // Validando os dados
        Assert.assertNotNull(enderecoSalvo.getId());
        // TODO: validação dos dados inseridos?

        // Salvando o id antes de apagar
        Integer enderecoId = enderecoSalvo.getId();
        // Apagando o registro
        enderecoDao.deleteById(enderecoId);
        // Testes
        assertThrows(SQLException.class, () -> enderecoDao.findById(enderecoId).orElse(null));
    }

    @Test
    public void deleteAll() throws SQLException {
        // Alunos
        EnderecoModel endereco1 = new EnderecoModel("Jack", LogradouroEnum.Rua);
        EnderecoModel endereco2 = new EnderecoModel("Jack", LogradouroEnum.Rua);
        // Salvando os dados
        Iterable<EnderecoModel> enderecoSalvo = enderecoDao.saveAll(List.of(endereco1, endereco2));
        // Validando os dados salvos
        enderecoSalvo.forEach(it -> Assert.assertTrue(it.getId() != null));
        // Apagando os dados salvos
        enderecoDao.deleteAll(enderecoSalvo);
    }
}