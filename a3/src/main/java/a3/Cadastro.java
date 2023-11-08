ackage a3;

import java.sql.SQLException; 
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.util.Scanner; 

public class ClassDelete{
    Conexao con = new Conexao(usuarioSQL: "Araujo", senhaSQL: "791016");

    public static void fazerCadastro(Connection connection, Scanner sc, Usuario usuario) throws SQLExcpetion{}

        if(usuario = null){
            System.out.println("Digite o nome do usuário");
            String nome = sc.nextLine();
            System.out.println("Digite o email");
            String email = sc.nextLine();
            System.out.println("Digite a sua senha");
            String senha = sc.nextLine();
            System.out.println("Digite sua altura");
            float altura = sc.nextFloat();
            System.out.println("Digite sua idade");
            int idade = sc.nextInt();
            System.out.println("Digite sua frequencia de exercicios");
            int frequencia = sc.nextInt();
            System.out.println("Digite seu genero(M ou F):" *);
            String genero = sc.nextLine();
    }
        if(usuario instanceof Admin){
            cadastroAdmin(connection, nome, email, senha, altura, idade, peso, frequencia, genero);
        }
        else {
        cadastroUsuario(connection, nome, email, senha, altura, idade, peso, frequencia, genero);
        }
        else {
        System.out.println("Voce ja esta cadastrado")
        }

        private static void cadastroUsurio(Connection connection, String nome, String email, String senha, float altura, int Idade, float frequencia, String genero) throws SQLException {
string SQL = "INSERT INTO usuario(nome, email, senha, altura, idade, peso, frequencia, genero, admin)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
try (PreparedStatement statement = connection.preparedStatement(sql)){
    statement.setString (1, nome);
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
    System.out.println("Usuario cadastrado com sucesso!");

} else {
    System.out.prinln("Não foi possivel cadastrar o usuario.");

        }
        private static void cadastroAdmin(Connection connection, String nome, String email, String senha, float altura, int Idade, float frequencia, String genero) throws SQLException {
string SQL = "INSERT INTO usuario(nome, email, senha, altura, idade, peso, frequencia, genero, admin)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
try (PreparedStatement statement = connection.preparedStatement(sql)){
    statement.setString (1, nome);
     statement.setString(2, email);
            statement.setString(3, senha);
            statement.setFloat(4, altura);
            statement.setInt(5, idade);
            statement.setFloat(6, peso);
            statement.setInt(7, frequencia);
            statement.setString(8, genero);
            Scanner scanner = new Scanner (System.in);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Voce deseja cadastrar um novo administrador? (S/N)");
  String admin = scanner.nextLine();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Voce deseja cadastrar um novo administrador? (S/N)");
            String admin = scanner.nextLine();
            if (admin.equals("S")) {
                statement.setBoolean(9, true);
            } else {
                statement.setBoolean(9, false);
            }
int linhasAfetadas = statement.executeUpdate(); 
if (linhasAfetadas > 0) {
    System.out.println("Usuario cadastrado com sucesso!");

} else {
    System.out.prinln("Não foi possivel cadastrar o usuario.");

        }
    }
    
