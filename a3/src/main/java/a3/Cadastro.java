package a3;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class Cadastro extends JFrame {

    private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JTextField alturaField;
    private JTextField idadeField;
    private JTextField pesoField;
    private JTextField frequenciaField;
    private JTextField generoField;
    private JButton cadastrarButton;

    Conexao con = new Conexao("root", "RootAdmin123");

    public Cadastro() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Usuário");
        setLayout(new GridLayout(9, 2, 10, 10));

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel senhaLabel = new JLabel("Senha:");
        senhaField = new JPasswordField();
        JLabel alturaLabel = new JLabel("Altura (metros):");
        alturaField = new JTextField();
        JLabel idadeLabel = new JLabel("Idade (anos):");
        idadeField = new JTextField();
        JLabel pesoLabel = new JLabel("Peso (quilos):");
        pesoField = new JTextField();
        JLabel frequenciaLabel = new JLabel("Frequência semanal de exercícios (0 a 7):");
        frequenciaField = new JTextField();
        JLabel generoLabel = new JLabel("Gênero (M ou F):");
        generoField = new JTextField();
        cadastrarButton = new JButton("Cadastrar");

        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    cadastrarUsuario();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        add(nomeLabel);
        add(nomeField);
        add(emailLabel);
        add(emailField);
        add(senhaLabel);
        add(senhaField);
        add(alturaLabel);
        add(alturaField);
        add(idadeLabel);
        add(idadeField);
        add(pesoLabel);
        add(pesoField);
        add(frequenciaLabel);
        add(frequenciaField);
        add(generoLabel);
        add(generoField);
        add(new JLabel());
        add(cadastrarButton);

        pack();
        setLocationRelativeTo(null);
    }

    private void cadastrarUsuario() throws SQLException {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());
        float altura = Float.parseFloat(alturaField.getText());
        int idade = Integer.parseInt(idadeField.getText());
        float peso = Float.parseFloat(pesoField.getText());
        int frequencia = Integer.parseInt(frequenciaField.getText());
        String genero = generoField.getText();

        Conexao con = new Conexao("root", "RootAdmin123");
        Connection connection = con.conectar();
        
        if (connection != null) {
            try {
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
                    statement.setBoolean(9, false);
                    int linhasAfetadas = statement.executeUpdate();
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao realizar cadastro.");
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.");
        }
    }
}
