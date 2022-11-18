package br.sapiens.daos;

import br.sapiens.models.AlunoModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T,ID> {

    <S extends T> S save(S entity) throws SQLException;
    //T save(T entity) throws SQLException;

    <S extends T> Iterable<T> saveAll(Iterable<S> entities) throws SQLException;

    Optional<T> findById(ID id) throws SQLException;

    Iterable<T> findAllById(Iterable<ID> ids) throws SQLException;

    void delete(T entity) throws SQLException;

    void deleteById(ID id) throws SQLException;

    void deleteAll(Iterable<? extends T> entities) throws SQLException;
}
