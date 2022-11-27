package br.sapiens.configs;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CriaEntidades {

    public CriaEntidades(Connection con) throws SQLException {
        String endereco = "" +
                "CREATE TABLE `endereco` (\n" +
                "  `id` bigint auto_increment,\n" +
                "  `descricao` varchar(200),\n" +
                "  `logradouro` varchar(200),\n" +
                "  `data` date,\n" +
                "  PRIMARY KEY (`id`)\n" +
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

        String matricula = "CREATE TABLE `Matricula` (\n" +
                " disciplina int, \n " +
                " aluno int, \n " +
                " periodo  varchar(200),\n " +
                " PRIMARY KEY (periodo, disciplina, aluno) \n" +
                ");\n";




        Statement statement = con.createStatement();
        statement.execute(matricula);
        System.out.println("Tabela matricula criada com sucesso. ");
        statement.execute(disciplinas);
        System.out.println("Tabela disciplina criada com sucesso. ");
        statement.execute(aluno);
        System.out.println("Tabela aluno criada com sucesso. ");
        statement.execute(endereco);
        System.out.println("Tabela endereco criada com sucesso. ");
        //System.out.println("Tabela matricula criada com sucesso. ");
        statement.close();
    }

}
