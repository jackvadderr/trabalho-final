package br.sapiens.daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.models.AlunoModel;
import br.sapiens.models.DisciplinaModel;
import br.sapiens.models.MatriculaModel;
import br.sapiens.models.PeriodoEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MatriculaDao implements CrudRepository<MatriculaModel, String>{

    private final Connection conn;
    private final String tabela =  "matricula"; // TODO: trocar todas os sql

    public MatriculaDao() throws SQLException {
        this.conn = new ConexaoSingleton().getConnection();
    }


    @Override
    public <S extends MatriculaModel> S save(S entity) throws SQLException {
        return insertInto(entity);

    }

    @Override
    public <S extends MatriculaModel> Iterable<MatriculaModel> saveAll(Iterable<S> entities) throws SQLException {
        ArrayList lista = new ArrayList();
        for(S entity : entities) {
            lista.add(save(entity));
        }
        return lista;
    }

    public <S extends AlunoModel> S update(S entity) throws SQLException {
        throw new SQLException("Não é possível atualizar matricula.");
    }

    public Iterator<MatriculaModel> findAll() throws SQLException{
        String sql = "select * from Matricula";
        PreparedStatement stmt = conn.prepareStatement(sql);
        List<MatriculaModel> resultado = new ArrayList();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String alunoId = rs.getString(1).split("-")[0];
                String disciplinaId = rs.getString(1).split("-")[1];
                PeriodoEnum periodo = PeriodoEnum.valueOf(rs.getString(1).split("-")[2]);
                AlunoDao alunoDao = new AlunoDao();
                AlunoModel alunoBanco = alunoDao.findById(Integer.parseInt(alunoId)).get();
                DisciplinaDao disciplinaDao = new DisciplinaDao();
                DisciplinaModel disciplinaBanco = disciplinaDao.findById(Integer.parseInt(disciplinaId)).get();

                resultado.add(new MatriculaModel(disciplinaBanco, alunoBanco, periodo));
            }
        }
        Iterator<MatriculaModel> interetorResult = resultado.iterator();
        return interetorResult;
    }

    @Override
    public Optional<MatriculaModel> findById(String id) throws SQLException {
        List<MatriculaModel> resultados = (List<MatriculaModel>) findAllById(List.of(id));
        if(resultados == null || resultados.size() != 1)
            throw new SQLException("Erro ao buscar valores, não existe somente um resultado! Size "+resultados.size());
        return Optional.ofNullable(resultados.get(0));
    }

    @Override
    public Iterable<MatriculaModel> findAllById(Iterable<String> ids) throws SQLException {
        List<String> lista = new ArrayList();
        Iterator<String> interetor = ids.iterator();
        while(interetor.hasNext()){
            lista.add(interetor.next());
        }
        String sqlIN = lista.stream()
                .map(x -> String.valueOf(x))
                .collect(Collectors.joining(",", "('", "')"));
        String sql = "select * from Matricula where id in(?)".replace("(?)", sqlIN);
        PreparedStatement stmt = conn.prepareStatement(sql);
        List<MatriculaModel> resultado = new ArrayList();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String alunoId = rs.getString(1).split("-")[0];
                String disciplinaId = rs.getString(1).split("-")[1];
                PeriodoEnum periodo = PeriodoEnum.valueOf(rs.getString(1).split("-")[2]);
                AlunoDao alunoDao = new AlunoDao();
                AlunoModel alunoBanco = alunoDao.findById(Integer.parseInt(alunoId)).get();
                DisciplinaDao disciplinaDao = new DisciplinaDao();
                DisciplinaModel disciplinaBanco = disciplinaDao.findById(Integer.parseInt(disciplinaId)).get();

                resultado.add(new MatriculaModel(disciplinaBanco, alunoBanco, periodo));
            }
        }
        return resultado;
    }

    private <S extends MatriculaModel> S insertInto(S entity) throws SQLException {
        String sql = "Insert into Matricula( id, disciplina, aluno, periodo) values( ? , ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1,entity.getId());
        pstmt.setInt(2,entity.getDisciplina().getId());
        pstmt.setInt(3,entity.getAluno().getId());
        pstmt.setString(4,entity.getPeriodo().toString());
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0)
            throw new SQLException("Falha, nenhuma linha foi inserida");
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        generatedKeys.next();
        //entity.setPeriodo();
        return entity;
    }

    public <S extends MatriculaModel> S update(S entity) throws SQLException {
        throw new SQLException("Nâo é possível atualizar matrícula");
    }

    @Override
    public void delete(MatriculaModel entity) throws SQLException {
        if (entity.getId() == null)
            System.out.print("Id não encontrado");
        else if(entity.getId() != null) {
            deleteById(entity.getId());
            System.out.print("Entidade apagada!");
        }
    }

    @Override
    public void deleteById(String s) throws SQLException {
        String sql = "DELETE FROM matricula WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, s);
        pstmt.execute();
    }

    @Override
    public void deleteAll(Iterable<? extends MatriculaModel> entities) throws SQLException {
        ArrayList<MatriculaModel> lista = new ArrayList();
        Iterator<MatriculaModel> interetor = (Iterator<MatriculaModel>) entities.iterator();
        while (interetor.hasNext()) {
            lista.add(interetor.next());
        }
        String sqlIN = (String) lista.stream()
                .map(x -> x.getId().toString())
                .collect(Collectors.joining(",", "('", "')"));
        String sql = "delete from Matricula where id in(?)".replace("(?)",sqlIN);
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
    }
}
