package com.prohire.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Lectura de variables de entorno de Railway
            String host = System.getenv("MYSQLHOST");
            String port = System.getenv("MYSQLPORT");
            String db = System.getenv("MYSQLDATABASE");
            String user = System.getenv("MYSQLUSER");
            String password = System.getenv("MYSQLPASSWORD");

            // Valores por defecto para pruebas locales en NetBeans
            if (host == null) host = "localhost";
            if (port == null) port = "3306";
            if (db == null) db = "prohire_db";
            if (user == null) user = "root";
            if (password == null) password = "";

            String url = "jdbc:mysql://" + host + ":" + port + "/" + db + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error: El driver de MySQL no está configurado correctamente.", e);
        }
    }
}