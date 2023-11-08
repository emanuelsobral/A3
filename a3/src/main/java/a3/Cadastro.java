package a3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Cadastro {

    Conexao con = new Conexao("root", "RootAdmin123");

    public static void fazerCadastro(Connection connection, Scanner sc, Usuario usuario) throws SQLException {
        if (usuario == null) {
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

            if (usuario instanceof Admin) {
                cadastrarAdmin(connection, nome, email, senha, altura, idade, peso, frequencia, genero);
            } else {
                cadastrarUsuario(connection, nome, email, senha, altura, idade, peso, frequencia, genero);
            }
        } else {
            System.out.println("Voce ja esta logado.");
        }
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

    private static void cadastrarAdmin(Connection connection, String nome, String email, String senha, float altura,
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
            Scanner sc = new Scanner(System.in);
            System.out.print("Voce deseja cadastrar um administrador? (S ou N): ");
            String admin = sc.nextLine();
            if (admin.equals("S") || admin.equals("s")) {
                statement.setBoolean(9, true);
            } else if (admin.equals("N") || admin.equals("n") || admin.equals(" ") || admin.equals("")) {
                statement.setBoolean(9, false);
            }
            int linhasAfetadas = statement.executeUpdate();
            sc.close();
            if (linhasAfetadas > 0) {
                System.out.println("Cadastro realizado com sucesso.");
            } else {
                System.out.println("Erro ao realizar cadastro.");
            }
        }
    }
}