package a3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class Admin extends Usuario {

    static Conexao con = new Conexao("root", "RootAdmin123");

    public Admin(int id, String nome, float altura, int idade, float peso, int frequencia, String genero) {
        super(id, nome, altura, idade, peso, frequencia, genero, true, frequencia);
    }

    public static void showAdminOptions(Connection connection) {
        String[] adminOptions = { "Exibir Todos os Usuarios","Mostrar Todos os Exercicios", "Alterar Usuario", "Alterar ou Cadastrar Exercicio", 
                                  "Cadastrar Usuario ou Admin", "Deletar Usuario", "Alterar Exercicio do Usuario"};

        JFrame frame = new JFrame("FitWeek : ADMIN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the title label properties
        JLabel tituloLabel = new JLabel("FitWeek : ADMIN");
        tituloLabel.setHorizontalAlignment(JLabel.CENTER);
        tituloLabel.setForeground(Color.RED);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(tituloLabel);

        frame.setLayout(new GridLayout(adminOptions.length + 1, 1));

        tituloLabel.setHorizontalAlignment(JLabel.CENTER);

        for (String option : adminOptions) {
            JButton button = new JButton(option);
            frame.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (option) {
                        case "Exibir Todos os Usuarios":
                            try {
                                Admin.exibirTodosUsuarios(connection);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            break;
                        case "Mostrar Todos os Exercicios":
                            try {
                                Admin.mostratTodosExercicios(connection);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            break;
                        case "Alterar Usuario":
                            try {
                                Admin.alterarUsuario(connection, new Scanner(System.in));
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            break;
                        case "Cadastrar Usuario ou Admin":
                            Admin.cadastrarAdmin(connection);
                            break;
                        case "Alterar ou Cadastrar Exercicio":
                            try {
                                Admin.alterarExercicio(connection, new Scanner(System.in));
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            break;
                        case "Deletar Usuario":
                            try {
                                Admin.deletarUsuario(connection, new Scanner(System.in));
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            break;

                        case "Alterar Exercicio do Usuario":
                            try {
                                Admin.AlterarExercicioDoUsuario(connection, new Scanner(System.in));
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            break;

                        default:
                            JOptionPane.showMessageDialog(null, "Opcao invalida.");
                            break;
                    }
                }
            });
        }

        frame.setSize(400, 600);
        frame.setVisible(true);

        frame.setLocationRelativeTo(null);

    }

    public static void cadastrarAdmin(Connection connection) {
        JFrame frame = new JFrame("Cadastro de Administrador");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(11, 2));

        JLabel nameLabel = new JLabel("Nome:");
        JTextField nameField = new JTextField();
        frame.add(nameLabel);
        frame.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        frame.add(emailLabel);
        frame.add(emailField);

        JLabel senhaLabel = new JLabel("Senha:");
        JPasswordField senhaField = new JPasswordField();
        frame.add(senhaLabel);
        frame.add(senhaField);

        JLabel senhaVerifLabel = new JLabel("Digite a senha novamente:");
        JPasswordField senhaVerifField = new JPasswordField();
        frame.add(senhaVerifLabel);
        frame.add(senhaVerifField);

        JLabel alturaLabel = new JLabel("Altura (metros):");
        JTextField alturaField = new JTextField();
        frame.add(alturaLabel);
        frame.add(alturaField);

        JLabel idadeLabel = new JLabel("Idade (anos):");
        JTextField idadeField = new JTextField();
        frame.add(idadeLabel);
        frame.add(idadeField);

        JLabel pesoLabel = new JLabel("Peso (quilos):");
        JTextField pesoField = new JTextField();
        frame.add(pesoLabel);
        frame.add(pesoField);

        JLabel frequenciaLabel = new JLabel("Frequência semanal de exercícios (0 a 7):");
        String[] frequenciaOptions = { "0", "1", "2", "3", "4", "5", "6", "7" };
        JComboBox<String> frequenciaComboBox = new JComboBox<>(frequenciaOptions);
        frame.add(frequenciaLabel);
        frame.add(frequenciaComboBox);

        JLabel generoLabel = new JLabel("Gênero:");
        String[] generoOptions = { "M", "F" };
        JComboBox<String> generoComboBox = new JComboBox<>(generoOptions);
        frame.add(generoLabel);
        frame.add(generoComboBox);

        JLabel adminLabel = new JLabel("Administrador (S/N):");
        String[] adminOptions = { "S", "N" };
        JComboBox<String> adminComboBox = new JComboBox<>(adminOptions);
        frame.add(adminLabel);
        frame.add(adminComboBox);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nameField.getText();
                String email = emailField.getText();
                String senha = new String(senhaField.getPassword());
                String senhaVerif = new String(senhaVerifField.getPassword());
                float altura = Float.parseFloat(alturaField.getText());
                int idade = Integer.parseInt(idadeField.getText());
                float peso = Float.parseFloat(pesoField.getText());
                int frequencia = Integer.parseInt((String) frequenciaComboBox.getSelectedItem());
                String genero = (String) generoComboBox.getSelectedItem();
                boolean admin = ((String) adminComboBox.getSelectedItem()).equalsIgnoreCase("S");

                if (!senha.equals(senhaVerif)) {
                    JOptionPane.showMessageDialog(frame, "As senhas não coincidem. Por favor, tente novamente.");
                    return;
                }

                String sql = "INSERT INTO usuario (nome, email, senha, altura, idade, peso, frequencia, genero, admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
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
                        JOptionPane.showMessageDialog(frame, "Cadastro realizado com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erro ao realizar cadastro.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao realizar cadastro: " + ex.getMessage());
                }
            }
        });
        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        frame.add(backButton);
        frame.add(cadastrarButton);

        frame.pack();
        frame.setVisible(true);

        frame.setLocationRelativeTo(null);
    }

    public static void exibirTodosUsuarios(Connection connection) throws SQLException {
        System.out.println("Voce escolheu exibir todos os usuarios no banco de dados.");

        JFrame frame = new JFrame("Lista de Usuários");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        String sql = "SELECT * FROM usuario";
        try (PreparedStatement statement = con.conectar().prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery(sql)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn("<html><b>" + metaData.getColumnName(i) + "</b></html>");
            }

            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getString(i);
                }
                tableModel.addRow(rowData);
            }
        }

        frame.setSize(1200, 600);
        frame.setVisible(true);

        frame.setLocationRelativeTo(null);

    }

    public static void deletarUsuario(Connection connection, Scanner sc) throws SQLException {
        System.out.println("Voce escolheu deletar um usuario do banco de dados.");
        String idInput = JOptionPane.showInputDialog(null, "Digite o ID do usuario que voce quer deletar:");
        int id = Integer.parseInt(idInput);
        String sql = "DELETE FROM usuario WHERE userID = ?";
        try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
            statement.setInt(1, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Usuario deletado com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Nao existe usuario com esse ID.");
            }
        }
    }

    public static void alterarUsuario(Connection connection, Scanner sc) throws SQLException {
        JFrame frame = new JFrame("Alterar Usuário");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel userIdLabel = new JLabel("ID do Usuário:");
        JTextField userIdField = new JTextField(10);
        JLabel emptyLabel3 = new JLabel("");
        JLabel emptyLabel4 = new JLabel("");
        JLabel opcaoLabel = new JLabel("Opção:");
        JComboBox<String> opcaoComboBox = new JComboBox<>(new String[]{"Nome", "Email", "Senha", "Altura", "Idade", "Peso", "Frequência", "Gênero", "Admin"});
        JButton alterarButton = new JButton("Alterar");

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(userIdField.getText());
                String opcao = (String) opcaoComboBox.getSelectedItem();

                // Perform the desired action based on the selected option
                switch (opcao) {
                    case "Nome":
                        try {
                            alterarNome(connection, sc, id);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case "Email":
                        try {
                            alterarEmail(connection, sc, id);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case "Senha":
                        try {
                            alterarSenha(connection, sc, id);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case "Altura":
                        try {
                            alterarAltura(connection, sc, id);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case "Idade":
                        try {
                            alterarIdade(connection, sc, id);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case "Peso":
                        try {
                            alterarPeso(connection, sc, id);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case "Frequência":
                        try {
                            alterarFrequencia(connection, sc, id);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case "Gênero":
                        try {
                            alterarGenero(connection, sc, id);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case "Admin":
                        try {
                            alterarAdmin(connection, sc, id);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            }
        });

        panel.add(userIdLabel);
        panel.add(userIdField);
        panel.add(opcaoLabel);
        panel.add(opcaoComboBox);
        panel.add(emptyLabel3);
        panel.add(emptyLabel4);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(alterarButton, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    static void AlterarExercicioDoUsuario(Connection connection, Scanner sc) throws SQLException {
        JFrame frame = new JFrame("Alterar Exercício do Usuário");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        JLabel userIdLabel = new JLabel("ID do Usuário:");
        JTextField userIdField = new JTextField(10);
        JLabel exercicioIdLabel = new JLabel("ID do Exercício:");
        JTextField exercicioIdField = new JTextField(10);
        JButton alterarButton = new JButton("Alterar");

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(userIdField.getText());
                int exercicioID = Integer.parseInt(exercicioIdField.getText());

                String sql = "UPDATE usuario SET exercicioID = ? WHERE userID = ?";
                try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
                    statement.setInt(1, exercicioID);
                    statement.setInt(2, id);
                    int linhasAfetadas = statement.executeUpdate();
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(frame, "Exercício adicionado com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erro ao adicionar exercício.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(userIdLabel);
        frame.add(userIdField);
        frame.add(exercicioIdLabel);
        frame.add(exercicioIdField);
        frame.add(alterarButton);

        frame.setVisible(true);
    }

private static void alterarNome(Connection connection, Scanner sc, int id) throws SQLException {
            JFrame frame = new JFrame("Alterar Nome do Usuário");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new FlowLayout());

            JLabel novoNomeLabel = new JLabel("Novo Nome:");
            JTextField novoNomeField = new JTextField(10);
            JButton alterarButton = new JButton("Alterar");

            alterarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String novoNome = novoNomeField.getText();
                    String sql = "UPDATE usuario SET nome = ? WHERE userID = ?";
                    try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
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

            frame.add(novoNomeLabel);
            frame.add(novoNomeField);
            frame.add(alterarButton);

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);
        }

private static void alterarEmail(Connection connection, Scanner sc, int id) throws SQLException {
            JFrame frame = new JFrame("Alterar Email do Usuário");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new FlowLayout());

            JLabel novoEmailLabel = new JLabel("Novo Email:");
            JTextField novoEmailField = new JTextField(10);
            JButton alterarButton = new JButton("Alterar");

            alterarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String novoEmail = novoEmailField.getText();
                    String sql = "UPDATE usuario SET email = ? WHERE userID = ?";
                    try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
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

            frame.add(novoEmailLabel);
            frame.add(novoEmailField);
            frame.add(alterarButton);

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);
        }

 private static void alterarSenha(Connection connection, Scanner sc, int id) throws SQLException {
            JFrame frame = new JFrame("Alterar Senha do Usuário");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new FlowLayout());

            JLabel novaSenhaLabel = new JLabel("Nova Senha:");
            JPasswordField novaSenhaField = new JPasswordField(10);
            JButton alterarButton = new JButton("Alterar");

            alterarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    char[] novaSenhaChars = novaSenhaField.getPassword();
                    String novaSenha = new String(novaSenhaChars);
                    String sql = "UPDATE usuario SET senha = ? WHERE userID = ?";
                    try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
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

            frame.add(novaSenhaLabel);
            frame.add(novaSenhaField);
            frame.add(alterarButton);

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);
        }

    private static void alterarAltura(Connection connection, Scanner sc, int id) throws SQLException {
            JFrame frame = new JFrame("Alterar Altura do Usuário");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new FlowLayout());

            JLabel novaAlturaLabel = new JLabel("Nova Altura (metros):");
            JTextField novaAlturaField = new JTextField(10);
            JButton alterarButton = new JButton("Alterar");

            alterarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String novaAlturaStr = novaAlturaField.getText();
                    float novaAltura = Float.parseFloat(novaAlturaStr);
                    String sql = "UPDATE usuario SET altura = ? WHERE userID = ?";
                    try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
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

            frame.add(novaAlturaLabel);
            frame.add(novaAlturaField);
            frame.add(alterarButton);

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);
        }
        
    private static void alterarIdade(Connection connection, Scanner sc, int id) throws SQLException {
            JFrame frame = new JFrame("Alterar Idade do Usuário");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new FlowLayout());

            JLabel novaIdadeLabel = new JLabel("Nova Idade (anos):");
            JTextField novaIdadeField = new JTextField(10);
            JButton alterarButton = new JButton("Alterar");

            alterarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String novaIdadeStr = novaIdadeField.getText();
                    int novaIdade = Integer.parseInt(novaIdadeStr);
                    String sql = "UPDATE usuario SET idade = ? WHERE userID = ?";
                    try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
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

            frame.add(novaIdadeLabel);
            frame.add(novaIdadeField);
            frame.add(alterarButton);

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);
        }

    private static void alterarPeso(Connection connection, Scanner sc, int id) throws SQLException {
        JFrame frame = new JFrame("Alterar Peso do Usuário");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        JLabel novoPesoLabel = new JLabel("Novo Peso (quilos):");
        JTextField novoPesoField = new JTextField(10);
        JButton alterarButton = new JButton("Alterar");

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String novoPesoStr = novoPesoField.getText();
                float novoPeso = Float.parseFloat(novoPesoStr);
                String sql = "UPDATE usuario SET peso = ? WHERE userID = ?";
                try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
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

        frame.add(novoPesoLabel);
        frame.add(novoPesoField);
        frame.add(alterarButton);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private static void alterarFrequencia(Connection connection, Scanner sc, int id) throws SQLException {
            JFrame frame = new JFrame("Alterar Frequência Semanal de Exercícios");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new FlowLayout());

            JLabel novaFrequenciaLabel = new JLabel("Nova Frequência (0 a 7):");
            JTextField novaFrequenciaField = new JTextField(10);
            JButton alterarButton = new JButton("Alterar");

            alterarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int novaFrequencia = Integer.parseInt(novaFrequenciaField.getText());
                    String sql = "UPDATE usuario SET frequencia = ? WHERE userID = ?";
                    try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
                        statement.setInt(1, novaFrequencia);
                        statement.setInt(2, id);
                        int linhasAfetadas = statement.executeUpdate();
                        if (linhasAfetadas > 0) {
                            JOptionPane.showMessageDialog(frame, "Frequência alterada com sucesso.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Erro ao alterar frequência.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            frame.add(novaFrequenciaLabel);
            frame.add(novaFrequenciaField);
            frame.add(alterarButton);

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);
        }

private static void alterarGenero(Connection connection, Scanner sc, int id) throws SQLException {
            JFrame frame = new JFrame("Alterar Gênero");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new FlowLayout());

            JLabel novoGeneroLabel = new JLabel("Novo Gênero (M ou F):");
            JTextField novoGeneroField = new JTextField(10);
            JButton alterarButton = new JButton("Alterar");

            alterarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String novoGenero = novoGeneroField.getText();
                    String sql = "UPDATE usuario SET genero = ? WHERE userID = ?";
                    try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
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

            frame.add(novoGeneroLabel);
            frame.add(novoGeneroField);
            frame.add(alterarButton);

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);
        }

    private static void alterarAdmin(Connection connection, Scanner sc, int id) throws SQLException {
        JFrame frame = new JFrame("Alterar Status de Admin");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        JLabel idLabel = new JLabel("ID do usuário:");
        JTextField idField = new JTextField(10);
        JLabel novoStatusLabel = new JLabel("Novo status de admin (S/N):");
        JTextField novoStatusField = new JTextField(10);
        JButton alterarButton = new JButton("Alterar");

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idUsuario = Integer.parseInt(idField.getText());
                String novoStatus = novoStatusField.getText();
                String sql = "UPDATE usuario SET admin = ? WHERE userID = ?";
                try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
                    statement.setString(1, novoStatus);
                    statement.setInt(2, idUsuario);
                    int linhasAfetadas = statement.executeUpdate();
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(frame, "Status de admin alterado com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erro ao alterar status de admin.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(idLabel);
        frame.add(idField);
        frame.add(novoStatusLabel);
        frame.add(novoStatusField);
        frame.add(alterarButton);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    static void alterarExercicio(Connection connection, Scanner sc) throws SQLException {
        String[] options = { "Cadastrar Exercicio", "Alterar Nome", "Alterar Intensidade", "Alterar MET" };
        JComboBox<String> dropdown = new JComboBox<>(options);

        // Create a JPanel with FlowLayout and set alignment to center
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(dropdown);

        // Set the preferred size of the dropdown
        dropdown.setPreferredSize(new Dimension(200, dropdown.getPreferredSize().height));

        JFrame frame = new JFrame("Admin");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panel);

        dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = dropdown.getSelectedIndex();
                try {
                    switch (selectedIndex) {
                        case 0:
                            CadastraExercicio(connection, new Scanner(System.in));
                            break;
                        case 1:
                            alterarNomeExercicio(connection, new Scanner(System.in), id);
                            break;
                        case 2:
                            alterarIntensidade(connection, new Scanner(System.in), id);
                            break;
                        case 3:
                            alterarMET(connection, new Scanner(System.in), id);
                            break;
                        default:
                            System.out.println("Opcao invalida.");
                            break;
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(dropdown);
        frame.setSize(400, 100);
        frame.setVisible(true);

        frame.setLocationRelativeTo(null);

    }

    public static void CadastraExercicio(Connection connection, Scanner sc) throws SQLException {
        JFrame frame = new JFrame("Cadastrar Exercicio");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel nameLabel = new JLabel("Nome do exercicio:");
        JTextField nameField = new JTextField(20);

        JLabel intensityLabel = new JLabel("Intensidade do exercicio (1 a 5):");
        String[] intensityOptions = { "1 - Fácil", "2 - Iniciante", "3 - Moderado", "4 - Avançado", "5 - Difícil" };
        JComboBox<String> intensityField = new JComboBox<>(intensityOptions);

        JLabel metLabel = new JLabel("MET do exercicio:");
        JTextField metField = new JTextField(20);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String exercicio = nameField.getText();
                String intensidade = intensityField.getSelectedItem().toString(); // Fix: Use getSelectedItem() instead
                                                                                  // of getText()

                float MET = Float.parseFloat(metField.getText());

                String sql = "INSERT INTO exercicios (exercicio, intensidade, fatorIntensidade, MET) VALUES (?, ?, ?, ?)";

                try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
                    statement.setString(1, exercicio);
                    statement.setString(2, intensidade.split(" - ")[1]);
                    statement.setInt(3, Integer.parseInt(intensidade.split(" - ")[0]));
                    statement.setFloat(4, MET);
                    int linhasAfetadas = statement.executeUpdate();
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(frame, "Cadastro realizado com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erro ao realizar cadastro.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(intensityLabel);
        frame.add(intensityField);
        frame.add(metLabel);
        frame.add(metField);
        frame.add(cadastrarButton);

        frame.setSize(400, 300);
        frame.setVisible(true);

        frame.setLocationRelativeTo(null);

    }

    private static void alterarMET(Connection connection, Scanner sc, int id) throws SQLException {
        JFrame frame = new JFrame("Alterar MET");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel idLabel = new JLabel("ID do exercicio:");
        JTextField idField = new JTextField();
        JLabel metLabel = new JLabel("Novo MET:");
        JTextField metField = new JTextField();

        JButton alterarButton = new JButton("Alterar");
        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int finalId = Integer.parseInt(idField.getText());
                    float novoMET = Float.parseFloat(metField.getText());
                    String sql = "UPDATE exercicios SET MET = ? WHERE exercicioID = ?";
                    try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
                        statement.setFloat(1, novoMET);
                        statement.setInt(2, finalId);
                        int linhasAfetadas = statement.executeUpdate();
                        if (linhasAfetadas > 0) {
                            JOptionPane.showMessageDialog(null, "MET alterado com sucesso.");
                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Erro ao alterar MET.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "ID inválido ou MET inválido.");
                }
            }
        });

        panel.add(idLabel);
        panel.add(idField);
        panel.add(metLabel);
        panel.add(metField);
        panel.add(new JLabel());
        panel.add(alterarButton);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

        frame.setLocationRelativeTo(null);

    }

    private static void alterarIntensidade(Connection connection, Scanner sc, int id) throws SQLException {
        JFrame frame = new JFrame("Alterar Intensidade");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel idLabel = new JLabel("ID do exercicio:");
        JTextField idField = new JTextField();
        JLabel intensidadeLabel = new JLabel("Nova intensidade:");
        String[] intensidadeOptions = { "1 - Fácil", "2 - Iniciante", "3 - Moderado", "4 - Avançado", "5 - Difícil" };
        JComboBox<String> intensidadeField = new JComboBox<>(intensidadeOptions);

        panel.add(idLabel);
        panel.add(idField);
        panel.add(intensidadeLabel);
        panel.add(intensidadeField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Alterar Intensidade", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            id = Integer.parseInt(idField.getText());
            String novaIntensidade = (String) intensidadeField.getSelectedItem();
            String sql = "UPDATE exercicios SET intensidade = ?, fatorIntensidade = ? WHERE exercicioID = ?";

            try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
                statement.setString(1, novaIntensidade.split(" - ")[1]);
                statement.setInt(2, Integer.parseInt(novaIntensidade.split(" - ")[0]));
                statement.setInt(3, id);
                int linhasAfetadas = statement.executeUpdate();
                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(null, "Intensidade alterada com sucesso.");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao alterar intensidade.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        frame.pack();
        frame.setVisible(true);

        frame.setLocationRelativeTo(null);

    }

    private static void alterarNomeExercicio(Connection connection, Scanner sc, int id) throws SQLException {
        JFrame frame = new JFrame("Alterar Nome do Exercício");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel idLabel = new JLabel("ID do exercício:");
        JTextField idField = new JTextField();
        JLabel nomeLabel = new JLabel("Novo nome do exercício:");
        JTextField nomeField = new JTextField();

        panel.add(idLabel);
        panel.add(idField);
        panel.add(nomeLabel);
        panel.add(nomeField);

        JButton button = new JButton("Alterar");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String novoNomeExercicio = nomeField.getText();
                String sql = "UPDATE exercicios SET exercicio = ? WHERE exercicioID = ?";

                try (PreparedStatement statement = con.conectar().prepareStatement(sql)) {
                    statement.setString(1, novoNomeExercicio);
                    statement.setInt(2, id);
                    int linhasAfetadas = statement.executeUpdate();
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(null, "Nome do exercício alterado com sucesso.");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao alterar nome do exercício.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);

        frame.setLocationRelativeTo(null);

    }

    public static void mostratTodosExercicios(Connection connection) throws SQLException {
        System.out.println("Voce escolheu exibir todos os exercicios no banco de dados.");

        JFrame frame = new JFrame("Lista de Exercícios");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        String sql = "SELECT * FROM exercicios";
        try (PreparedStatement statement = con.conectar().prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn("<html><b>" + metaData.getColumnName(i) + "</b></html>");
            }

            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getString(i);
                }
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        frame.pack();
        frame.setVisible(true);

        frame.setLocationRelativeTo(null);

    }

}
