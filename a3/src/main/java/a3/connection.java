package a3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Codigo de Conexao com o banco de dados
public class connection {
    private String BD = "a3";
    private String conexao = "jdbc:mysql://localhost:3306/" + BD;
    private String usuarioSQL;
    private String senhaSQL;

    //Construtor
    public connection(String usuarioSQL, String senhaSQL) {
        this.usuarioSQL = usuarioSQL;
        this.senhaSQL = senhaSQL;
    }

    //Metodo para conectar com o banco de dados
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(conexao, usuarioSQL, senhaSQL);
    }
}

