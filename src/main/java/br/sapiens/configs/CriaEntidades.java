package br.sapiens.configs;


import br.sapiens.models.CursoEnum;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CriaEntidades {

    public CursoEnum curso;

    public CriaEntidades(Connection con) throws SQLException {
//        String matricula = "" +
//                "CREATE TABLE `Matricula` (\n" +
//                "  `disciplina` int,\n" +
//                "  `aluno` int,\n" +
//                "  `periodo` varchar(200),\n" +
//                "  PRIMARY KEY (`periodo`,`disciplina`, `aluno`)\n" +
//                ");\n";
        String matricula = "" +
                "CREATE TABLE `Matricula` (\n" +
                "  id varchar ,\n" +
                "  disciplina int,\n" +
                "  aluno int,\n" +
                "  periodo varchar(200),\n" +
                "  PRIMARY KEY(id)\n" +
                ");\n";
        String disciplinas = "CREATE TABLE `Disciplina` (\n" +
                "  `id` int AUTO_INCREMENT,\n" +
                "  `descricao` varchar(200),\n" +
                "  `curso` varchar(200),\n" +
                "  PRIMARY KEY (`id`)\n" +
                ");\n" +
                "\n";
        String aluno = "CREATE TABLE `Aluno` (\n" +
                "  `id` int AUTO_INCREMENT,\n" +
                "  `nome` varchar(200),\n" +
                "  `dataNascimento` Date,\n" +
                "  `curso` varchar(200),\n" +
                "  PRIMARY KEY (`id`)\n" +
                ");\n";

        String endereco = "" +
                "CREATE TABLE `endereco` (\n" +
                "  `id` bigint auto_increment,\n" +
                "  `descricao` varchar(200),\n" +
                "  `logradouro` varchar(200),\n" +
                "  PRIMARY KEY (`id`)\n" +
                ");\n";

        // Criando tabelas
        Statement statement = con.createStatement();

        statement.execute(matricula);
        statement.execute(disciplinas);
        statement.execute(aluno);
        statement.execute(endereco);

        System.out.println("Tabelas criada com sucesso");

        // Inserir tabelas


        // criar as chaves estrangeiras


    }
}