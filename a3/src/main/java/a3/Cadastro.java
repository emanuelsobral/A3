package a3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Cadastro {

    Conexao con = new Conexao("root", "RootAdmin123");

    public static void fazerCadastro(Connection connection) throws SQLException {
        String nome = JOptionPane.showInputDialog(null, "Digite seu nome:");
        String email = JOptionPane.showInputDialog(null, "Digite seu email:");
        String senha = JOptionPane.showInputDialog(null, "Digite sua senha:");
        float altura = Float.parseFloat(JOptionPane.showInputDialog(null, "Digite sua altura em metros:"));
        int idade = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite sua idade em anos:"));
        float peso = Float.parseFloat(JOptionPane.showInputDialog(null, "Digite seu peso em quilos:"));
        String genero = JOptionPane.showInputDialog(null, "Digite seu genero (M ou F):");
        cadastrarUsuario(connection, nome, email, senha, altura, idade, peso, genero);
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