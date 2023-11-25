package a3;

public class MainGUI extends JFrame {
    private JTextField emailTextField;
    private JPasswordField senhaPasswordField;
    private JButton loginButton;
    private JButton cadastroButton;
    private JTextArea outputTextArea;

    public MainGUI() {
        setTitle("Sistema de Login e Cadastro");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));

        JLabel emailLabel = new JLabel("Email:");
        emailTextField = new JTextField();
        JLabel senhaLabel = new JLabel("Senha:");
        senhaPasswordField = new JPasswordField();

        loginPanel.add(emailLabel);
        loginPanel.add(emailTextField);
        loginPanel.add(senhaLabel);
        loginPanel.add(senhaPasswordField);

        loginButton = new JButton("Fazer Login");
        cadastroButton = new JButton("Fazer Cadastro");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(cadastroButton);

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);

        add(loginPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(new JScrollPane(outputTextArea), BorderLayout.NORTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        });

        cadastroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerCadastro();
            }
        });
    }

    private void fazerLogin() {
        String email = emailTextField.getText();
        String senha = new String(senhaPasswordField.getPassword());

        Conexao con = new Conexao("root", "RootAdmin123");

        try (Connection connection = con.conectar()) {
            Login login = new Login(email, senha);
            Usuario usuario = login.fazerLogin(connection);

            if (usuario != null) {
                outputTextArea.append("Login efetuado com sucesso.\n");
                if (usuario instanceof Admin) {
                    outputTextArea.append("Você é um administrador.\n");
                    Admin admin = (Admin) usuario;
                    // chamar métodos de admin aqui
                } else {
                    outputTextArea.append("Você é um usuário.\n");
                    outputTextArea.append("Escolha uma opcao:\n");
                    outputTextArea.append("1 - Exibir Seus Dados\n");
                    outputTextArea.append("2 - Alterar Seus Dados\n");
                    int opcaoUser = Integer.parseInt(JOptionPane.showInputDialog("Escolha uma opcao:"));

                    switch (opcaoUser) {
                        case 1:
                            usuario.exibirDados(connection);
                            break;
                        case 2:
                            usuario.alterarDados(connection);
                            break;
                        default:
                            outputTextArea.append("Opcao invalida.\n");
                            break;
                    }
                }
            } else {
                outputTextArea.append("Email ou senha incorretos.\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void fazerCadastro() {
        String nome = JOptionPane.showInputDialog("Digite seu nome:");
        String email = JOptionPane.showInputDialog("Digite seu email:");
        String senha = JOptionPane.showInputDialog("Digite sua senha:");
        float altura = Float.parseFloat(JOptionPane.showInputDialog("Digite sua altura em metros:"));
        int idade = Integer.parseInt(JOptionPane.showInputDialog("Digite sua idade em anos:"));
        float peso = Float.parseFloat(JOptionPane.showInputDialog("Digite seu peso em quilos:"));
        int frequencia = Integer.parseInt(JOptionPane.showInputDialog("Digite sua frequencia semanal de exercicios (0 a 7):"));
        String genero = JOptionPane.showInputDialog("Digite seu genero (M ou F):");
        boolean admin = false;

        Conexao con = new Conexao("root", "RootAdmin123");

        try (Connection connection = con.conectar()) {
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
                    outputTextArea.append("Cadastro realizado com sucesso.\n");
                } else {
                    outputTextArea.append("Erro ao realizar cadastro.\n");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainGUI mainGUI = new MainGUI();
                mainGUI.setVisible(true);
            }
        });
    }
}
