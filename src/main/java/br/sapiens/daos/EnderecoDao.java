package br.sapiens.daos;

import br.sapiens.configs.ConexaoSingleton;
import br.sapiens.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class EnderecoDao implements CrudRepository<EnderecoModel,Integer> {

    private final Connection conn;
    private final String tabela =  "endereco"; // TODO: trocar todas os sql

    public EnderecoDao() throws SQLException {
        this.conn = new ConexaoSingleton().getConnection();
    }

    @Override
    public <S extends EnderecoModel> S save(S entity) throws SQLException {
        if(entity.getId() == null)
            return insertInto(entity);
        else
            return update(entity);
    }

    private <S extends EnderecoModel> S update(S entity) throws SQLException {
        String sql = "UPDATE endereco SET descricao = ?, logradouro = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,entity.getDescricao());
        pstmt.setString(2,entity.getLogradouro().toString());
        pstmt.setString(3,entity.getId().toString());
        pstmt.executeUpdate();
        return entity;
    }

    private <S extends EnderecoModel> S insertInto(S entity) throws SQLException {
        String sql = "Insert into endereco(descricao, logradouro) values(?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        //pstmt.setString(1, this.tabela);
        pstmt.setString(1,entity.getDescricao());
        pstmt.setString(2,entity.getLogradouro().toString());
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0)
            throw new SQLException("Falha, nenhuma linha foi inserida");
        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        generatedKeys.next();
        entity.setId(generatedKeys.getInt(1));
        return entity;
    }

    @Override
    public <S extends EnderecoModel> Iterable<EnderecoModel> saveAll(Iterable<S> entities) throws SQLException {
        ArrayList lista = new ArrayList();
        for(S entity : entities) {
            lista.add(save(entity));
        }
        return lista;
    }

    public Iterator<EnderecoModel> findAll() throws SQLException{
        String sql = "select * from aluno";
        PreparedStatement stmt = conn.prepareStatement(sql);
        List<EnderecoModel> resultado = new ArrayList();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultado.add(new EnderecoModel(rs.getInt(1),rs.getString(2), LogradouroEnum.valueOf(rs.getString(3))));
            }
        }
        Iterator<EnderecoModel> interetorResult = resultado.iterator();
        return interetorResult;
    }

    @Override
    public Optional<EnderecoModel> findById(Integer id) throws SQLException {
        List<EnderecoModel> resultados = findAllById(List.of(id));
        if(resultados == null || resultados.size() != 1)
            throw new SQLException("Erro ao buscar valores, não existe somente um resultado! Size "+resultados.size());
        return Optional.ofNullable(resultados.get(0));
    }

    @Override
    public List<EnderecoModel> findAllById(Iterable<Integer> ids) throws SQLException {
        List<Integer> lista = new ArrayList();
        Iterator<Integer> interetor = ids.iterator();
        while(interetor.hasNext()){
            lista.add(interetor.next());
        }
        String sqlIN = lista.stream()
                .map(x -> String.valueOf(x))
                .collect(Collectors.joining(",", "(", ")"));
        String sql = "select * from endereco where id in(?)".replace("(?)", sqlIN);
        PreparedStatement stmt = conn.prepareStatement(sql);
        List<EnderecoModel> resultado = new ArrayList();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultado.add(new EnderecoModel(rs.getInt(1),rs.getString(2), LogradouroEnum.valueOf(rs.getString(3))));
            }
        }
        return resultado;
    }

    @Override
    public void delete(EnderecoModel entity) throws SQLException {
        String sql = "DELETE FROM endereco WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, entity.getId().toString());
        pstmt.execute();
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM endereco WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id.toString());
        pstmt.executeUpdate();
    }

    @Override
    public void deleteAll(Iterable<? extends EnderecoModel> entities) throws SQLException {
        List<Integer> lista = new ArrayList();
        Iterator<EnderecoModel> interetor = (Iterator<EnderecoModel>) entities.iterator();
        while(interetor.hasNext()){
            lista.add(interetor.next().getId());
        }
        String sqlIN = lista.stream()
                .map(x -> String.valueOf(x))
                .collect(Collectors.joining(",", "(", ")"));
        //String sql = "select * from aluno where id in(?)".replace("(?)", sqlIN);
        String sql = "DELETE FROM endereco WHERE id in(?)".replace("(?)", sqlIN);
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
    }


}