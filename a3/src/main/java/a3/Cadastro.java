package a3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.*;

public class Cadastro {

    Conexao con = new Conexao("root", "RootAdmin123");

    public static void fazerCadastro(Connection connection) throws SQLException {
        JTextField nomeField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        JTextField alturaField = new JTextField();
        JTextField idadeField = new JTextField();
        JTextField pesoField = new JTextField();
        JTextField generoField = new JTextField();

        Object[] fields = {
                "Nome:", nomeField,
                "Email:", emailField,
                "Senha:", senhaField,
                "Altura (metros):", alturaField,
                "Idade (anos):", idadeField,
                "Peso (quilos):", pesoField,
                "Gênero (M ou F):", generoField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Cadastro", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String email = emailField.getText();
            String senha = new String(senhaField.getPassword());
            float altura = Float.parseFloat(alturaField.getText());
            int idade = Integer.parseInt(idadeField.getText());
            float peso = Float.parseFloat(pesoField.getText());
            String genero = generoField.getText();

            cadastrarUsuario(connection, nome, email, senha, altura, idade, peso, genero);
        }
    }

    private static void cadastrarUsuario(Connection connection, String nome, String email, String senha, float altura,
            int idade, float peso, String genero) throws SQLException {
        String sql = "INSERT INTO usuario (nome, email, senha, altura, idade, peso, genero, admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, email);
            statement.setString(3, senha);
            statement.setFloat(4, altura);
            statement.setInt(5, idade);
            statement.setFloat(6, peso);
            statement.setString(7, genero);
            statement.setBoolean(8, false);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao realizar cadastro.");
            }
        }
    }
}