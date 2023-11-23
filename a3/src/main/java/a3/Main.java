package a3;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Conexao con = new Conexao("root", "RootAdmin123");

        Scanner sc = new Scanner(System.in);

        try (Connection connection = con.conectar()) {
            System.out.println("Conexao com o banco de dados estabelecida.");
            System.out.println("Escolha uma opcao: ");
            System.out.println("1 - Fazer login");
            System.out.println("2 - Fazer cadastro");
            int opcao = sc.nextInt();
            sc.nextLine();
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

        Login login = new Login(email, senha);
        Usuario usuario = login.fazerLogin(connection);

        if (usuario != null) {
            System.out.println("Login efetuado com sucesso.");
            if (usuario instanceof Admin) {
                System.out.println("Você é um administrador.");
                Admin admin = (Admin) usuario;
                // chamar métodos de admin aqui
            } else {
                System.out.println("Você é um usuário.");
                System.out.println("Escolha uma opcao: ");
                System.out.println("1 - Exibir Seus Dados");
                System.out.println("2 - Alterar Seus Dadoas");
                int opcaoUser = sc.nextInt();

                switch (opcaoUser) {
                    case 1:
                        usuario.exibirDados(connection);
                        break;
                    case 2:
                        usuario.alterarDados(connection);
                        break;
                    default:
                        System.out.println("Opcao invalida.");
                        break;
                    }
            }
        } else {
            System.out.println("Email ou senha incorretos.");
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
        boolean admin = false;

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

    private static void cadastrarAdmin(Connection connection, Scanner sc) throws SQLException {
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
        System.out.println("Voce escolheu exibir todos os usuarios no banco de dados.");
        String sql = "SELECT * FROM usuario";
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            printResultSet(resultSet);
        }
    }

    private static void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            System.out.printf("%-15s", metaData.getColumnName(i));
        }
        System.out.println();
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-15s", resultSet.getString(i));
            }
            System.out.println();
        }
    }

    private static void deletarUsuario(Connection connection, Scanner sc) throws SQLException {
        System.out.println("Voce escolheu deletar um usuario do banco de dados.");
        System.out.print("Digite o ID do usuario que voce quer deletar: ");
        int id = sc.nextInt();
        sc.nextLine(); // consumir a quebra de linha
        String sql = "DELETE FROM usuario WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Usuario deletado com sucesso.");
            } else {
                System.out.println("Nao existe usuario com esse ID.");
            }
        }
    }
}