package br.sapiens.configs;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CriaEntidades {
    public CriaEntidades() throws SQLException {
        ConexaoSingleton s1 = new ConexaoSingleton();
        Connection conAluno = s1.getConnection();
        String sql = "CREATE TABLE alunos (matricula int primary key, nome varchar(200))";
        Statement statement = conAluno.createStatement();
        System.out.println("Tabela alunos criada com sucesso!");
        statement.execute(sql);
        conAluno.close();
    }
    public void criarTabelaAlunos(){

    }
}
