package br.sapiens.daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.models.DisciplinaModel;
import br.sapiens.models.Endereco;
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
