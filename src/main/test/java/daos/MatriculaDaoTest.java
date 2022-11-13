package daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.configs.CriaEntidades;
import br.sapiens.daos.AlunoDao;
import br.sapiens.daos.DisciplinaDao;
import br.sapiens.daos.MatriculaDao;
import br.sapiens.models.*;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class MatriculaDaoTest {


    MatriculaDao matriculaDao = new MatriculaDao();
    AlunoDao alunoDao = new AlunoDao();
    DisciplinaDao disciplinaDao = new DisciplinaDao();

    public MatriculaDaoTest() throws SQLException {

    }

    @BeforeAll
    public static void init() throws SQLException {
        new CriaEntidades(new ConexaoSingleton().getConnection());
    }


    @Test
    public void save() throws SQLException {
        AlunoModel aluno1 = new AlunoModel("Jack", "2022-08-29", CursoEnum.SISTEMA);
        AlunoModel alunoSalvo = alunoDao.save(aluno1);

        DisciplinaModel disciplina1 = new DisciplinaModel("Descrição 01", CursoEnum.SISTEMA);
        DisciplinaModel disciplinaSalva = disciplinaDao.save(disciplina1);

        MatriculaModel matricula1 = new MatriculaModel(disciplina1, aluno1, PeriodoEnum.SEGUNDO);
        MatriculaModel matriculaSalva = matriculaDao.save(matricula1);

        Assert.assertTrue(matriculaSalva.getId() != null);
    }

    @Test
    public void findById() throws SQLException {

    }

    @Test
    public void update() throws SQLException {

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