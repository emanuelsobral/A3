package a3;

import java.sql.*;

public class Admin extends Usuario {
    public Admin(int id, String nome, float altura, int idade, float peso, int frequencia, String genero) {
        super(id, nome, altura, idade, peso, frequencia, genero, true);
    }

    public void cadastrarUsuario(Connection connection) {
        // implementar a lógica para cadastrar um novo usuário
    }

    public void cadastrarAdmin(Connection connection) {
        // implementar a lógica para cadastrar um novo administrador
    }

    public void exibirTodosUsuarios(Connection connection) {
        // implementar a lógica para exibir todos os usuários
    }

    public void deletarUsuario(Connection connection) {
        // implementar a lógica para deletar um usuário
    }

    public void alterarUsuario(Connection connection) {
        // implementar a lógica para alterar as informações de um usuário
    }
}