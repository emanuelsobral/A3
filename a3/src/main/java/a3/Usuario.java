package a3;

import java.sql.*;
import java.util.Scanner;

public class Usuario {
    protected static int id;
    private String nome;
    private float altura;
    private int idade;
    private float peso;
    private int frequencia;
    private String genero;
    private boolean admin;
    private int exercicioID;

    
    Conexao con = new Conexao("root", "RootAdmin123");

    public Usuario(int id, String nome, float altura, int idade, float peso, int frequencia, String genero, boolean admin, int exercicioID) {
        this.id = id;
        this.nome = nome;
        this.altura = altura;
        this.idade = idade;
        this.peso = peso;
        this.frequencia = frequencia;
        this.genero = genero;
        this.admin = admin;
        this.exercicioID = exercicioID;
    }

    public String exercicioAtual(Connection connection) throws SQLException {
        if (this.exercicioID == 0) {
            return "Você não está fazendo nenhum exercício no momento.";
        } else {
            String sql = "SELECT * FROM exercicios WHERE exercicioID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, this.exercicioID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String exercicio = resultSet.getString("exercicio");
                        return exercicio;
                    } else {
                        return "Erro ao obter exercício atual.";
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "Erro ao obter exercício atual.";
            }
        }
    }

    public void DadosUsuarioExibir(Connection connection) throws SQLException {
        System.out.println("Ola " + this.nome + ", seja bem vindo ao seu perfil!");
        System.out.println("Seu IMC é: " + this.calcularIMC());
        System.out.println("Seu gasto calórico basal é: " + this.calcularGastoCaloricoBasal());
        System.out.println("O tempo de Atividade Recomendada para sua idade é: " + this.calcularTempoAtividadeRecomendada());
        if (this.frequencia == 0) {
            System.out.println("Você não está fazendo nenhum exercício no momento.");
        } else {
            System.out.println("Você está fazendo " + this.frequencia + " vezes por semana.");
            System.out.println("Seu exercício atual é: " + this.exercicioAtual(connection));
            System.out.println("Voce deve fazer pelo menos" + this.calcularTempoExercicio() + " minutos de exercício por dia.");
        }
    }

    private String calcularTempoExercicio() {
        String sql = "SELECT * FROM exercicios WHERE exercicioID = ?";
        try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
            statement.setInt(1, this.exercicioID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    if (this.idade < 18) {
                        return String.valueOf((60 / this.frequencia));
                    } else if (this.idade < 64) {
                        return String.valueOf((150 / this.frequencia));
                    } else {
                        return String.valueOf((120 / this.frequencia));
                    }
                } else {
                    System.out.println("Erro ao calcular tempo de exercício.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String calcularTempoAtividadeRecomendada() {
        if (this.idade < 17) {
            return "60 minutos";
        } else if (this.idade < 64) {
            return "150 minutos";
        } else {
            return "120 minutos";
        }
    }

    private String calcularGastoCaloricoBasal() {
        float gastoCaloricoBasal;
        if (this.genero.equalsIgnoreCase("M")) {
            gastoCaloricoBasal = (float) (66.5 + (13.75 * this.peso) + (5.003 * this.altura) - (6.755 * this.idade));
        } else {
            gastoCaloricoBasal = (float) (655.1 + (9.563 * this.peso) + (1.850 * this.altura) - (4.676 * this.idade));
        }
        return String.format("%.2f", gastoCaloricoBasal);
    }

    private String calcularIMC() {
        float imc = this.peso / (this.altura * this.altura);
        if (imc < 18.5) {
            return "Abaixo do peso";
        } else if (imc < 25) {
            return "Peso normal";
        } else if (imc < 30) {
            return "Sobrepeso";
        } else if (imc < 35) {
            return "Obesidade grau 1";
        } else if (imc < 40) {
            return "Obesidade grau 2";
        } else {
            return "Obesidade grau 3";
        }
    }

    public void selecionarExercicio(Connection connection, Scanner sc) throws SQLException {
        System.out.println("Escolha um exercício: ");
        ExibirExercicios(connection);
        System.out.print("Digite o ID do exercício: ");
        int exercicioID = sc.nextInt();
        sc.nextLine(); // consumir a quebra de linha
        System.out.print("Digite a frequência semanal de exercícios: ");
        int frequencia = sc.nextInt();
        sc.nextLine(); // consumir a quebra de linha
        String sql = "UPDATE usuario SET exercicioID = ?, frequencia = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, exercicioID);
            statement.setInt(2, frequencia);
            statement.setInt(3, this.id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Exercício selecionado com sucesso.");
            } else {
                System.out.println("Erro ao selecionar exercício.");
            }
        }
    }

    private void ExibirExercicios(Connection connection) throws SQLException {
        String sql = "SELECT * FROM exercicios";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int exercicioID = resultSet.getInt("exercicioID");
                    String exercicio = resultSet.getString("exercicio");
                    String intensidade = resultSet.getString("intensidade");
                    float MET = resultSet.getFloat("MET");
                    System.out.println("ID: " + exercicioID);
                    System.out.println("Exercicio: " + exercicio);
                    System.out.println("Intensidade: " + intensidade);
                    System.out.println("MET: " + MET);
                    System.out.println();
                }
            }
        }
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

