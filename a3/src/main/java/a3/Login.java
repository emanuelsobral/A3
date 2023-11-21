package a3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Delete{

    Conexao con = new Conexao("root", "usjt");

    public static void deletarUsuario(Connection connection, Scanner sc, Usuario usuario) throws SQLException {
        if (usuario == null) {
            System.out.println("Você precisa estar logado para deletar um usuário.");
            return;
        }

        if (usuario instanceof Admin) {
            System.out.print("Digite o id do usuário que deseja deletar: ");
            int id = sc.nextInt();
            sc.nextLine(); // consumir a quebra de linha
       //     deletarUsuario(connection, id); teacher não soube dizer oq é isso, perguntou pra ti emanu 
        } else {
            System.out.print("Tem certeza que deseja deletar sua conta? (S ou N): ");
            String resposta = sc.nextLine();
            if (resposta.equals("S") || resposta.equals("s")) {
              //  deletarUsuario(connection, usuario.); método get id não existe querido
            } else {
                System.out.println("Operação cancelada.");
            }
        }
    }

    private static void deletarUsuario(Connection connection, Usuario usuario) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, usuario.id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Usuário deletado com sucesso.");
            } else {
                System.out.println("Erro ao deletar usuário.");
            }
        }
    }
}
