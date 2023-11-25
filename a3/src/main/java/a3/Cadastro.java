package a3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Cadastro {

    Conexao con = new Conexao("root", "RootAdmin123");

    public static void fazerCadastro(Connection connection, Scanner sc) throws SQLException {
            System.out.print("Digite seu nome: ");
            String nome = sc.nextLine();
            System.out.print("Digite seu email: ");
            String email = sc.nextLine();
            System.out.print("Digite sua senha: ");
            String senha = sc.nextLine();
            System.out.print("Digite sua altura em metros: ");
            float altura = sc.nextFloat();
            System.out.print("Digite sua idade em anos: ");
            int idade = sc.nextInt();
            System.out.print("Digite seu peso em quilos: ");
            float peso = sc.nextFloat();
            System.out.print("Digite sua frequencia semanal de exercicios (0 a 7): ");
            int frequencia = sc.nextInt();
            sc.nextLine(); // consumir a quebra de linha
            System.out.print("Digite seu genero (M ou F): ");
            String genero = sc.nextLine();
            cadastrarUsuario(connection, nome, email, senha, altura, idade, peso, frequencia, genero);
        }

    private static void cadastrarUsuario(Connection connection, String nome, String email, String senha, float altura,
            int idade, float peso, int frequencia, String genero) throws SQLException {
        String sql = "INSERT INTO usuario (nome, email, senha, altura, idade, peso, frequencia, genero, admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, email);
            statement.setString(3, senha);
            statement.setFloat(4, altura);
            statement.setInt(5, idade);
            statement.setFloat(6, peso);
            statement.setInt(7, frequencia);
            statement.setString(8, genero);
            statement.setBoolean(9, false);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Cadastro realizado com sucesso.");
            } else {
                System.out.println("Erro ao realizar cadastro.");
            }
        }
    }

    
}
	