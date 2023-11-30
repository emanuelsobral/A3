package a3;

import javax.swing.*;

import a3.Usuario.UserInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Conexao con = new Conexao("root", "RootAdmin123");

        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(9, 1));

        JLabel tituloLabel = new JLabel("FitWeek");
        tituloLabel.setHorizontalAlignment(JLabel.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tituloLabel.setForeground(new Color(128, 0, 128)); 

        JLabel emptyLabel2 = new JLabel(); 

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setHorizontalAlignment(JLabel.CENTER);

        JTextField emailField = new JTextField(20);
        JPasswordField senhaField = new JPasswordField(20);

        JLabel emptyLabel4 = new JLabel();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Se cadastrar");

        panel.add(tituloLabel);
        panel.add(emptyLabel2); 
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(senhaLabel);
        panel.add(senhaField);
        panel.add(emptyLabel4);
        panel.add(loginButton);
        panel.add(registerButton);

        frame.add(panel, BorderLayout.CENTER);

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
                            frame.dispose();
                            Admin.showAdminOptions(connection);
                        } else {
                            frame.dispose();

                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Usuario usuario = new Usuario(con.conectar()); // Create an instance of the enclosing class with the connection
                                        UserInterface userInterface = usuario.new UserInterface(con.conectar());
                                        userInterface.setVisible(true);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
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
