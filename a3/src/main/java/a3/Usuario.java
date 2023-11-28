package a3;

import java.sql.*;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;


public class Usuario {
    protected static final Object DadosUsuarioExibir = null;
    protected static int id;
    private static String nome;
    private static float altura;
    private static int idade;
    private static float peso;
    private static int frequencia;
    private static String genero;
    private boolean admin;
    private static int exercicioID;

    static Conexao con = new Conexao("root", "RootAdmin123");

    public Usuario(int id, String nome, float altura, int idade, float peso, int frequencia, String genero,
            boolean admin, int exercicioID) {
        Usuario.id = id;
        Usuario.nome = nome;
        Usuario.altura = altura;
        Usuario.idade = idade;
        Usuario.peso = peso;
        Usuario.frequencia = frequencia;
        Usuario.genero = genero;
        this.admin = admin;
        Usuario.exercicioID = exercicioID;
    }

    public Usuario(Connection conectar) {
    }

    public static String exercicioAtual(Connection connection) throws SQLException {
        if (exercicioID == 0) {
            return "Você não está fazendo nenhum exercício no momento.";
        } else {
            String sql = "SELECT * FROM exercicios WHERE exercicioID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, exercicioID);
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


    public class UserInterface extends JFrame {
        public UserInterface(Connection connection) {
            setTitle("User Options");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 300);
            setLayout(new BorderLayout());

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(4, 1));

            JButton selectExerciseButton = new JButton("Selecionar um Exercicio");
            selectExerciseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        selecionarExercicio(connection, new Scanner(System.in));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            buttonPanel.add(selectExerciseButton);

            JButton displayDataButton = new JButton("Exibir Dados");
            displayDataButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        exibirInformacoesUsuario(connection);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            buttonPanel.add(displayDataButton);

            JButton updateDataButton = new JButton("Alterar Dados");
            updateDataButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        alterarDados(connection);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            buttonPanel.add(updateDataButton);

            JButton deleteAccountButton = new JButton("Deletar Conta");
            deleteAccountButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        deletarConta(connection, new Scanner(System.in));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            buttonPanel.add(deleteAccountButton);

            add(buttonPanel, BorderLayout.CENTER);
        }

    }

    public static void exibirInformacoesUsuario(Connection connection) throws SQLException {
        JFrame frame = new JFrame("Informações do Usuário");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);

        JLabel labelNome = new JLabel("Nome: " + nome);
        labelNome.setBounds(20, 20, 300, 20);
        frame.add(labelNome);

        JLabel labelIMC = new JLabel("IMC: " + calcularIMC());
        labelIMC.setBounds(20, 50, 300, 20);
        frame.add(labelIMC);

        JLabel labelGastoCaloricoBasal = new JLabel("Gasto Calórico Basal: " + calcularGastoCaloricoBasal());
        labelGastoCaloricoBasal.setBounds(20, 80, 300, 20);
        frame.add(labelGastoCaloricoBasal);

        JLabel labelTempoAtividadeRecomendada = new JLabel(
                "Tempo de Atividade Recomendada: " + calcularTempoAtividadeRecomendada());
        labelTempoAtividadeRecomendada.setBounds(20, 110, 300, 20);
        frame.add(labelTempoAtividadeRecomendada);

        if (frequencia == 0) {
            JLabel labelSemExercicio = new JLabel("Você não está fazendo nenhum exercício no momento.");
            labelSemExercicio.setBounds(20, 140, 300, 20);
            frame.add(labelSemExercicio);
        } else {
            JLabel labelFrequencia = new JLabel("Frequência: " + frequencia + " vezes por semana");
            labelFrequencia.setBounds(20, 140, 300, 20);
            frame.add(labelFrequencia);

            JLabel labelExercicioAtual = new JLabel("Exercício Atual: " + exercicioAtual(connection));
            labelExercicioAtual.setBounds(20, 170, 300, 20);
            frame.add(labelExercicioAtual);

            JLabel labelTempoExercicio = new JLabel(
                    "Tempo de Exercício: " + calcularTempoExercicio() + " minutos por dia");
            labelTempoExercicio.setBounds(20, 200, 300, 20);
            frame.add(labelTempoExercicio);
        }

        JButton closeButton = new JButton("Close");
        closeButton.setBounds(150, 230, 100, 30);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        frame.add(closeButton);

        frame.setVisible(true);
    }

    private static String calcularTempoExercicio() {
        String sql = "SELECT * FROM exercicios WHERE exercicioID = ?";
        try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
            statement.setInt(1, exercicioID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    if (idade < 18) {
                        return String.valueOf((60 / frequencia));
                    } else if (idade < 64) {
                        return String.valueOf((150 / frequencia));
                    } else {
                        return String.valueOf((120 / frequencia));
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

    private static String calcularTempoAtividadeRecomendada() {
        if (idade < 17) {
            return "60 minutos";
        } else if (idade < 64) {
            return "150 minutos";
        } else {
            return "120 minutos";
        }
    }

    private static String calcularGastoCaloricoBasal() {
        float gastoCaloricoBasal;
        if (genero.equalsIgnoreCase("M")) {
            gastoCaloricoBasal = (float) (66.5 + (13.75 * peso) + (5.003 * altura) - (6.755 * idade));
        } else {
            gastoCaloricoBasal = (float) (655.1 + (9.563 * peso) + (1.850 * altura) - (4.676 * idade));
        }
        return String.format("%.2f", gastoCaloricoBasal);
    }

    private static String calcularIMC() {
        float imc = peso / (altura * altura);
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

    public static void selecionarExercicio(Connection connection, Scanner sc) throws SQLException {
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
            statement.setInt(3, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Exercício selecionado com sucesso.");
            } else {
                System.out.println("Erro ao selecionar exercício.");
            }
        }
    }

    private static void ExibirExercicios(Connection connection) throws SQLException {
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
            statement.setInt(1, id);
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

    public static void deletarConta(Connection connection, Scanner sc) throws SQLException {
        System.out.println("Tem certeza que deseja deletar a conta? (S/N)");
        String confirmacao = sc.nextLine();
        if (confirmacao.equalsIgnoreCase("S")) {
            String sql = "DELETE FROM usuario WHERE userID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
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

    public static void alterarDados(Connection connection) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
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
