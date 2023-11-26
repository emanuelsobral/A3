package a3;

import java.sql.*;
import java.util.Scanner;

public class Admin extends Usuario {
    public Admin(int id, String nome, float altura, int idade, float peso, int frequencia, String genero) {
        super(id, nome, altura, idade, peso, frequencia, genero, true, frequencia);
    }

    public static void cadastrarUsuario(Connection connection, Scanner sc) throws SQLException {
        // implementar a lógica para cadastrar um novo usuário
        System.out.print("Digite seu nome: ");
        String nome = sc.nextLine();
        System.out.print("Digite seu email: ");
        String email = sc.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = sc.nextLine();
        System.out.print("Digite sua altura em metros: ");
        float altura = sc.nextFloat();
        System.out.print("Digite sua idade em anos: ");
        int idade = sc.nextInt();
        System.out.print("Digite seu peso em quilos: ");
        float peso = sc.nextFloat();
        System.out.print("Digite sua frequencia semanal de exercicios (0 a 7): ");
        int frequencia = sc.nextInt();
        sc.nextLine(); // consumir a quebra de linha
        System.out.print("Digite seu genero (M ou F): ");
        String genero = sc.nextLine();
        boolean admin = false;

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
            statement.setBoolean(9, admin);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Cadastro realizado com sucesso.");
            } else {
                System.out.println("Erro ao realizar cadastro.");
            }
        }

    }

    public static void cadastrarAdmin(Connection connection, Scanner sc) throws SQLException {
        System.out.print("Digite seu nome: ");
        String nome = sc.nextLine();
        System.out.print("Digite seu email: ");
        String email = sc.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = sc.nextLine();
        System.out.print("Digite sua altura em metros: ");
        float altura = sc.nextFloat();
        System.out.print("Digite sua idade em anos: ");
        int idade = sc.nextInt();
        System.out.print("Digite seu peso em quilos: ");
        float peso = sc.nextFloat();
        System.out.print("Digite sua frequencia semanal de exercicios (0 a 7): ");
        int frequencia = sc.nextInt();
        sc.nextLine(); // consumir a quebra de linha
        System.out.print("Digite seu genero (M ou F): ");
        String genero = sc.nextLine();
        System.out.print("Voce e um administrador? (S/N): ");
        String resposta = sc.nextLine();
        boolean admin = resposta.equalsIgnoreCase("S");

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
            statement.setBoolean(9, admin);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Cadastro realizado com sucesso.");
            } else {
                System.out.println("Erro ao realizar cadastro.");
            }
        }
    }

    public static void exibirTodosUsuarios(Connection connection) throws SQLException {
        System.out.println("Voce escolheu exibir todos os usuarios no banco de dados.");
        String sql = "SELECT * FROM usuario";
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            printResultSet(resultSet);
        }
    }

    private static void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            System.out.printf("%-15s", metaData.getColumnName(i));
        }
        System.out.println();
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-15s", resultSet.getString(i));
            }
            System.out.println();
        }
    }

    public static void deletarUsuario(Connection connection, Scanner sc) throws SQLException {
        // implementar a lógica para deletar um usuário
        System.out.println("Voce escolheu deletar um usuario do banco de dados.");
        System.out.print("Digite o ID do usuario que voce quer deletar: ");
        int id = sc.nextInt();
        sc.nextLine(); // consumir a quebra de linha
        String sql = "DELETE FROM usuario WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Usuario deletado com sucesso.");
            } else {
                System.out.println("Nao existe usuario com esse ID.");
            }
        }
    }

    public static void alterarUsuario(Connection connection, Scanner sc) throws SQLException {
        System.out.println("Voce escolheu alterar informacoes de um usuario.");
        System.out.print("Digite o ID do usuario que voce quer alterar: ");
        int id = sc.nextInt();
        sc.nextLine(); // consumir a quebra de linha
        String sql = "SELECT * FROM usuario WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Usuario encontrado.");
                    int opcaoAlterar;
                    do {
                        System.out.println("Escolha qual informacao voce quer alterar: ");
                        System.out.println("1 - Nome");
                        System.out.println("2 - Email");
                        System.out.println("3 - Senha");
                        System.out.println("4 - Altura");
                        System.out.println("5 - Idade");
                        System.out.println("6 - Peso");
                        System.out.println("7 - Frequencia");
                        System.out.println("8 - Genero");
                        System.out.println("9 - Admin");
                        System.out.println("0 - Sair");
                        opcaoAlterar = sc.nextInt();
                        sc.nextLine(); // consumir a quebra de linha
                        switch (opcaoAlterar) {
                            case 1:
                                alterarNome(connection, sc, id);
                                break;
                            case 2:
                                alterarEmail(connection, sc, id);
                                break;
                            case 3:
                                alterarSenha(connection, sc, id);
                                break;
                            case 4:
                                alterarAltura(connection, sc, id);
                                break;
                            case 5:
                                alterarIdade(connection, sc, id);
                                break;
                            case 6:
                                alterarPeso(connection, sc, id);
                                break;
                            case 7:
                                alterarFrequencia(connection, sc, id);
                                break;
                            case 8:
                                alterarGenero(connection, sc, id);
                                break;
                            case 9:
                                alterarAdmin(connection, sc, id);
                                break;
                            case 10:
                                AlterarExercicioDoUsuario(connection, sc);
                                break;
                            case 0:
                                System.out.println("Saindo do modo de alteracao.");
                                break;
                            default:
                                System.out.println("Opcao invalida.");
                                break;
                        }
                    } while (opcaoAlterar != 0);
                } else {
                    System.out.println("Nao existe usuario com esse ID.");
                }
            }
        }
    }

    static void AlterarExercicioDoUsuario(Connection connection, Scanner sc) throws SQLException {
        System.out.println("Digite o id do usuario que voce quer alterar: ");
        int id = sc.nextInt();
        System.out.print("Digite o id do exercicio que voce quer adicionar ao usuario: ");
        int exercicioID = sc.nextInt();
        sc.nextLine(); // consumir a quebra de linha
        String sql = "UPDATE usuario SET exercicioID = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, exercicioID);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0){
                System.out.println("Exercicio adicionado com sucesso.");
            } else {
                System.out.println("Erro ao adicionar exercicio.");
            }
        }
    }

    private static void alterarNome(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite o novo nome: ");
        String novoNome = sc.nextLine();
        String sql = "UPDATE usuario SET nome = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, novoNome);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Nome alterado com sucesso.");
            } else {
                System.out.println("Erro ao alterar nome.");
            }
        }
    }

    private static void alterarEmail(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite o novo email: ");
        String novoEmail = sc.nextLine();
        String sql = "UPDATE usuario SET email = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, novoEmail);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Email alterado com sucesso.");
            } else {
                System.out.println("Erro ao alterar email.");
            }
        }
    }

    private static void alterarSenha(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite a nova senha: ");
        String novaSenha = sc.nextLine();
        String sql = "UPDATE usuario SET senha = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, novaSenha);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Senha alterada com sucesso.");
            } else {
                System.out.println("Erro ao alterar senha.");
            }
        }
    }

    private static void alterarAltura(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite a nova altura em metros: ");
        float novaAltura = sc.nextFloat();
        String sql = "UPDATE usuario SET altura = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setFloat(1, novaAltura);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Altura alterada com sucesso.");
            } else {
                System.out.println("Erro ao alterar altura.");
            }
        }
    }

    private static void alterarIdade(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite a nova idade em anos: ");
        int novaIdade = sc.nextInt();
        String sql = "UPDATE usuario SET idade = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, novaIdade);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Idade alterada com sucesso.");
            } else {
                System.out.println("Erro ao alterar idade.");
            }
        }
    }

    private static void alterarPeso(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite o novo peso em quilos: ");
        float novoPeso = sc.nextFloat();
        String sql = "UPDATE usuario SET peso = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setFloat(1, novoPeso);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Peso alterado com sucesso.");
            } else {
                System.out.println("Erro ao alterar peso.");
            }
        }
    }

    private static void alterarFrequencia(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite a nova frequencia semanal de exercicios (0 a 7): ");
        int novaFrequencia = sc.nextInt();
        String sql = "UPDATE usuario SET frequencia = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, novaFrequencia);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Frequencia alterada com sucesso.");
            } else {
                System.out.println("Erro ao alterar frequencia.");
            }
        }
    }

    private static void alterarGenero(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite o novo genero (M ou F): ");
        String novoGenero = sc.nextLine();
        String sql = "UPDATE usuario SET genero = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, novoGenero);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Genero alterado com sucesso.");
            } else {
                System.out.println("Erro ao alterar genero.");
            }
        }
    }

    private static void alterarAdmin(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.println("Voce escolheu alterar informacoes de um administrador.");
        System.out.print("Digite o ID do administrador que voce quer alterar: ");
        id = sc.nextInt();
        sc.nextLine(); // consumir a quebra de linha
        String sql = "SELECT * FROM usuario WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Administrador encontrado.");
                    int opcaoAlterar;
                    System.out.println("Escolha qual informacao voce quer alterar: ");
                    System.out.println("1 - Nome");
                    System.out.println("2 - Email");
                    System.out.println("3 - Senha");
                    opcaoAlterar = sc.nextInt();
                    sc.nextLine(); // consumir a quebra de linha
                    switch (opcaoAlterar) {
                        case 1:
                            alterarNome(connection, sc, id);
                            break;
                        case 2:
                            alterarEmail(connection, sc, id);
                            break;
                        case 3:
                            alterarSenha(connection, sc, id);
                            break;

                    }
                    while (opcaoAlterar != 0)
                        ;
                } else {
                    System.out.println("Nao existe usuario com esse ID.");
                }
            }
        }
    }

    static void alterarExercicio(Connection connection, Scanner sc) throws SQLException {
        System.out.println("Voce escolheu alterar informacoes de um exercicio.");
        int opcaoAlterar;
        System.out.println("Escolha qual informacao voce quer alterar: ");
        System.out.println("-------------Cadastro--------------");
        System.out.println("0 - Cadastrar Exercicio");
        System.out.println("------------Alteraçoes-------------");
        System.out.println("1 - Nome");
        System.out.println("2 - Intensidade");
        System.out.println("3 - Fator de Intensidade");
        System.out.println("4 - MET");
        opcaoAlterar = sc.nextInt();
        sc.nextLine(); // consumir a quebra de linha
        switch (opcaoAlterar) {
            case 0:
                CadastraExercicio(connection, sc);
                break;
            case 1:
                alterarNomeExercicio(connection, sc, id);
                break;
            case 2:
                alterarIntensidade(connection, sc, id);
                break;
            case 3:
                alterarFatorIntensidade(connection, sc, id);
                break;
            case 4:
                alterarMET(connection, sc, id);
                break;
            default:
                System.out.println("Opcao invalida.");
                break;
            }
        }

    private static void CadastraExercicio(Connection connection, Scanner sc) throws SQLException {
        System.out.print("Digite o nome do exercicio: ");
        String exercicio = sc.nextLine();
        System.out.print("Digite a intensidade do exercicio: ");
        String intensidade = sc.nextLine();
        System.out.print("Digite o fator de intensidade do exercicio: ");
        int fatorIntensidade = sc.nextInt();
        System.out.print("Digite o MET do exercicio: ");
        float MET = sc.nextFloat();
        String sql = "INSERT INTO exercicios (exercicio, intensidade, fatorIntensidade, MET) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, exercicio);
            statement.setString(2, intensidade);
            statement.setInt(3, fatorIntensidade);
            statement.setFloat(4, MET);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Cadastro realizado com sucesso.");
            } else {
                System.out.println("Erro ao realizar cadastro.");
            }
        }
    }

    private static void alterarMET(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite o id do exercicio que voce quer alterar: ");
        id = sc.nextInt();
        System.out.print("Digite o novo MET: ");
        float novoMET = sc.nextFloat();
        String sql = "UPDATE exercicios SET MET = ? WHERE exercicioID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setFloat(1, novoMET);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0){
                System.out.println("MET alterado com sucesso.");
            } else {
                System.out.println("Erro ao alterar MET.");
            }
        }
    }

    private static void alterarFatorIntensidade(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite o id do exercicio que voce quer alterar: ");
        id = sc.nextInt();
        System.out.print("Digite o novo fator de intensidade: ");
        int novoFatorIntensidade = sc.nextInt();
        String sql = "UPDATE exercicios SET fatorIntensidade = ? WHERE exercicioID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, novoFatorIntensidade);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0){
                System.out.println("Fator de intensidade alterado com sucesso.");
            } else {
                System.out.println("Erro ao alterar fator de intensidade.");
            }
        }
    }

    private static void alterarIntensidade(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite o id do exercicio que voce quer alterar: ");
        id = sc.nextInt();
        System.out.print("Digite a nova intensidade: ");
        String novaIntensidade = sc.nextLine();
        String sql = "UPDATE exercicios SET intensidade = ? WHERE exercicioID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, novaIntensidade);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0){
                System.out.println("Intensidade alterada com sucesso.");
            } else {
                System.out.println("Erro ao alterar intensidade.");
            }
        }
    }

    private static void alterarNomeExercicio(Connection connection, Scanner sc, int id) throws SQLException {
        System.out.print("Digite o id do exercicio que voce quer alterar: ");
        id = sc.nextInt();
        System.out.print("Digite o novo nome do exercicio: ");
        String novoNomeExercicio = sc.nextLine();
        String sql = "UPDATE exercicios SET exercicio = ? WHERE exercicioID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, novoNomeExercicio);
            statement.setInt(2, id);
            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0){
                System.out.println("Nome do exercicio alterado com sucesso.");
            } else {
                System.out.println("Erro ao alterar nome do exercicio.");
            }
        }
    }
    static void mostratTodosExercicios(Connection connection) throws SQLException {
        System.out.println("Voce escolheu exibir todos os exercicios no banco de dados.");
        String sql = "SELECT * FROM exercicios";
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            printResultSet(resultSet);
        }
    }

    
}
