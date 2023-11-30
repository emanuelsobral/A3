package dbcreate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbCreate {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/A3";
        String username = "root";
        String password = "RootAdmin123";

        try (Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement()) {

            // Criando o banco de dados
            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS A3";
            statement.executeUpdate(createDatabaseQuery);

            // Usando o banco de dados
            String useDatabaseQuery = "USE A3";
            statement.executeUpdate(useDatabaseQuery);

            // Criando tabela de exercicios
            String createExerciciosTableQuery = "CREATE TABLE IF NOT EXISTS exercicios (" +
                    "exercicioID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "exercicio VARCHAR(50) NOT NULL," +
                    "intensidade VARCHAR(10) NOT NULL," +
                    "fatorIntensidade INT NOT NULL," +
                    "MET DECIMAL(4,2) NOT NULL" +
                    ")";
            statement.executeUpdate(createExerciciosTableQuery);

            // Criando tabela de usuário
            String createUsuarioTableQuery = "CREATE TABLE IF NOT EXISTS usuario (" +
                    "userID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "nome VARCHAR(60) NOT NULL," +
                    "email VARCHAR(60) NOT NULL," +
                    "senha VARCHAR(60) NOT NULL," +
                    "altura FLOAT NOT NULL," +
                    "idade INT(11) NOT NULL," +
                    "peso FLOAT NOT NULL," +
                    "frequencia INT(2)," +
                    "genero TINYTEXT NOT NULL," +
                    "admin TINYINT(1)," +
                    "exercicioID INT," +
                    "CONSTRAINT fk_userexe FOREIGN KEY (exercicioID) REFERENCES exercicios (exercicioID)" +
                    ")";
            statement.executeUpdate(createUsuarioTableQuery);

            // Populando tabela de exercicios
            String insertExerciciosQuery = "INSERT INTO exercicios (exercicio, intensidade, fatorIntensidade, MET) VALUES "
                    +
                    "('Caminhada', 'Fácil', 1, 3.5)," +
                    "('Corrida', 'Moderado', 3, 7.0)," +
                    "('Natação', 'Difícil', 5, 10.0)," +
                    "('Yoga', 'Fácil', 1, 2.5)," +
                    "('Musculação', 'Moderado', 3, 5.0)," +
                    "('Boxe', 'Difícil',5,12.0)," +
                    "('Escalada', 'Difícil', 5 , 11.0)," +
                    "('HIIT', 'Difícil', 5 , 15.0)," +
                    "('Crossfit', 'Difícil', 5 , 12.0)," +
                    "('Pilates', 'Fácil', 1 , 3.0)," +
                    "('Tai Chi', 'Iniciante', 2 , 3.0)," +
                    "('Dança Zumba', 'Moderado', 3 , 6.0)," +
                    "('Basquete', 'Moderado', 3 , 6.5)," +
                    "('Futebol', 'Avançado', 4 , 7.0)," +
                    "('Saltar Corda', 'Avançado', 4, 10.0)," +
                    "('Corida em ladeira', 'Difícil', 5 , 15.0)," +
                    "('Remo', 'Difícil', 5 , 12.0)," +
                    "('Ciclismo em terreno acidentado', 'Difícil' , 5 , 14.0)," +
                    "('HIIT com pesos', 'Difícil', 5 , 16.0)";
            statement.executeUpdate(insertExerciciosQuery);

            // Populando tabela de usuário
            String insertUsuarioQuery = "INSERT INTO usuario (nome, email, senha, altura, idade, peso, genero, exercicioID) "
                    +
                    "('Carlos', 'carlos@email.com', 'senhacarlos', 1.76, 38, 65.7, 'M', 0)," +
                    "('Maria', 'maria@email.com', 'maria123', 1.62, 28, 54.2, 'F', 0)," +
                    "(('Roberto', 'roberto@email.com', 'roberto34', 1.72, 43, 72.2, 'M', 0)," +
                    "('Usuario', 'usuario', 'usuario', 1.72, 43, 72.2, 'M', 0)";
            statement.executeUpdate(insertUsuarioQuery);

            // Populando tabela de usuário com admin
            String insertAdminQuery = "INSERT INTO usuario (nome, email, senha, altura, idade, peso, frequencia, genero, admin) VALUES "
                    +
                    "('Admin', 'admin', 'admin', 1.72, 43, 72.2, 5, 'M', 1)";
            statement.executeUpdate(insertAdminQuery);

            System.out.println("Código MySQL executado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao executar o código MySQL: " + e.getMessage());
        }
    }
}
