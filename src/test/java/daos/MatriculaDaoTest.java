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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        // Aluno
        AlunoModel aluno1 = new AlunoModel("Jack", "2022-08-29", CursoEnum.SISTEMA);
        // Disciplina
        DisciplinaModel disciplina1 = new DisciplinaModel("Descrição 01", CursoEnum.SISTEMA);
        // Matricula
        MatriculaModel matricula1 = new MatriculaModel(disciplina1, aluno1, PeriodoEnum.SEGUNDO);
        // Salvando os dados
        alunoDao.save(aluno1);
        disciplinaDao.save(disciplina1);
        MatriculaModel matriculaSalva = matriculaDao.save(matricula1);
        //

        Assert.assertNotNull(matriculaSalva.getId());
    }

    @Test
    public void saveAll() throws SQLException {
        // Aluno
        AlunoModel aluno1 = new AlunoModel(null, "Maria", "2025-11-20", CursoEnum.SISTEMA);
        AlunoModel aluno2 = new AlunoModel(null, "Batata", "2025-11-29", CursoEnum.DIREITO);
        // Disciplina
        DisciplinaModel disciplina1 = new DisciplinaModel(null, "Lógica", CursoEnum.SISTEMA);
        DisciplinaModel disciplina2 = new DisciplinaModel(null, "Direito trabalhista", CursoEnum.DIREITO);
        // Matricula
        MatriculaModel matricula = new MatriculaModel(disciplina1, aluno1, PeriodoEnum.PRIMEIRO);
        MatriculaModel matricula2 = new MatriculaModel(disciplina2, aluno2, PeriodoEnum.SEGUNDO);
        // Salvando os dados
        alunoDao.saveAll(List.of(aluno1, aluno2));
        disciplinaDao.saveAll(List.of(disciplina1, disciplina2));
        Iterable<MatriculaModel> matriculaSalva = matriculaDao.saveAll(List.of(matricula, matricula2));
        // Testes
        matriculaSalva.forEach(Assert::assertNotNull);
    }

    @Test
    public void update() throws SQLException {
        // Aluno
        AlunoModel aluno = new AlunoModel(null, "Maria", "2025-11-29", CursoEnum.SISTEMA);
        // Disciplina
        DisciplinaModel disciplina = new DisciplinaModel(null, "Lógica", CursoEnum.SISTEMA);
        // Matricula
        MatriculaModel matricula = new MatriculaModel(disciplina, aluno, PeriodoEnum.PRIMEIRO);
        // Salvando os dados
        Iterable<AlunoModel> alunoSalvo = alunoDao.saveAll(List.of(aluno));
        Iterable<DisciplinaModel> disciplinaSalvo = disciplinaDao.saveAll(List.of(disciplina));
        MatriculaModel matriculaSalva = matriculaDao.save(matricula);
        assertThrows(SQLException.class, () -> matriculaDao.update(matricula));

    }

    @Test
    public void findAll() throws SQLException{
        // Aluno
        AlunoModel aluno1 = new AlunoModel(null, "Maria", "2025-11-20", CursoEnum.SISTEMA);
        AlunoModel aluno2 = new AlunoModel(null, "Batata", "2025-11-29", CursoEnum.DIREITO);
        // Disciplina
        DisciplinaModel disciplina1 = new DisciplinaModel(null, "Lógica", CursoEnum.SISTEMA);
        DisciplinaModel disciplina2 = new DisciplinaModel(null, "Direito trabalhista", CursoEnum.DIREITO);
        // Matricula
        MatriculaModel matricula = new MatriculaModel(disciplina1, aluno1, PeriodoEnum.PRIMEIRO);
        MatriculaModel matricula2 = new MatriculaModel(disciplina2, aluno2, PeriodoEnum.PRIMEIRO);
        // Salvando os dados
        Iterable<AlunoModel> alunoSalvo = alunoDao.saveAll(List.of(aluno1, aluno2));
        Iterable<DisciplinaModel> disciplinaSalvo = disciplinaDao.saveAll(List.of(disciplina1, disciplina2));
        Iterable<MatriculaModel> matriculaSalva = matriculaDao.saveAll(List.of(matricula, matricula2));
        // Banco
        Iterator<MatriculaModel> matriculaBanco = matriculaSalva.iterator();
        // Fazendo listagem de todos as matriculas salvas no banco
        Iterator<MatriculaModel> resultados = matriculaDao.findAll();
        while (resultados.hasNext()) {
            MatriculaModel registro = resultados.next();
            System.out.print("\n" +
                             registro.getDisciplina().getId() + " | " +
                             registro.getAluno().getId() + " | " +
                             registro.getPeriodo() + " | " +
                             registro.getId());
        }
        // TODO: Testes
        // O teste é o seguinte: comparar os itens de matriculaSalva com o de resultados
        // usando asserts com lambda
        // TODO
        Assert.assertTrue(false);
    }

    @Test
    public void findById() throws SQLException {
        // Aluno
        AlunoModel aluno = new AlunoModel(null, "Maria", "2025-11-29", CursoEnum.SISTEMA);
        // Disciplina
        DisciplinaModel disciplina = new DisciplinaModel(null, "Lógica", CursoEnum.SISTEMA);
        // Matricula
        MatriculaModel matricula = new MatriculaModel(disciplina, aluno, PeriodoEnum.PRIMEIRO);
        // Salvando os dados
        Iterable<AlunoModel> alunoSalvo = alunoDao.saveAll(List.of(aluno));
        Iterable<DisciplinaModel> disciplinaSalvo = disciplinaDao.saveAll(List.of(disciplina));
        MatriculaModel matriculaSalva = matriculaDao.save(matricula);
        // Validando os dados salvos
        alunoSalvo.forEach(it -> Assert.assertNotNull(it.getId()));
        // Pegando os ids
        String matriculaId = matriculaSalva.getId();
        // Banco
        MatriculaModel matriculaBanco = matriculaDao.findById(matriculaId).orElse(null);
        // Teste
        Assert.assertNotSame(matricula, matriculaBanco);
        Assert.assertEquals(matriculaBanco.getId(), matriculaId);
    }

    @Test
    public void findAllById() throws SQLException {
        // TODO
    }

    @Test
    public void delete() throws SQLException {
        // Aluno
        AlunoModel aluno = new AlunoModel(null, "Maria", "2025-11-29", CursoEnum.SISTEMA);
        // Disciplina
        DisciplinaModel disciplina = new DisciplinaModel(null, "Lógica", CursoEnum.SISTEMA);
        // Matricula
        MatriculaModel matricula = new MatriculaModel(disciplina, aluno, PeriodoEnum.PRIMEIRO);
        // Salvando os dados
        Iterable<AlunoModel> alunoSalvo = alunoDao.saveAll(List.of(aluno));
        Iterable<DisciplinaModel> disciplinaSalvo = disciplinaDao.saveAll(List.of(disciplina));
        MatriculaModel matriculaSalva = matriculaDao.save(matricula);
        // Pegando os ids
        String matriculaId = matriculaSalva.getId();
        // Dados do Banco antes de apagar
        MatriculaModel matriculaBanco = matriculaDao.findById(matriculaId).orElse(null);
        // Validando se os dados salvos
        Assert.assertNotNull(matriculaSalva.getId());
        Assert.assertNotSame(matricula, matriculaBanco);
        Assert.assertEquals(matriculaBanco.getId(), matriculaId);
        // Apagando a matricula
        matriculaDao.delete(matriculaSalva);
        // Testes
        assertThrows(SQLException.class, () -> matriculaDao.findById(matriculaId).orElse(null));
    }

    @Test
    public void deleteById() throws SQLException {
        // Aluno
        AlunoModel aluno = new AlunoModel( "Maria", "2025-11-29", CursoEnum.SISTEMA);
        // Disciplina
        DisciplinaModel disciplina = new DisciplinaModel("Lógica", CursoEnum.SISTEMA);
        // Matricula
        MatriculaModel matricula = new MatriculaModel(disciplina, aluno, PeriodoEnum.PRIMEIRO);
        // Salvando os dados
        Iterable<AlunoModel> alunoSalvo = alunoDao.saveAll(List.of(aluno));
        Iterable<DisciplinaModel> disciplinaSalvo = disciplinaDao.saveAll(List.of(disciplina));
        MatriculaModel matriculaSalva = matriculaDao.save(matricula);
        // Pegando os ids
        String matriculaId = matriculaSalva.getId();
        // Dados do Banco antes de apagar
        MatriculaModel matriculaBanco = matriculaDao.findById(matriculaId).orElse(null);
        // Validando se os dados salvos
        Assert.assertNotSame(matricula, matriculaBanco);
        //assert matriculaBanco != null;
        Assert.assertEquals(matriculaBanco.getId(), matriculaId);
        // Apagando matricula pelo id
        matriculaDao.deleteById(matriculaSalva.getId());
        // Testes
        assertThrows(SQLException.class, () -> matriculaDao.findById(matriculaId).orElse(null));
    }

    @Test
    public void deleteAll() throws SQLException {
        //CRIANDO E SALVANDO ALUNO DISCIPLINA E MATRICULA

        //DISCIPLINA
        DisciplinaModel disciplina = new DisciplinaModel(null, "MATEMÁTICA", CursoEnum.SISTEMA);

        Iterable<DisciplinaModel> disciplinaSalva =disciplinaDao.saveAll(List.of(disciplina));
        //ALUNO
        AlunoModel aluno = new AlunoModel(null,"Maria", "2022-05-12" , CursoEnum.SISTEMA);
        Iterable<AlunoModel> alunoSalvo = alunoDao.saveAll(List.of(aluno));
        //MATRICULA
        MatriculaModel matricula = new MatriculaModel(disciplina, aluno, PeriodoEnum.PRIMEIRO);
        MatriculaModel matricula2 = new MatriculaModel(disciplina, aluno, PeriodoEnum.SEGUNDO);
        Iterable<MatriculaModel> matriculaSalva = matriculaDao.saveAll(List.of(matricula));


        // Criando lista de id
        List<String> matriculaIds = new ArrayList<String>();
        for (MatriculaModel i: matriculaSalva) {
            matriculaIds.add(i.getId());
        }
        System.out.println(matriculaIds);

        matriculaDao.deleteAll(matriculaSalva);
        //assertThrows(SQLException.class, () -> matriculaDao.findAllById(matriculaIds));
    }
}