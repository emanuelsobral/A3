package a3;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Import Class Connection
        Conexao con = new Conexao("root", "RootAdmin123");

        Scanner sc = new Scanner(System.in);

        // import Metodo para conectar com o banco de dados
        try (Connection connection = con.conectar()) {
            System.out.println("Conexao com o banco de dados estabelecida.");
            System.out.println("Escolha uma opcao: ");
            System.out.println("1 - Fazer login");
            System.out.println("2 - Fazer cadastro");
            int opcao = sc.nextInt();
            sc.nextLine(); // consumir a quebra de linha
            switch (opcao) {
                case 1:
                    fazerLogin(connection, sc);
                    break;
                case 2:
                    Cadastro.fazerCadastro(connection, sc);
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }

    private static void fazerLogin(Connection connection, Scanner sc) throws SQLException {
        System.out.print("Digite seu email: ");
        String email = sc.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = sc.nextLine();

        Login login = new Login(email, senha);
        Usuario usuario = login.fazerLogin(connection);

        if (usuario != null) {
            System.out.println("Login efetuado com sucesso.");
            if (usuario instanceof Admin) {
                System.out.println("Você é um administrador.");
                System.out.println("Escolha uma opcao: ");
                System.out.println("1 - Exibir Todos os Usuarios");
                System.out.println("2 - Deletar Usuario");
                System.out.println("3 - Alterar Usuario");
                System.out.println("4 - Cadastratrar Usuario");
                System.out.println("5 - cadastrar Admin");
                int opcaoAdmin = sc.nextInt();

                switch (opcaoAdmin) {
                    case 1:
                        Admin.exibirTodosUsuarios(connection);
                        break;
                    case 2:
                        Admin.deletarUsuario(connection, sc);
                        break;
                    case 3:
                        Admin.alterarUsuario(connection, sc);
                        break;
                    case 4:
                        Admin.cadastrarUsuario(connection, sc);
                        break;
                    case 5:
                        Admin.cadastrarAdmin(connection, sc);
                        break;
                    default:
                        System.out.println("Opcao invalida.");
                        break;
                }
            } else {
                System.out.println("Você é um usuário.");
                System.out.println("Escolha uma opcao: ");
                System.out.println("1 - Exibir Seus Dados");
                System.out.println("2 - Alterar Seus Dadoas");
                int opcaoUser = sc.nextInt();

                switch (opcaoUser) {
                    case 1:
                        usuario.exibirDados(connection);
                        break;
                    case 2:
                        usuario.alterarDados(connection);
                        break;
                    default:
                        System.out.println("Opcao invalida.");
                        break;
                    }
            }
        } else {
            System.out.println("Email ou senha incorretos.");
        }
    }

}