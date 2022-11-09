package daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.configs.CriaEntidades;
import br.sapiens.daos.DisciplinaDao;
import br.sapiens.models.CursoEnum;
import br.sapiens.models.DisciplinaModel;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class DisciplinaDaoTest {


    DisciplinaDao disciplinaDao = new DisciplinaDao();

    public DisciplinaDaoTest() throws SQLException {

    }

    @BeforeAll
    public static void init() throws SQLException {
        new CriaEntidades(new ConexaoSingleton().getConnection());
    }


    @Test
    public void save() throws SQLException {
        DisciplinaModel disciplina = new DisciplinaModel(null,"Lógica", CursoEnum.SISTEMA);
        DisciplinaModel disciplinaSalvo = disciplinaDao.save(disciplina);
        Assert.assertTrue(disciplinaSalvo.getId() != null);
    }


    @Test
    public void saveAll() throws SQLException {
        DisciplinaModel disciplina1 = new DisciplinaModel(null,"Lógica", CursoEnum.SISTEMA);
        DisciplinaModel disciplina2 = new DisciplinaModel(null,"Jurisdição", CursoEnum.DIREITO);
        Iterable<DisciplinaModel> disciplinaSalvo = disciplinaDao.saveAll(List.of(disciplina1, disciplina2));
        disciplinaSalvo.forEach(it -> Assert.assertTrue(it.getId() != null));
    }

    @Test
    public void findById() throws SQLException {
        DisciplinaModel disciplina1 = new DisciplinaModel(null,"Lógica", CursoEnum.SISTEMA);
        DisciplinaModel disciplinaSalvo = disciplinaDao.save(disciplina1);
        Assert.assertTrue(disciplinaSalvo.getId() != null);
        Integer id = disciplinaSalvo.getId();
        DisciplinaModel disciplinaBanco = disciplinaDao.findById(id).get();
        Assert.assertTrue(disciplina1 != disciplinaBanco);
        Assert.assertTrue( disciplinaBanco.getId() == id);
    }


    @Test
    public void update() throws SQLException {
        DisciplinaModel disciplina1 = new DisciplinaModel(null,"Lógica", CursoEnum.SISTEMA);
        DisciplinaModel disciplinaSalvo = disciplinaDao.save(disciplina1);
        Integer id = disciplinaSalvo.getId();
        disciplinaSalvo.setDescricao("Programação");
        disciplinaDao.save(disciplina1);
        DisciplinaModel disciplinaBanco = disciplinaDao.findById(id).get();
        Assert.assertTrue(disciplinaBanco.getDescricao().equals("Programação"));
        Assert.assertTrue(disciplinaBanco != disciplina1);
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