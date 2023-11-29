package a3;

import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
            setTitle("FitWeek");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 300);
            setLayout(new BorderLayout());

            setLocationRelativeTo(null);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(6, 1));

            // Add titleLabel text field
            JLabel tituloLabel = new JLabel("FitWeek");
            tituloLabel.setHorizontalAlignment(JLabel.CENTER);
            tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
            tituloLabel.setForeground(new Color(128, 0, 128)); 
            buttonPanel.add(tituloLabel);

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

            JButton selectExerciseButton = new JButton("Selecionar um Exercicio");
            selectExerciseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        selecionarExercicio(connection);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            buttonPanel.add(selectExerciseButton);

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

            JButton exitButton = new JButton("Sair");
            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            buttonPanel.add(exitButton);

            add(buttonPanel, BorderLayout.CENTER);
        }
    }

    public static void exibirInformacoesUsuario(Connection connection) throws SQLException {
        JFrame frame = new JFrame("Informações do Usuário");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);

        JLabel labelNome = new JLabel("<html><font color=\"purple\">Nome: </font>" + nome + "</html>");
        labelNome.setBounds(20, 20, 300, 20);
        frame.add(labelNome);

        JLabel labelIMC = new JLabel("<html><font color=\"purple\">IMC: </font>" + calcularIMC() + "</html>");
        labelIMC.setBounds(20, 50, 300, 20);
        frame.add(labelIMC);

        JLabel labelGastoCaloricoBasal = new JLabel("<html><font color=\"purple\">Gasto Calórico Basal: </font>" + calcularGastoCaloricoBasal() + "</html>");
        labelGastoCaloricoBasal.setBounds(20, 80, 300, 20);
        frame.add(labelGastoCaloricoBasal);

        JLabel labelTempoAtividadeRecomendada = new JLabel("<html><font color=\"purple\">Tempo de Atividade Recomendada: </font>" + calcularTempoAtividadeRecomendada() + "</html>");
        labelTempoAtividadeRecomendada.setBounds(20, 110, 300, 20);
        frame.add(labelTempoAtividadeRecomendada);

        if (frequencia == 0) {
            JLabel labelSemExercicio = new JLabel("<html><font color=\"purple\">Você não está fazendo nenhum exercício no momento.</font></html>");
            labelSemExercicio.setBounds(20, 140, 400, 20);
            frame.add(labelSemExercicio);
        } else {
            JLabel labelFrequencia = new JLabel("<html><font color=\"purple\">Frequência: </font>" + frequencia + " vezes por semana</html>");
            labelFrequencia.setBounds(20, 140, 300, 20);
            frame.add(labelFrequencia);

            JLabel labelExercicioAtual = new JLabel("<html><font color=\"purple\">Exercício Atual: </font>" + exercicioAtual(connection) + "</html>");
            labelExercicioAtual.setBounds(20, 170, 300, 20);
            frame.add(labelExercicioAtual);

            JLabel labelTempoExercicio = new JLabel("<html><font color=\"purple\">Tempo de Exercício: </font>" + calcularTempoExercicio() + " minutos por dia</html>");
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

        frame.setLocationRelativeTo(null);

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


        public static void selecionarExercicio(Connection connection) throws SQLException {
            JFrame frame = new JFrame("Selecionar Exercício");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(new BorderLayout());

            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(4, 2));

            JLabel exercicioLabel = new JLabel("Escolha um exercício:");
            JComboBox<String> exercicioComboBox = new JComboBox<>();
            JLabel frequenciaLabel = new JLabel("Selecione a frequência semanal de exercícios:");
            JComboBox<Integer> frequenciaComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7});

            JButton selecionarButton = new JButton("Selecionar");
            JButton voltarButton = new JButton("Voltar");

            panel.add(exercicioLabel);
            panel.add(exercicioComboBox);
            panel.add(frequenciaLabel);
            panel.add(frequenciaComboBox);
            panel.add(voltarButton);
            panel.add(selecionarButton);

            frame.add(panel, BorderLayout.CENTER);
            frame.setVisible(true);

            // Populate the exercise combo box
            List<String> exercicios = getExercicios(connection);
            for (String exercicio : exercicios) {
                exercicioComboBox.addItem(exercicio);
            }

            selecionarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int exercicioID = exercicioComboBox.getSelectedIndex() + 1;
                    int frequencia = (int) frequenciaComboBox.getSelectedItem();

                    String sql = "UPDATE usuario SET exercicioID = ?, frequencia = ? WHERE userID = ?";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setInt(1, exercicioID);
                        statement.setInt(2, frequencia);
                        statement.setInt(3, id);
                        int linhasAfetadas = statement.executeUpdate();
                        if (linhasAfetadas > 0) {
                            JOptionPane.showMessageDialog(frame, "Exercício selecionado com sucesso.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Erro ao selecionar exercício.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            voltarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose(); // Close the current frame
                }
            });
        }

        private static List<String> getExercicios(Connection connection) throws SQLException {
            List<String> exercicios = new ArrayList<>();
            String sql = "SELECT * FROM exercicios";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String exercicio = resultSet.getString("exercicio");
                        String intensidade = resultSet.getString("intensidade");
                        exercicios.add(exercicio + " (" + intensidade + ")");
                    }
                }
            }
            return exercicios;
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
        int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar a conta?", "Confirmação",
                JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM usuario WHERE userID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                int linhasAfetadas = statement.executeUpdate();
                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(null, "Conta deletada com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao deletar conta.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operação cancelada.");
        }
    }

    public static void alterarDados(Connection connection) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String[] options = { "Nome", "Email", "Senha", "Altura", "Idade", "Peso", "Frequencia", "Genero" };
                    JComboBox<String> comboBox = new JComboBox<>(options);
                    int result = JOptionPane.showConfirmDialog(null, comboBox,
                            "Escolha qual informacao voce quer alterar:",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        int opcaoAlterar = comboBox.getSelectedIndex();
                        switch (opcaoAlterar) {
                            case 0:
                                alterarNome(connection, new Scanner(System.in), id);
                                break;
                            case 1:
                                alterarEmail(connection, new Scanner(System.in), id);
                                break;
                            case 2:
                                alterarSenha(connection, new Scanner(System.in), id);
                                break;
                            case 3:
                                alterarAltura(connection, new Scanner(System.in), id);
                                break;
                            case 4:
                                alterarIdade(connection, new Scanner(System.in), id);
                                break;
                            case 5:
                                alterarPeso(connection, new Scanner(System.in), id);
                                break;
                            case 6:
                                alterarFrequencia(connection, new Scanner(System.in), id);
                                break;
                            case 7:
                                alterarGenero(connection, new Scanner(System.in), id);
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Opcao invalida.");
                                break;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Operacao cancelada.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nao existe usuario com esse ID.");
                }
            }
        }
    }

    private static void alterarNome(Connection connection, Scanner sc, int id) throws SQLException {
        JFrame frame = new JFrame("Alterar Nome");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        JLabel nomeLabel = new JLabel("Digite o novo nome: ");
        JTextField nomeField = new JTextField(20);
        JButton alterarButton = new JButton("Alterar");

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String novoNome = nomeField.getText();
                String sql = "UPDATE usuario SET nome = ? WHERE userID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, novoNome);
                    statement.setInt(2, id);
                    int linhasAfetadas = statement.executeUpdate();
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(frame, "Nome alterado com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erro ao alterar nome.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(nomeLabel);
        frame.add(nomeField);
        frame.add(alterarButton);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private static void alterarEmail(Connection connection, Scanner sc, int id) throws SQLException {
        JFrame frame = new JFrame("Alterar Email");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        JLabel emailLabel = new JLabel("Digite o novo email: ");
        JTextField emailField = new JTextField(20);
        JButton alterarButton = new JButton("Alterar");

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String novoEmail = emailField.getText();
                String sql = "UPDATE usuario SET email = ? WHERE userID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, novoEmail);
                    statement.setInt(2, id);
                    int linhasAfetadas = statement.executeUpdate();
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(frame, "Email alterado com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erro ao alterar email.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(alterarButton);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private static void alterarSenha(Connection connection, Scanner sc, int id) throws SQLException {
        JFrame frame = new JFrame("Alterar Senha");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        JLabel senhaLabel = new JLabel("Digite a nova senha: ");
        JPasswordField senhaField = new JPasswordField(20);
        JButton alterarButton = new JButton("Alterar");

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] novaSenhaChars = senhaField.getPassword();
                String novaSenha = new String(novaSenhaChars);
                String sql = "UPDATE usuario SET senha = ? WHERE userID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, novaSenha);
                    statement.setInt(2, id);
                    int linhasAfetadas = statement.executeUpdate();
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(frame, "Senha alterada com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erro ao alterar senha.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(senhaLabel);
        frame.add(senhaField);
        frame.add(alterarButton);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private static void alterarAltura(Connection connection, Scanner sc, int id) throws SQLException {
        JFrame frame = new JFrame("Alterar Altura");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        JLabel alturaLabel = new JLabel("Digite a nova altura em metros: ");
        JTextField alturaField = new JTextField(10);
        JButton alterarButton = new JButton("Alterar");

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float novaAltura = Float.parseFloat(alturaField.getText());
                String sql = "UPDATE usuario SET altura = ? WHERE userID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setFloat(1, novaAltura);
                    statement.setInt(2, id);
                    int linhasAfetadas = statement.executeUpdate();
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(frame, "Altura alterada com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erro ao alterar altura.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(alturaLabel);
        frame.add(alturaField);
        frame.add(alterarButton);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private static void alterarIdade(Connection connection, Scanner sc, int id) throws SQLException {
        JFrame frame = new JFrame("Alterar Idade");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        JLabel idadeLabel = new JLabel("Digite a nova idade em anos: ");
        JTextField idadeField = new JTextField(10);
        JButton alterarButton = new JButton("Alterar");

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int novaIdade = Integer.parseInt(idadeField.getText());
                String sql = "UPDATE usuario SET idade = ? WHERE userID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, novaIdade);
                    statement.setInt(2, id);
                    int linhasAfetadas = statement.executeUpdate();
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(frame, "Idade alterada com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erro ao alterar idade.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(idadeLabel);
        frame.add(idadeField);
        frame.add(alterarButton);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private static void alterarPeso(Connection connection, Scanner sc, int id) throws SQLException {
        JFrame frame = new JFrame("Alterar Peso");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        JLabel pesoLabel = new JLabel("Digite o novo peso em quilos: ");
        JTextField pesoField = new JTextField(10);
        JButton alterarButton = new JButton("Alterar");

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float novoPeso = Float.parseFloat(pesoField.getText());
                String sql = "UPDATE usuario SET peso = ? WHERE userID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setFloat(1, novoPeso);
                    statement.setInt(2, id);
                    int linhasAfetadas = statement.executeUpdate();
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(frame, "Peso alterado com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erro ao alterar peso.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(pesoLabel);
        frame.add(pesoField);
        frame.add(alterarButton);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private static void alterarFrequencia(Connection connection, Scanner sc, int id) throws SQLException {
        JFrame frame = new JFrame("Alterar Frequencia");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        JLabel frequenciaLabel = new JLabel("Digite a nova frequencia semanal de exercicios (0 a 7): ");
        JTextField frequenciaField = new JTextField(10);
        JButton alterarButton = new JButton("Alterar");

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int novaFrequencia = Integer.parseInt(frequenciaField.getText());
                String sql = "UPDATE usuario SET frequencia = ? WHERE userID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, novaFrequencia);
                    statement.setInt(2, id);
                    int linhasAfetadas = statement.executeUpdate();
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(frame, "Frequencia alterada com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erro ao alterar frequencia.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(frequenciaLabel);
        frame.add(frequenciaField);
        frame.add(alterarButton);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private static void alterarGenero(Connection connection, Scanner sc, int id) throws SQLException {
        JFrame frame = new JFrame("Alterar Gênero");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        JLabel generoLabel = new JLabel("Selecione o novo gênero: ");
        String[] generoOptions = { "Masculino", "Feminino" };
        JComboBox<String> generoComboBox = new JComboBox<>(generoOptions);
        JButton alterarButton = new JButton("Alterar");

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String novoGenero = (String) generoComboBox.getSelectedItem();
                String sql = "UPDATE usuario SET genero = ? WHERE userID = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, novoGenero);
                    statement.setInt(2, id);
                    int linhasAfetadas = statement.executeUpdate();
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(frame, "Gênero alterado com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erro ao alterar gênero.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(generoLabel);
        frame.add(generoComboBox);
        frame.add(alterarButton);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

}
