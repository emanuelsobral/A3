package dbcreate;

import java.sql.*;

public class dbCreate {

    public static void main(String[] args) {
        String nomeBanco = "a3";
        String nomeTabela = "usuario";

        //conexao
        String url = "jdbc:mysql://localhost:3306/";
        
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite seu nome de usuario do mySQL \n Caso nao responda valor atribuido sera root:");
        String user = sc.nextLine();
        if (user == "") {
            user = "root";
        }
        System.out.print("Digite sua senha do mySQL: ");
        String password = sc.nextLine();
        
        sc.close();
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão estabelecida com sucesso!");

            // Criar um objeto Statement para executar comandos SQL
            Statement stmt = con.createStatement();

            String sql = "CREATE DATABASE " + nomeBanco;

            int result = stmt.executeUpdate(sql);
            if (result == 1) {
                System.out.println("Banco de dados " + nomeBanco + " criado com sucesso!");
            } else {
                System.out.println("Erro ao criar o banco de dados " + nomeBanco);
            }

            url += nomeBanco;

            // Reconectar ao MySQL com a nova URL
            con = DriverManager.getConnection(url, user, password);

            // Criar um novo objeto Statement para executar comandos SQL no novo banco de dados
            stmt = con.createStatement();

            sql = "CREATE TABLE " + nomeTabela + " (userID INT NOT NULL AUTO_INCREMENT, nome VARCHAR(60) NOT NULL, email VARCHAR(60) NOT NULL, senha VARCHAR(60) NOT NULL, altura FLOAT NOT NULL, idade INT(11) NOT NULL, peso FLOAT NOT NULL, frequencia INT(2) NOT NULL, genero TINYTEXT NOT NULL, PRIMARY KEY (userID))";

            result = stmt.executeUpdate(sql);
            if (result == 0) {
                System.out.println("Tabela " + nomeTabela + " criada com sucesso!");
                stmt.executeUpdate("INSERT INTO usuario (nome, email, senha, altura, idade, peso, frequencia, genero) VALUES ('Maria', 'maria@email.com', 'maria123', 1.62, 28, 54.2, 2, 'F')");
                stmt.executeUpdate("INSERT INTO usuario (nome, email, senha, altura, idade, peso, frequencia, genero) VALUES ('Roberto', 'roberto@email.com', 'roberto34', 1.72, 43, 72.2, 5, 'M')");
            } else {
                System.out.println("Erro ao criar a tabela " + nomeTabela);
            }

            stmt.close();
            con.close();

        } catch (SQLException e) {
            // Tratar os possíveis erros de conexão ou de execução do comando SQL
            e.printStackTrace();
        }
    }
}
