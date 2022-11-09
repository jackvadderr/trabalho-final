package br.sapiens.daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.models.CursoEnum;
import br.sapiens.models.DisciplinaModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DisciplinaDao implements CrudRepository<DisciplinaModel,Integer>{

    private final Connection conn;

    public DisciplinaDao() throws SQLException {
        this.conn = new ConexaoSingleton().getConnection();
    }

    @Override
    public <S extends DisciplinaModel> S save(S entity) throws SQLException {
        if(entity.getId() == null)
            return insertInto(entity);
        else
            return update(entity);
    }

    @Override
    public <S extends DisciplinaModel> Iterable<DisciplinaModel> saveAll(Iterable<S> entities) throws SQLException {
        ArrayList lista = new ArrayList();
        for(S entity : entities) {
            lista.add(save(entity));
        }
        return lista;
    }

    @Override
    public Optional<DisciplinaModel> findById(Integer id) throws SQLException {
        List<DisciplinaModel> resultados = (List<DisciplinaModel>) findAllById(List.of(id));
        if(resultados == null || resultados.size() != 1)
            throw new SQLException("Erro ao buscar valores, não existe somente um resultado! Size "+resultados.size());
        return Optional.ofNullable(resultados.get(0));
    }

    @Override
    public Iterable<DisciplinaModel> findAllById(Iterable<Integer> ids) throws SQLException {
        List<Integer> lista = new ArrayList();
        Iterator<Integer> interetor = ids.iterator();
        while(interetor.hasNext()){
            lista.add(interetor.next());
        }
        String sqlIN = lista.stream()
                .map(x -> String.valueOf(x))
                .collect(Collectors.joining(",", "(", ")"));
        String sql = "select * from disciplina where id in(?)".replace("(?)", sqlIN);
        PreparedStatement stmt = conn.prepareStatement(sql);
        List<DisciplinaModel> resultado = new ArrayList();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultado.add(new DisciplinaModel(rs.getInt(1),rs.getString(2), CursoEnum.valueOf(rs.getString(3))));
            }
        }
        return resultado;
    }

    private <S extends DisciplinaModel> S insertInto(S entity) throws SQLException {
        String sql = "Insert into disciplina(descricao, curso) values(?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1,entity.getDescricao());
        pstmt.setString(2,entity.getCurso().toString());
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0)
            throw new SQLException("Falha, nenhuma linha foi inserida");
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        generatedKeys.next();
        entity.setId(generatedKeys.getInt(1));
        return entity;
    }

    private <S extends DisciplinaModel> S update(S entity) throws SQLException {
        String sql = "UPDATE disciplina SET descricao = ?, curso = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,entity.getDescricao());
        pstmt.setString(2,entity.getCurso().toString());
        pstmt.setString(3,entity.getId().toString());
        pstmt.executeUpdate();
        return entity;
    }

    @Override
    public void delete(DisciplinaModel entity) {

    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void deleteAll(Iterable<? extends DisciplinaModel> entities) {

    }


}
