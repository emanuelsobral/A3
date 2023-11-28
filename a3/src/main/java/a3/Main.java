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
                System.out.println("6 - Alterar Exercicio ou Cadastrar Exercicio");
                System.out.println("7 - Alterar Exercicio do Usuario");
                System.out.println("8 - Mostrar Todos os Exercicios");
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
                    case 6:
                        Admin.alterarExercicio(connection, sc);
                        break;
                    case 7:
                        Admin.AlterarExercicioDoUsuario(connection, sc);
                        break;
                    case 8:
                        Admin.mostratTodosExercicios(connection);
                        break;
                    default:
                        System.out.println("Opcao invalida.");
                        break;
                }
            } else {
                int opcaoUsuario;
                do {
                    clearConsole();

                    System.out.println("Você é um usuario.");
                    usuario.DadosUsuarioExibir(connection);
                    System.out.println("Escolha uma opcao: ");
                    System.out.println("0 - Selecionar um Exercicio");
                    System.out.println("1 - Exibir Dados");
                    System.out.println("2 - Alterar Dados");
                    System.out.println("3 - Deletar Conta");
                    System.out.println("9 - Sair");
                    opcaoUsuario = sc.nextInt();

                    switch (opcaoUsuario) {
                        case 0:
                            usuario.selecionarExercicio(connection, sc);
                            break;
                        case 1:
                            usuario.exibirDados(connection);
                            break;
                        case 2:
                            usuario.alterarDados(connection);
                            break;
                        case 3:
                            usuario.deletarConta(connection, sc);
                            break;
                        case 9:
                            System.out.println("Saindo...");
                            break;
                        default:
                            System.out.println("Opcao invalida.");
                            break;
                    }
                } while (opcaoUsuario != 9);
            }
        } else {
            System.out.println("Email ou senha incorretos.");
        }
    }

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

