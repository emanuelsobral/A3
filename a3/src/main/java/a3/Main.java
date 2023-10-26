package a3;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String BD = "a3";
        String conexao = "jdbc:mysql://localhost:3306/" + BD;
    
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite seu nome de usuario do mySQL \n Caso nao responda valor atribuido sera root:");
        String usuarioSQL = sc.nextLine();
        if (usuarioSQL == "") {
            usuarioSQL = "root";
        }
        System.out.print("Digite sua senha do mySQL: ");
        String senhaSQL = sc.nextLine();
        
        sc.close();

        try {
             Connection connection = DriverManager.getConnection(conexao, usuarioSQL, senhaSQL);
             Statement statement = connection.createStatement();
            // statement.executeUpdate("CREATE DATABASE A3");
            // statement.executeUpdate("USE A3");
            // statement.executeUpdate("CREATE TABLE usuario (userID INT NOT NULL AUTO_INCREMENT, nome VARCHAR(60) NOT NULL, email VARCHAR(60) NOT NULL, senha VARCHAR(60) NOT NULL, altura FLOAT NOT NULL, idade INT(11) NOT NULL, peso FLOAT NOT NULL, frequencia INT(2) NOT NULL, genero TINYTEXT NOT NULL, PRIMARY KEY (userID))");
            // statement.executeUpdate("INSERT INTO usuario (nome, email, senha, altura, idade, peso, frequencia, genero) VALUES ('Maria', 'maria@email.com', 'maria123', 1.62, 28, 54.2, 2, 'F')");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM usuario");

            while (resultSet.next()) {
                int id = resultSet.getInt("userID");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                float altura = resultSet.getFloat("altura");
                int idade = resultSet.getInt("idade");
                float peso = resultSet.getFloat("peso");
                int frequencia = resultSet.getInt("frequencia");
                String genero = resultSet.getString("genero");

                System.out.println("ID: " + id + ", Nome: " + nome + ", Email: " + email + ", Senha: " + senha + ", Altura: " + altura + ", Idade: " + idade + ", Peso: " + peso + ", Frequncia: " + frequencia + ", Genero: " + genero);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
