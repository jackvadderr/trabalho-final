package daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.configs.CriaEntidades;
import br.sapiens.daos.AlunoDao;
import br.sapiens.models.*;
import br.sapiens.models.DateParse;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
        AlunoModel aluno = new AlunoModel(null,"Jack", new Date(), CursoEnum.DIREITO);
        AlunoModel alunoSalvo = alunoDao.save(aluno);
        Assert.assertTrue(alunoSalvo.getId() != null);
    }

    @Test
    public void saveAll() throws SQLException {
        // Alunos
        AlunoModel aluno1 = new AlunoModel("Jack", new Date(), CursoEnum.DIREITO);
        AlunoModel aluno2 = new AlunoModel("Batata", new Date(), CursoEnum.SISTEMA);
        // Salvando os dados
        Iterable<AlunoModel> alunoSalvo = alunoDao.saveAll(List.of(aluno1, aluno2));
        alunoSalvo.forEach(it -> Assert.assertTrue(it.getId() != null));
    }

    @Test
    public void findAll() throws SQLException{
        // Aluno
        AlunoModel aluno1 = new AlunoModel("Maria", new Date(), CursoEnum.SISTEMA);
        AlunoModel aluno2 = new AlunoModel( "Batata", new Date(), CursoEnum.DIREITO);
        // Salvando os dados
        Iterable<AlunoModel> alunoSalvo = alunoDao.saveAll(List.of(aluno1, aluno2));
        // Banco
        Iterator<AlunoModel> alunoBanco = alunoSalvo.iterator();
        // Fazendo listagem de todos as matriculas salvas no banco
        Iterator<AlunoModel> resultados = (Iterator<AlunoModel>) alunoDao.findAll();
        while (resultados.hasNext()) {
            AlunoModel registro = resultados.next();
            System.out.print("\n" +
                    registro.getId() + " | " +
                    registro.getNome() + " | " +
                    registro.getDataNascimento() + " | " +
                    registro.getCurso());
        }
        // TODO: Testes
        // O teste ?? o seguinte: comparar os itens
        // usando asserts com lambda
    }

    @Test
    public void findById() throws SQLException {
        // Aluno
        AlunoModel aluno1 = new AlunoModel("Jack", new Date(), CursoEnum.SISTEMA);
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
    public void findAllById() throws SQLException {
        // Armazenar dois alunos na tabela
        AlunoModel aluno1 = new AlunoModel("Jack", new Date(), CursoEnum.SISTEMA);
        AlunoModel aluno2 = new AlunoModel("Batata", new Date(), CursoEnum.DIREITO);
        Iterable<AlunoModel> alunoSalvo = alunoDao.saveAll(List.of(aluno1, aluno2));
        alunoSalvo.forEach(it -> Assert.assertTrue(it.getId() != null));
        // Criando lista de id
        List<Integer> alunosIds = new ArrayList<>();
        for (AlunoModel aluno: alunoSalvo ) {
            alunosIds.add(aluno.getId());
        }
        // Banco
        Iterable<AlunoModel> alunosBanco = alunoDao.findAllById(alunosIds);
        // Testes

        for (AlunoModel alunosalvado: alunoSalvo) {
            for (AlunoModel alunobanco: alunosBanco) {
                if(alunosalvado.getId() == alunobanco.getId()){
                    Assert.assertEquals(alunosalvado.getId(),alunobanco.getId());
                    Assert.assertEquals(alunosalvado.getNome(),alunobanco.getNome());
                    Assert.assertEquals(new DateParse().parse(alunosalvado.getDataNascimento()).toString(), alunobanco.getDataNascimento().toString());
                    Assert.assertEquals(alunosalvado.getCurso(),alunobanco.getCurso());
                }
            }
        }

    }

    @Test
    public void update() throws SQLException {
        // Aluno
        AlunoModel aluno1 = new AlunoModel("Jack", new Date(), CursoEnum.DIREITO);
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
        AlunoModel aluno = new AlunoModel("Jack", new Date(), CursoEnum.DIREITO);
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
        AlunoModel aluno = new AlunoModel("Jack", new Date(), CursoEnum.DIREITO);
        // Salvando os dados
        AlunoModel alunoSalvo = alunoDao.save(aluno);
        // Validando os dados
        Assert.assertNotNull(alunoSalvo.getId());
        // TODO: valida????o dos dados inseridos?

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
        AlunoModel aluno1 = new AlunoModel("Jack", new Date(), CursoEnum.DIREITO);
        AlunoModel aluno2 = new AlunoModel("Batata", new Date(),CursoEnum.SISTEMA);
        // Salvando os dados
        Iterable<AlunoModel> alunoSalvo = alunoDao.saveAll(List.of(aluno1, aluno2));
        // Validando disciplinas
        alunoSalvo.forEach(it -> Assert.assertTrue(it.getId() != null));
        // Salvando os ids
        List<Integer> alunoId = new ArrayList<>();
        alunoSalvo.forEach( x -> alunoId.add(x.getId()));
        // Apagando registro
        alunoDao.deleteAll(alunoSalvo);
        // FIXME: Teste
        for(Integer id: alunoId){
            assertThrows(SQLException.class, () ->  alunoDao.findById(id).get());
        }
    }
}
