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

public class MatriculaDao implements CrudRepository<MatriculaModel,Integer>{

    private final Connection conn;

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

    @Override
    public Optional<MatriculaModel> findById(Integer integer) throws SQLException {
        List<MatriculaModel> resultados = (List<MatriculaModel>) findAllById(List.of(integer));
        if(resultados == null || resultados.size() != 1)
            throw new SQLException("Erro ao buscar valores, não existe somente um resultado! Size "+resultados.size());
        return Optional.ofNullable(resultados.get(0));
    }

    @Override
    public Iterable<MatriculaModel> findAllById(Iterable<Integer> integers) throws SQLException {
        List<Integer> lista = new ArrayList();
        Iterator<Integer> interetor = integers.iterator();
        while(interetor.hasNext()){
            lista.add(interetor.next());
        }
        String sqlIN = lista.stream()
                .map(x -> String.valueOf(x))
                .collect(Collectors.joining(",", "(", ")"));
        String sql = "select * from Matricula where id in(?)".replace("(?)", sqlIN);
        PreparedStatement stmt = conn.prepareStatement(sql);
        List<MatriculaModel> resultado = new ArrayList();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultado.add(new MatriculaModel((DisciplinaModel) rs.getObject(2), (AlunoModel) rs.getObject(3),PeriodoEnum.valueOf(rs.getString(1))));

            }
        }
        return resultado;
    }

    private <S extends MatriculaModel> S insertInto(S entity) throws SQLException {
        String sql = "Insert into Matricula( id, disciplina, aluno, periodo) values(  ? , ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1,entity.getId().toString());
        pstmt.setInt(2,entity.getDisciplina().getId());
        pstmt.setInt(3,entity.getAluno().getId());
        pstmt.setString(4,entity.getPeriodo().toString());
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0)
            throw new SQLException("Falha, nenhuma linha foi inserida");
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        generatedKeys.next();
        entity.setPeriodo();
        return entity;
    }

    private <S extends MatriculaModel> S update(S entity) throws SQLException {
        String sql = "UPDATE matricula SET disciplina = ?, periodo = ?, aluno = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,entity.getDisciplina().getId().toString());
        pstmt.setString(2,entity.getPeriodo().toString());
        pstmt.setString(3,entity.getAluno().getId().toString());
        pstmt.setString(4,entity.getId().toString());
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
