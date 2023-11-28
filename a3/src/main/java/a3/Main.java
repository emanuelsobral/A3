package a3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Conexao con = new Conexao("root", "RootAdmin123");

        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        JLabel emailLabel = new JLabel("Email:");
        JLabel senhaLabel = new JLabel("Senha:");

        JTextField emailField = new JTextField(20);
        JPasswordField senhaField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");

        JButton registerButton = new JButton("Se cadastrar");

        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(senhaLabel);
        frame.add(senhaField);
        frame.add(loginButton);
        frame.add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String senha = new String(senhaField.getPassword());

                try (Connection connection = con.conectar()) {
                    Login login = new Login(email, senha);
                    Usuario usuario = login.fazerLogin(connection);

                    if (usuario != null) {
                        JOptionPane.showMessageDialog(null, "Login efetuado com sucesso.");

                        if (usuario instanceof Admin) {
                            String[] adminOptions = {"Exibir Todos os Usuarios", "Deletar Usuario", "Alterar Usuario", "Cadastrar Usuario", "Cadastrar Admin", "Alterar Exercicio ou Cadastrar Exercicio", "Alterar Exercicio do Usuario", "Mostrar Todos os Exercicios"};
                            JPanel adminPanel = new JPanel();
                            adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));

                            for (String option : adminOptions) {
                                JButton button = new JButton(option);
                                adminPanel.add(button);
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
                                            case "Deletar Usuario":
                                                try {
                                                    Admin.deletarUsuario(connection, new Scanner(System.in));
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
                                            case "Cadastrar Usuario":
                                                try {
                                                    Admin.cadastrarUsuario(connection, new Scanner(System.in));
                                                } catch (SQLException e1) {
                                                    e1.printStackTrace();
                                                }
                                                break;
                                            case "Cadastrar Admin":
                                                try {
                                                    Admin.cadastrarAdmin(connection, new Scanner(System.in));
                                                } catch (SQLException e1) {
                                                    e1.printStackTrace();
                                                }
                                                break;
                                            case "Alterar Exercicio ou Cadastrar Exercicio":
                                                try {
                                                    Admin.alterarExercicio(connection, new Scanner(System.in));
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
                                            case "Mostrar Todos os Exercicios":
                                                try {
                                                    Admin.mostratTodosExercicios(connection);
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

                            JOptionPane.showMessageDialog(null, adminPanel, "Admin Options", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            String[] userOptions = {"Selecionar um Exercicio", "Exibir Dados", "Alterar Dados", "Deletar Conta"};
                            JPanel userPanel = new JPanel();
                            userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));

                            for (String option : userOptions) {
                                JButton button = new JButton(option);
                                userPanel.add(button);
                                button.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        switch (option) {
                                            case "Selecionar um Exercicio":
                                                try {
                                                    usuario.selecionarExercicio(connection, new Scanner(System.in));
                                                } catch (SQLException e1) {
                                                    e1.printStackTrace();
                                                }
                                                break;
                                            case "Exibir Dados":
                                                try {
                                                    usuario.exibirInformacoesUsuario(connection);
                                                } catch (SQLException e1) {
                                                    e1.printStackTrace();
                                                }
                                                break;
                                            case "Alterar Dados":
                                                try {
                                                    usuario.alterarDados(connection);
                                                } catch (SQLException e1) {
                                                    e1.printStackTrace();
                                                }
                                                break;
                                            case "Deletar Conta":
                                                try {
                                                    usuario.deletarConta(connection, new Scanner(System.in));
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

                            JOptionPane.showMessageDialog(null, userPanel, "User Options", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Email ou senha incorretos.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                try (Connection connection = con.conectar()) {
                    Cadastro.fazerCadastro(connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            
        });
            frame.setVisible(true);
    }
}
