package br.sapiens.daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlunoDao implements CrudRepository<AlunoModel, Integer>{

    private final Connection conn;
    private final String tabela =  "aluno";

    public AlunoDao() throws SQLException {
        this.conn = new ConexaoSingleton().getConnection();
    }

    @Override
    public <S extends AlunoModel> S save(S entity) throws SQLException {
        if (entity.getId() == null)
            return insertInto(entity);
        else
            return update(entity);
    }

    @Override
    public <S extends AlunoModel> Iterable<AlunoModel> saveAll(Iterable<S> entities) throws SQLException {
        ArrayList lista = new ArrayList();
        for(S entity : entities) {
            lista.add(save(entity));
        }
        return lista;
    }

    public Iterator<AlunoModel> findAll() throws SQLException{
        String sql = "select * from aluno";
        PreparedStatement stmt = conn.prepareStatement(sql);
        List<AlunoModel> resultado = new ArrayList();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultado.add(new AlunoModel(rs.getInt(1),rs.getString(2), rs.getString(3), CursoEnum.valueOf(rs.getString(4))));
            }
        }
        Iterator<AlunoModel> interetorResult = resultado.iterator();
        return interetorResult;
    }

    @Override
    public Optional<AlunoModel> findById(Integer id) throws SQLException {
        List<AlunoModel> resultados = (List<AlunoModel>) findAllById(List.of(id));
        if(resultados == null || resultados.size() != 1)
            throw new SQLException("Erro ao buscar valores, não existe somente um resultado! Size "+resultados.size());
        return Optional.ofNullable(resultados.get(0));
    }

    @Override
    public Iterable<AlunoModel> findAllById(Iterable<Integer> ids) throws SQLException {
        List<Integer> lista = new ArrayList();
        Iterator<Integer> interetor = ids.iterator();
        while(interetor.hasNext()){
            lista.add(interetor.next());
        }
        String sqlIN = lista.stream()
                .map(x -> String.valueOf(x))
                .collect(Collectors.joining(",", "(", ")"));
        String sql = "select * from aluno where id in(?)".replace("(?)", sqlIN);
        PreparedStatement stmt = conn.prepareStatement(sql);
        List<AlunoModel> resultado = new ArrayList();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultado.add(new AlunoModel(rs.getInt(1),rs.getString(2), rs.getString(3), CursoEnum.valueOf(rs.getString(4))));
            }
        }
        return resultado;
    }

    private <S extends AlunoModel> S update(S entity) throws SQLException {
        String sql = "UPDATE aluno SET nome = ?, dataNascimento = ?, curso = ?  WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,entity.getNome());
        pstmt.setString(2,entity.getDataNascimento());
        pstmt.setString(3,entity.getCurso().toString());
        pstmt.setString(4,entity.getId().toString());
        pstmt.executeUpdate();
        return entity;
    }

    private <S extends AlunoModel> S insertInto(S entity) throws SQLException {
        String sql = "Insert into aluno(nome, dataNascimento, curso) values(?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1,entity.getNome());
        pstmt.setString(2,entity.getDataNascimento());
        pstmt.setString(3,entity.getCurso().toString());
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0)
            throw new SQLException("Falha, nenhuma linha foi inserida");
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        generatedKeys.next();
        entity.setId(generatedKeys.getInt(1));
        return entity;
    }

    @Override
    public void delete(AlunoModel entity) throws SQLException {
        if (entity.getId() != null){
            deleteById(entity.getId());
        }
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM aluno WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id.toString());
        pstmt.executeUpdate();
    }

    @Override
    public void deleteAll(Iterable<? extends AlunoModel> entities) throws SQLException {
        List<Integer> lista = new ArrayList<>();
        Iterator<AlunoModel> interetor = (Iterator<AlunoModel>) entities.iterator();
        while(interetor.hasNext()){
            lista.add(interetor.next().getId());
        }
        String sqlIN = lista.stream()
                .map(x -> String.valueOf(x))
                .collect(Collectors.joining(",", "(", ")"));
        String sql = "DELETE FROM aluno WHERE id in(?)".replace("(?)", sqlIN);
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
    }

}
