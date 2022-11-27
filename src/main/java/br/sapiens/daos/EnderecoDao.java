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
        String sql = "UPDATE endereco SET descricao = ?, logradouro = ?, data = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,entity.getDescricao());
        pstmt.setString(2,entity.getLogradouro().toString());
        pstmt.setDate(3, new DateParse().parse(entity.getData()));
        pstmt.setString(4,entity.getId().toString());

        pstmt.executeUpdate();
        return entity;
    }

    private <S extends EnderecoModel> S insertInto(S entity) throws SQLException {
        String sql = "Insert into endereco(descricao, logradouro, data) values(?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1,entity.getDescricao());
        pstmt.setString(2,entity.getLogradouro().toString());
        pstmt.setDate(3, new DateParse().parse(entity.getData()));
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

    @Override
    public Optional<EnderecoModel> findById(Integer id) throws SQLException {
        List<EnderecoModel> resultados = findAllById(List.of(id));
        if(resultados == null || resultados.size() != 1)
            throw new SQLException("Erro ao buscar valores, não existe somente um resultado! Size "+resultados.size());
        return Optional.ofNullable(resultados.get(0));
    }

    @Override
    public List<EnderecoModel> findAllById(Iterable<Integer> ids) throws SQLException {
        String sql = "select * from endereco ";
        if(ids != null) {
            List<Integer> lista = new ArrayList();
            Iterator<Integer> interetor = ids.iterator();
            while(interetor.hasNext()){
                lista.add(interetor.next());
            }
            String sqlIN = lista.stream()
                    .map(x -> String.valueOf(x))
                    .collect(Collectors.joining(",", "(", ")"));
            sql = sql+" where id in(?)".replace("(?)", sqlIN);
        }
        PreparedStatement stmt = conn.prepareStatement(sql);
        List<EnderecoModel> resultado = new ArrayList();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt(1);
                String descricao = rs.getString(2);
                LogradouroEnum logEnum = LogradouroEnum.valueOf(rs.getString(3));
                java.sql.Date date = rs.getDate(4);
                EnderecoModel endereco = new EnderecoModel(id, descricao, logEnum, date);
                resultado.add(endereco);
            }
        }
        return resultado;
    }



    @Override
    public void delete(EnderecoModel entity) {

    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void deleteAll(Iterable<? extends EnderecoModel> entities) {

    }

    public List<EnderecoModel> findAll() throws SQLException {
        return findAllById(null);
    }
}
