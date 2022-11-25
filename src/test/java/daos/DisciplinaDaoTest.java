package daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.configs.CriaEntidades;
import br.sapiens.daos.DisciplinaDao;
import br.sapiens.models.AlunoModel;
import br.sapiens.models.CursoEnum;
import br.sapiens.models.DisciplinaModel;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void findAll() throws SQLException{
        // Aluno
        DisciplinaModel disciplina1 = new DisciplinaModel("Lógica", CursoEnum.SISTEMA);
        DisciplinaModel disciplina2 = new DisciplinaModel("Acidentes de trabalho", CursoEnum.DIREITO);
        // Salvando os dados
        Iterable<DisciplinaModel> disciplinaSalvo = disciplinaDao.saveAll(List.of(disciplina1, disciplina2));
        // Banco
        Iterator<DisciplinaModel> disciplinaBanco = disciplinaSalvo.iterator();
        // Fazendo listagem de todos as matriculas salvas no banco
        Iterator<DisciplinaModel> resultados = disciplinaDao.findAll();
        while (resultados.hasNext()) {
            DisciplinaModel registro = resultados.next();
            System.out.print("\n" +
                    registro.getId() + " | " +
                    registro.getDescricao() + " | " +
                    registro.getCurso());
        }
        // TODO: Testes
        // O teste é o seguinte: comparar os itens
        // usando asserts com lambda
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
    public void findAllById() throws SQLException {
        // Armazenar dois alunos na tabela
        DisciplinaModel disciplina1 = new DisciplinaModel(null,"Lógica", CursoEnum.SISTEMA);
        DisciplinaModel disciplina2 = new DisciplinaModel(null,"Lógica", CursoEnum.SISTEMA);
        Iterable<DisciplinaModel> disciplinaSalvo = disciplinaDao.saveAll(List.of(disciplina1, disciplina2));
        disciplinaSalvo.forEach(it -> Assert.assertTrue(it.getId() != null));
        // Criando lista de id
        List<Integer> alunosIds = new ArrayList<>();
        for (DisciplinaModel disciplina: disciplinaSalvo ) {
            alunosIds.add(disciplina.getId());
        }
        // Banco
        Iterable<DisciplinaModel> alunosBanco = disciplinaDao.findAllById(alunosIds);
        // Testes

        for (DisciplinaModel disciplinado: disciplinaSalvo) {
            for (DisciplinaModel disciplinaDoBanco: alunosBanco) {
                if(disciplinado.getId() == disciplinaDoBanco.getId()){
                    Assert.assertEquals(disciplinado.getId(),disciplinaDoBanco.getId());
                    Assert.assertEquals(disciplinado.getDescricao(),disciplinaDoBanco.getDescricao());
                    Assert.assertEquals(disciplinado.getCurso(),disciplinaDoBanco.getCurso());
                }
            }
        }

    }

    @Test
    public void update() throws SQLException {
        DisciplinaModel disciplina1 = new DisciplinaModel("Lógica", CursoEnum.SISTEMA);
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
        // Aluno
        DisciplinaModel disciplina1 = new DisciplinaModel(null, "Lógica", CursoEnum.SISTEMA);
        // Salvando os dados
        DisciplinaModel disciplinaSalvo = disciplinaDao.save(disciplina1);
        // Validando os dados
        Assert.assertNotNull(disciplinaSalvo.getId());
        // Salvando o id antes de apagar
        Integer disciplinaId = disciplinaSalvo.getId();
        // Apagando o registro
        disciplinaDao.delete(disciplinaSalvo);
        // Testes
        assertThrows(SQLException.class, () -> disciplinaDao.findById(disciplinaId).get());
    }

    @Test
    public void deleteById() throws SQLException {
        // Disciplina
        DisciplinaModel disciplina1 = new DisciplinaModel(null, "Lógica", CursoEnum.SISTEMA);
        // Salvando os dados
        DisciplinaModel disciplinaSalvo = disciplinaDao.save(disciplina1);
        // Validando os dados
        Assert.assertNotNull(disciplinaSalvo.getId());
        // TODO: validação dos dados inseridos

        // Salvando o id antes de apagar
        Integer alunoId = disciplinaSalvo.getId();
        // Apagando o registro
        disciplinaDao.deleteById(alunoId);

        Iterator<DisciplinaModel> resultados = disciplinaDao.findAll();
        while (resultados.hasNext()) {
            DisciplinaModel registro = resultados.next();
            System.out.print("\n" +
                    registro.getId() + " | " +
                    registro.getDescricao() + " | " +
                    registro.getCurso());
        }

        // Testes
        assertThrows(SQLException.class, () -> disciplinaDao.findById(alunoId).orElse(null));
    }

    @Test
    public void deleteAll() throws SQLException {
        // Alunos
        DisciplinaModel disciplina1 = new DisciplinaModel("2022-11-09", CursoEnum.DIREITO);
        DisciplinaModel disciplina2 = new DisciplinaModel( "2022-11-10", CursoEnum.SISTEMA);
        // Salvando os dados
        Iterable<DisciplinaModel> disciplinaSalva = disciplinaDao.saveAll(List.of(disciplina1, disciplina2));
        // Validando disciplinas
        disciplinaSalva.forEach(it -> Assert.assertTrue(it.getId() != null));
        // Salvando os ids
        List<Integer> disciplinaId = new ArrayList<>();
        disciplinaSalva.forEach( x -> disciplinaId.add(x.getId()));
        // Apagando registro
        disciplinaDao.deleteAll(disciplinaSalva);

        // Teste
        for(Integer id: disciplinaId){
            assertThrows(SQLException.class, () ->  disciplinaDao.findById(id).get());
        }
    }
}