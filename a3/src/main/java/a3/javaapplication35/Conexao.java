/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication35;

import java.sql.*;

//Codigo de Conexao com o banco de dados
public class Conexao {
    private String BD = "a3";
    private String conexao = "jdbc:mysql://localhost:3306/" + BD;
    private String usuarioSQL;
    private String senhaSQL;

    //Construtor
    public Conexao(String usuarioSQL, String senhaSQL) {
        this.usuarioSQL = usuarioSQL;
        this.senhaSQL = senhaSQL;
    }

    //Metodo para conectar com o banco de dados
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(conexao, usuarioSQL, senhaSQL);
    }
}