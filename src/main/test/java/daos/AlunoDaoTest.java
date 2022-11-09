package daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.configs.CriaEntidades;
import br.sapiens.daos.AlunoDao;
import br.sapiens.models.AlunoModel;
import br.sapiens.models.CursoEnum;
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
        AlunoModel aluno = new AlunoModel(null,"Jack", "2022-11-12",CursoEnum.DIREITO);
        AlunoModel alunoSalvo = alunoDao.save(aluno);
        Assert.assertTrue(alunoSalvo.getId() != null);
    }

    @Test
    public void saveAll() throws SQLException {
        AlunoModel aluno1 = new AlunoModel(null,"Jack", "2022-11-09", CursoEnum.DIREITO);
        AlunoModel aluno2 = new AlunoModel(null,"Batata", "2022-11-10", CursoEnum.SISTEMA);
        Iterable<AlunoModel> alunoSalvo = alunoDao.saveAll(List.of(aluno1, aluno2));
        alunoSalvo.forEach(it -> Assert.assertTrue(it.getId() != null));
    }

    @Test
    public void findById() throws SQLException {
        AlunoModel aluno1 = new AlunoModel(null,"Jack", "2022-11-15", CursoEnum.SISTEMA);
        AlunoModel alunoSalvo = alunoDao.save(aluno1);
        Assert.assertTrue(alunoSalvo.getId() != null);

        Integer id = alunoSalvo.getId();
        AlunoModel alunoBanco = alunoDao.findById(id).get();
        Assert.assertTrue(aluno1 != alunoBanco);
        Assert.assertTrue( alunoBanco.getId() == id);
    }

    @Test
    public void update() throws SQLException {
        AlunoModel aluno1 = new AlunoModel(null,"Jack", "2022-11-20", CursoEnum.DIREITO);
        AlunoModel alunoSalvo = alunoDao.save(aluno1);
        Integer id = alunoSalvo.getId();
        alunoSalvo.setNome("kcja");
        alunoDao.save(aluno1);
        AlunoModel alunoBanco = alunoDao.findById(id).get();
        Assert.assertTrue(alunoBanco.getNome().equals("kcja"));
        Assert.assertTrue(alunoBanco != aluno1);
    }

    @Test
    public void delete() throws SQLException {

    }

    @Test
    public void deleteById() throws SQLException {

    }

    @Test
    public void deleteAll() throws SQLException {

    }
}