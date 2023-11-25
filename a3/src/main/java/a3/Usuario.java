package a3;

import java.sql.*;
import java.util.Scanner;

public class Usuario {
    private int id;
    private String nome;
    private float altura;
    private int idade;
    private float peso;
    private int frequencia;
    private String genero;
    private boolean admin;

    
    Conexao con = new Conexao("root", "RootAdmin123");

    public Usuario(int id, String nome, float altura, int idade, float peso, int frequencia, String genero, boolean admin) {
        this.id = id;
        this.nome = nome;
        this.altura = altura;
        this.idade = idade;
        this.peso = peso;
        this.frequencia = frequencia;
        this.genero = genero;
        this.admin = admin;
    }

    public void exibirDados(Connection connection) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, this.id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nome = resultSet.getString("nome");
                    String email = resultSet.getString("email");
                    float altura = resultSet.getFloat("altura");
                    int idade = resultSet.getInt("idade");
                    float peso = resultSet.getFloat("peso");
                    int frequencia = resultSet.getInt("frequencia");
                    String genero = resultSet.getString("genero");
                    System.out.println("Nome: " + nome);
                    System.out.println("Email: " + email);
                    System.out.println("Altura: " + altura);
                    System.out.println("Idade: " + idade);
                    System.out.println("Peso: " + peso);
                    System.out.println("Frequência semanal de exercícios: " + frequencia);
                    System.out.println("Gênero: " + genero);
                } else {
                    System.out.println("Usuário não encontrado.");
                }
            }
        }
    }

    public void deletarConta(Connection connection, Scanner sc) throws SQLException {
        System.out.println("Tem certeza que deseja deletar a conta? (S/N)");
        String confirmacao = sc.nextLine();
        if (confirmacao.equalsIgnoreCase("S")) {
            String sql = "DELETE FROM usuario WHERE userID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, this.id);
                int linhasAfetadas = statement.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Conta deletada com sucesso.");
                } else {
                    System.out.println("Erro ao deletar conta.");
                }
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    public void alterarDados(Connection connection) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, this.id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Usuario encontrado.");
                    int opcaoAlterar;
                    do {
                        Scanner sc = new Scanner(System.in);
                        System.out.println("Escolha qual informacao voce quer alterar: ");
                        System.out.println("1 - Nome");
                        System.out.println("2 - Email");
                        System.out.println("3 - Senha");
                        System.out.println("4 - Altura");
                        System.out.println("5 - Idade");
                        System.out.println("6 - Peso");
                        System.out.println("7 - Frequencia");
                        System.out.println("8 - Genero");
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
    
}

