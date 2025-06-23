package br.Inatel.DAO;

import java.sql.*;

public abstract class ConnectionDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/trabaio";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}
