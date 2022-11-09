package br.sapiens.daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.models.MatriculaModel;

import java.sql.*;
import java.util.Optional;

public class MatriculaDao implements CrudRepository<MatriculaModel,Integer>{

    private final Connection conn;

    public MatriculaDao() throws SQLException {
        this.conn = new ConexaoSingleton().getConnection();
    }


    @Override
    public <S extends MatriculaModel> S save(S entity) throws SQLException {
        if(entity.getId() == null)
            return insertInto(entity);
        else
            return update(entity);
    }

    @Override
    public <S extends MatriculaModel> Iterable<MatriculaModel> saveAll(Iterable<S> entities) throws SQLException {
        return null;
    }

    @Override
    public Optional<MatriculaModel> findById(Integer integer) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Iterable<MatriculaModel> findAllById(Iterable<Integer> integers) throws SQLException {
        return null;
    }

    private <S extends MatriculaModel> S insertInto(S entity) throws SQLException {
        String sql = "Insert into matricula(disciplina, aluno, periodo) values(?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1,entity.getDisciplina().toString());
        pstmt.setString(2,entity.getAluno().toString());
        pstmt.setString(3,entity.getPeriodo().toString());
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0)
            throw new SQLException("Falha, nenhuma linha foi inserida");
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        generatedKeys.next();
        entity.setId(generatedKeys.getInt(1));
        return entity;
    }

    private <S extends MatriculaModel> S update(S entity) throws SQLException {
        String sql = "UPDATE matricula SET disciplina = ?, periodo = ?, aluno = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,entity.getDisciplina().toString());
        pstmt.setString(2,entity.getPeriodo().toString());
        pstmt.setString(2,entity.getAluno().toString());
        pstmt.setString(3,entity.getId().toString());
        pstmt.executeUpdate();
        return entity;
    }

    @Override
    public void delete(MatriculaModel entity) {

    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void deleteAll(Iterable<? extends MatriculaModel> entities) {

    }
}
