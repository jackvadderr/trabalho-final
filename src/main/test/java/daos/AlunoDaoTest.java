package daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.configs.CriaEntidades;
import br.sapiens.daos.AlunoDao;
import br.sapiens.daos.EnderecoDao;
import br.sapiens.models.AlunoModel;
import br.sapiens.models.CursoEnum;
import br.sapiens.models.Endereco;
import br.sapiens.models.LogradouroEnum;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class AlunoDaoTest {


    AlunoDao alunoDao = new AlunoDao();

    public AlunoDaoTest() throws SQLException {

    }

    @BeforeAll
    public static void init() throws SQLException {
        new CriaEntidades(new ConexaoSingleton().getConnection());
    }


    @Test
    public void save() throws SQLException {
        AlunoModel aluno = new AlunoModel(null,"Jack", CursoEnum.DIREITO);
        AlunoModel alunoSalvo = alunoDao.save(aluno);
        Assert.assertTrue(alunoSalvo.getId() != null);
    }

    @Test
    public void saveAll() throws SQLException {
        AlunoModel aluno1 = new AlunoModel(null,"Jack", CursoEnum.DIREITO);
        AlunoModel aluno2 = new AlunoModel(null,"Batata - 2", CursoEnum.SISTEMA);
        Iterable<AlunoModel> alunoSalvo = alunoDao.saveAll(List.of(aluno1, aluno2));
        alunoSalvo.forEach(it -> Assert.assertTrue(it.getId() != null));
    }

    @Test
    public void findById() throws SQLException {
        AlunoModel aluno1 = new AlunoModel(null,"Jack", CursoEnum.DIREITO);
        AlunoModel alunoSalvo = alunoDao.save(aluno1);
        Assert.assertTrue(alunoSalvo.getId() != null);
        Integer id = alunoSalvo.getId();
        AlunoModel alunoBanco = alunoDao.findById(id).get();
        Assert.assertTrue(aluno1 != alunoBanco);
        Assert.assertTrue( alunoBanco.getId() == id);
    }

}