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
                        System.out.println("Escolha uma opcao: ");
                        System.out.println("1 - Cadastrar um usuario");
                        System.out.println("2 - Cadastrar outro administrador");
                        System.out.println("3 - Exibir todos os usuarios no banco de dados");
                        System.out.println("4 - Deletar um usuario do banco de dados");
                        System.out.println("5 - Alterar informacoes de um usuario");
                        int opcaoAdmin = sc.nextInt();
                        sc.nextLine(); // consumir a quebra de linha
                        switch (opcaoAdmin) {
                            case 1:
                                fazerCadastro(connection, sc);
                                break;
                            case 2:
                                cadastrarAdmin(connection,sc);
                                break;
                            case 3:
                                exibirTodosUsuarios(connection);
                                break;
                            case 4:
                                deletarUsuario(connection, sc);
                                break;
                            case 5:
                                alterarUsuario(connection, sc);
                                break;
                            default:
                                System.out.println("Opcao invalida.");
                                break;
                        }
                    } else {
                        System.out.println("Voce e um usuario comum.");
                        System.out.printf(
                                "ID: %d, Nome: %s, Email: %s, Altura: %.2f, Idade: %d, Peso: %.2f, Frequencia: %d, Genero: %s%n",
                                id, nome, email, altura, idade, peso, frequencia, genero);
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

    private static void alterarUsuario(Connection connection, Scanner sc) throws SQLException {
        System.out.println("Voce escolheu alterar informacoes de um usuario.");
        System.out.print("Digite o ID do usuario que voce quer alterar: ");
        int id = sc.nextInt();
        sc.nextLine(); // consumir a quebra de linha
        String sql = "SELECT * FROM usuario WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Usuario encontrado.");
                    int opcaoAlterar;
                    do {
                        System.out.println("Escolha qual informacao voce quer alterar: ");
                        System.out.println("1 - Nome");
                        System.out.println("2 - Email");
                        System.out.println("3 - Senha");
                        System.out.println("4 - Altura");
                        System.out.println("5 - Idade");
                        System.out.println("6 - Peso");
                        System.out.println("7 - Frequencia");
                        System.out.println("8 - Genero");
                        System.out.println("9 - Admin");
                        System.out.println("0 - Sair");
                        opcaoAlterar = sc.nextInt();
                        sc.nextLine(); // consumir a quebra de linha
                        switch (opcaoAlterar) {
                            case 1:
                                alterarNome(connection, sc, id);
                                break;
                            case 2:
                                alterarEmail(connection, sc, id);
                                break;
                            case 3:
                                alterarSenha(connection, sc, id);
                                break;
                            case 4:
                                alterarAltura(connection, sc, id);
                                break;
                            case 5:
                                alterarIdade(connection, sc, id);
                                break;
                            case 6:
                                alterarPeso(connection, sc, id);
                                break;
                            case 7:
                                alterarFrequencia(connection, sc, id);
                                break;
                            case 8:
                                alterarGenero(connection, sc, id);
                                break;
                            case 9:
                                alterarAdmin(connection, sc, id);
                                break;
                            case 0:
                                System.out.println("Saindo do modo de alteracao.");
                                break;
                            default:
                                System.out.println("Opcao invalida.");
                                break;
                        }
                    } while (opcaoAlterar != 0);
                } else {
                    System.out.println("Nao existe usuario com esse ID.");
                }
            }
        }
    }

    private static void alterarNome(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite o novo nome: ");
        String novoNome = sc.nextLine();
        String sql = "UPDATE usuario SET nome = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, novoNome);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Nome alterado com sucesso.");
            } else {
                System.out.println("Erro ao alterar nome.");
            }
        }
    }

    private static void alterarEmail(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite o novo email: ");
        String novoEmail = sc.nextLine();
        String sql = "UPDATE usuario SET email = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, novoEmail);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Email alterado com sucesso.");
            } else {
                System.out.println("Erro ao alterar email.");
            }
        }
    }

    private static void alterarSenha(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite a nova senha: ");
        String novaSenha = sc.nextLine();
        String sql = "UPDATE usuario SET senha = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, novaSenha);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Senha alterada com sucesso.");
            } else {
                System.out.println("Erro ao alterar senha.");
            }
        }
    }

    private static void alterarAltura(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite a nova altura em metros: ");
        float novaAltura = sc.nextFloat();
        String sql = "UPDATE usuario SET altura = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setFloat(1, novaAltura);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Altura alterada com sucesso.");
            } else {
                System.out.println("Erro ao alterar altura.");
            }
        }
    }

    private static void alterarIdade(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite a nova idade em anos: ");
        int novaIdade = sc.nextInt();
        String sql = "UPDATE usuario SET idade = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, novaIdade);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Idade alterada com sucesso.");
            } else {
                System.out.println("Erro ao alterar idade.");
            }
        }
    }

    private static void alterarPeso(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite o novo peso em quilos: ");
        float novoPeso = sc.nextFloat();
        String sql = "UPDATE usuario SET peso = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setFloat(1, novoPeso);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Peso alterado com sucesso.");
            } else {
                System.out.println("Erro ao alterar peso.");
            }
        }
    }

    private static void alterarFrequencia(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite a nova frequencia semanal de exercicios (0 a 7): ");
        int novaFrequencia = sc.nextInt();
        String sql = "UPDATE usuario SET frequencia = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, novaFrequencia);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Frequencia alterada com sucesso.");
            } else {
                System.out.println("Erro ao alterar frequencia.");
            }
        }
    }

    private static void alterarGenero(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite o novo genero (M ou F): ");
        String novoGenero = sc.nextLine();
        String sql = "UPDATE usuario SET genero = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, novoGenero);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Genero alterado com sucesso.");
            } else {
                System.out.println("Erro ao alterar genero.");
            }
        }
    }

    private static void alterarAdmin(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite se o usuario e um administrador (S/N): ");
        String resposta = sc.nextLine();
        boolean novoAdmin = resposta.equalsIgnoreCase("S");
        String sql = "UPDATE usuario SET admin = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, novoAdmin);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Admin alterado com sucesso.");
            } else {
                System.out.println("Erro ao alterar admin.");
            }
        }
    }

}