package java.daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.configs.CriaEntidades;
import br.sapiens.daos.AlunoDao;
import br.sapiens.models.*;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        // Alunos
        AlunoModel aluno1 = new AlunoModel("Jack", "2022-11-09", CursoEnum.DIREITO);
        AlunoModel aluno2 = new AlunoModel("Batata", "2022-11-10", CursoEnum.SISTEMA);
        // Salvando os dados
        Iterable<AlunoModel> alunoSalvo = alunoDao.saveAll(List.of(aluno1, aluno2));
        alunoSalvo.forEach(it -> Assert.assertTrue(it.getId() != null));
    }

    @Test
    public void findAll() throws SQLException{
        // Aluno
        AlunoModel aluno1 = new AlunoModel("Maria", "2025-11-20", CursoEnum.SISTEMA);
        AlunoModel aluno2 = new AlunoModel( "Batata", "2025-11-29", CursoEnum.DIREITO);
        // Salvando os dados
        Iterable<AlunoModel> alunoSalvo = alunoDao.saveAll(List.of(aluno1, aluno2));
        // Banco
        Iterator<AlunoModel> alunoBanco = alunoSalvo.iterator();
        // Fazendo listagem de todos as matriculas salvas no banco
        Iterator<AlunoModel> resultados = alunoDao.findAll();
        while (resultados.hasNext()) {
            AlunoModel registro = resultados.next();
            System.out.print("\n" +
                    registro.getId() + " | " +
                    registro.getNome() + " | " +
                    registro.getDataNascimento() + " | " +
                    registro.getCurso());
        }
        // TODO: Testes
        // O teste é o seguinte: comparar os itens
        // usando asserts com lambda
    }

    @Test
    public void findById() throws SQLException {
        // Aluno
        AlunoModel aluno1 = new AlunoModel(null,"Jack", "2022-11-15", CursoEnum.SISTEMA);
        // Salvando os dados
        AlunoModel alunoSalvo = alunoDao.save(aluno1);
        // Validando os dados salvos
        Assert.assertNotNull(alunoSalvo.getId());
        // Salvando o id
        Integer alunoId = alunoSalvo.getId();
        // Testes
        AlunoModel alunoBanco = alunoDao.findById(alunoId).get();
        Assert.assertNotSame(aluno1, alunoBanco);
        Assert.assertSame(alunoBanco.getId(), alunoId);
    }

    @Test
    public void update() throws SQLException {
        // Aluno
        AlunoModel aluno1 = new AlunoModel(null,"Jack", "2022-11-20", CursoEnum.DIREITO);
        // Salvando os dados
        AlunoModel alunoSalvo = alunoDao.save(aluno1);
        // Salvando o id
        Integer alunoId = alunoSalvo.getId();
        // Alterando o dado: nome
        alunoSalvo.setNome("kcja");
        // Salvando o dado alterado
        alunoDao.save(aluno1);
        // Banco
        AlunoModel alunoBanco = alunoDao.findById(alunoId).get();
        // Testes
        Assert.assertEquals("kcja", alunoBanco.getNome());
        Assert.assertNotSame(alunoBanco, aluno1);
    }

    @Test
    public void delete() throws SQLException {
        // Aluno
        AlunoModel aluno = new AlunoModel(null,"Jack", "2022-11-12",CursoEnum.DIREITO);
        // Salvando os dados
        AlunoModel alunoSalvo = alunoDao.save(aluno);
        // Validando os dados
        Assert.assertNotNull(alunoSalvo.getId());
        // Salvando o id antes de apagar
        Integer alunoId = alunoSalvo.getId();
        // Apagando o registro
        alunoDao.delete(alunoSalvo);
        // Testes
        assertThrows(SQLException.class, () -> alunoDao.findById(alunoId).orElse(null));
    }

    @Test
    public void deleteById() throws SQLException {
        // Aluno
        AlunoModel aluno = new AlunoModel(null,"Jack", "2022-11-12",CursoEnum.DIREITO);
        // Salvando os dados
        AlunoModel alunoSalvo = alunoDao.save(aluno);
        // Validando os dados
        Assert.assertNotNull(alunoSalvo.getId());
        // TODO: validação dos dados inseridos?

        // Salvando o id antes de apagar
        Integer alunoId = alunoSalvo.getId();
        // Apagando o registro
        alunoDao.deleteById(alunoId);
        // Testes
        assertThrows(SQLException.class, () -> alunoDao.findById(alunoId).orElse(null));
    }

    @Test
    public void deleteAll() throws SQLException {
        // Alunos
        AlunoModel aluno1 = new AlunoModel("Jack", "2022-11-09", CursoEnum.DIREITO);
        AlunoModel aluno2 = new AlunoModel("Batata", "2022-11-10", CursoEnum.SISTEMA);
        // Salvando os dados
        Iterable<AlunoModel> alunoSalvo = alunoDao.saveAll(List.of(aluno1, aluno2));

        alunoSalvo.forEach(it -> Assert.assertTrue(it.getId() != null));

        alunoDao.deleteAll(alunoSalvo);
    }
}