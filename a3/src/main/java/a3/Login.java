package a3;

import java.sql.*;

public class Login {
    private String email;
    private String senha;

    public Login(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    // getters e setters para cada campo

    public Usuario fazerLogin(Connection connection) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, this.email);
            statement.setString(2, this.senha);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("userID");
                    String nome = resultSet.getString("nome");
                    float altura = resultSet.getFloat("altura");
                    int idade = resultSet.getInt("idade");
                    float peso = resultSet.getFloat("peso");
                    int frequencia = resultSet.getInt("frequencia");
                    String genero = resultSet.getString("genero");
                    boolean admin = resultSet.getBoolean("admin");
                    int exercicioID = resultSet.getInt("exercicioID");

                    if (admin) {
                        return new Admin(id, nome, altura, idade, peso, frequencia, genero);
                    } else {
                        return new Usuario(id, nome, altura, idade, peso, frequencia, genero, false, exercicioID);
                    }
                } else {
                    return null;
                }
            }
        }
    }
}
