package a3;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String BD = "a3";
        String conexao = "jdbc:mysql://localhost:3306/" + BD;
    
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite seu nome de usuario do mySQL \n Caso nao responda valor atribuido sera root: ");
        String usuarioSQL = sc.nextLine();
        if (usuarioSQL == "") {
            usuarioSQL = "root";
        }
        System.out.print("Digite sua senha do mySQL: ");
        String senhaSQL = sc.nextLine();

        try (Connection connection = DriverManager.getConnection(conexao, usuarioSQL, senhaSQL)) {
            System.out.println("Conexao com o banco de dados estabelecida.");
            System.out.println("Escolha uma opcao: ");
            System.out.println("1 - Fazer login");
            System.out.println("2 - Fazer cadastro");
            int opcao = sc.nextInt();
            sc.nextLine(); // consumir a quebra de linha
            switch (opcao) {
                case 1:
                    fazerLogin(connection, sc);
                    break;
                case 2:
                    fazerCadastro(connection, sc);
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }

    private static void fazerLogin(Connection connection, Scanner sc) throws SQLException {
        System.out.print("Digite seu email: ");
        String email = sc.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = sc.nextLine();
        
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, senha);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Login efetuado com sucesso.");
                    int id = resultSet.getInt("userID");
                    String nome = resultSet.getString("nome");
                    float altura = resultSet.getFloat("altura");
                    int idade = resultSet.getInt("idade");
                    float peso = resultSet.getFloat("peso");
                    int frequencia = resultSet.getInt("frequencia");
                    String genero = resultSet.getString("genero");
                    boolean admin = resultSet.getBoolean("admin");

                    if (admin) {
                        System.out.println("Voce e um administrador.");
                        System.out.println("Deseja ver todos os usuarios no banco de dados? (S/N)");
                        String resposta = sc.nextLine();
                        if (resposta.equalsIgnoreCase("S")) {
                            exibirTodosUsuarios(connection);
                        } else {
                            System.out.println("Ok, ate mais.");
                        }
                    } else {
                        System.out.println("Voce e um usuario comum.");
                        System.out.printf("ID: %d, Nome: %s, Email: %s, Altura: %.2f, Idade: %d, Peso: %.2f, Frequencia: %d, Genero: %s%n", id, nome, email, altura, idade, peso, frequencia, genero);
                        System.out.println("Obrigado por usar o nosso programa.");
                    }
                } else {
                    System.out.println("Email ou senha incorretos.");
                }
            }
        }
    }

    private static void fazerCadastro(Connection connection, Scanner sc) throws SQLException {
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
        System.out.print("Voce e um administrador? (S/N): ");
        String resposta = sc.nextLine();
        boolean admin = resposta.equalsIgnoreCase("S");

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
            statement.setBoolean(9, admin);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Cadastro realizado com sucesso.");
            } else {
                System.out.println("Erro ao realizar cadastro.");
            }
        }
    }

    private static void exibirTodosUsuarios(Connection connection) throws SQLException {
        String sql = "SELECT * FROM usuario";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            printResultSet(resultSet);
        }
    }

    private static void printResultSet(ResultSet resultSet) throws SQLException {
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
            boolean admin = resultSet.getBoolean("admin");

            System.out.printf("ID: %d, Nome: %s, Email: %s, Senha: %s, Altura: %.2f, Idade: %d, Peso: %.2f, Frequencia: %d, Genero: %s, Admin: %b%n", id, nome, email, senha, altura, idade, peso, frequencia, genero, admin);
        }
    }
}