package a3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(4, 2));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailTextField = new JTextField();

        JLabel senhaLabel = new JLabel("Senha:");
        JPasswordField senhaPasswordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailTextField.getText();
                String senha = new String(senhaPasswordField.getPassword());

                // Import Class Connection
                Conexao con = new Conexao("root", "RootAdmin123");

                // import Metodo para conectar com o banco de dados
                try (Connection connection = con.conectar()) {
                    Login login = new Login(email, senha);
                    Usuario usuario = login.fazerLogin(connection);

                    if (usuario != null) {
                        JOptionPane.showMessageDialog(frame, "Login efetuado com sucesso.");

                        if (usuario instanceof Admin) {
                            String[] opcoesAdmin = { "Exibir Todos os Usuarios", "Deletar Usuario", "Alterar Usuario",
                                    "Cadastrar Usuario", "Cadastrar Admin" };
                            int opcaoAdmin = JOptionPane.showOptionDialog(frame, "Você é um administrador.",
                                    "Opções do Administrador", JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.PLAIN_MESSAGE, null, opcoesAdmin, opcoesAdmin[0]);

                            switch (opcaoAdmin) {
                                case 0:
                                    Admin.exibirTodosUsuarios(connection);
                                    break;
                                case 1:
                                    Admin.deletarUsuario(connection, null);
                                    break;
                                case 2:
                                    Admin.alterarUsuario(connection, null);
                                    break;
                                case 3:
                                    Admin.cadastrarUsuario(connection, null);
                                    break;
                                case 4:
                                    Admin.cadastrarAdmin(connection, null);
                                    break;
                                default:
                                    JOptionPane.showMessageDialog(frame, "Opcao invalida.");
                                    break;
                            }
                        } else {
                            String[] opcoesUsuario = { "Exibir Dados", "Alterar Dados", "Deletar Conta" };
                            int opcaoUsuario = JOptionPane.showOptionDialog(frame, "Você é um usuario.",
                                    "Opções do Usuario", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                                    opcoesUsuario, opcoesUsuario[0]);

                            switch (opcaoUsuario) {
                                case 0:
                                    usuario.exibirDados(connection);
                                    break;
                                case 1:
                                    usuario.alterarDados(connection);
                                    break;
                                case 2:
                                    usuario.deletarConta(connection, null);
                                    break;
                                default:
                                    JOptionPane.showMessageDialog(frame, "Opcao invalida.");
                                    break;
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Email ou senha incorretos.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton cadastrarButton = new JButton("Fazer Cadastro");
        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Cadastro cadastro = new Cadastro();
                cadastro.setVisible(true);
            }
        });

        frame.add(emailLabel);
        frame.add(emailTextField);
        frame.add(senhaLabel);
        frame.add(senhaPasswordField);
        frame.add(new JLabel());
        frame.add(loginButton);
        frame.add(new JLabel());
        frame.add(cadastrarButton);

        frame.setVisible(true);
    }
}