package br.Inatel.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Usuario {

    public String CPF;
    public String Nome;
    public String Endereco;
    public int Idade;

    public Usuario(String CPF, String Nome, String Endereco, int Idade) {
        this.CPF = CPF;
        this.Nome = Nome;
        this.Endereco = Endereco;
        this.Idade = Idade;
    }

    public static boolean create(Connection conn, Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (CPF, Nome, Endereco, Idade) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.CPF);
            stmt.setString(2, usuario.Nome);
            stmt.setString(3, usuario.Endereco);
            stmt.setInt(4, usuario.Idade);
            return stmt.executeUpdate() == 1;
        }
    }

    public static Usuario read(Connection conn, String CPF) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE CPF = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, CPF);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getString("CPF"),
                        rs.getString("Nome"),
                        rs.getString("Endereco"),
                        rs.getInt("Idade")
                );
            }
        }
        return null;
    }

    public static List<Usuario> readAll(Connection conn) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getString("CPF"),
                        rs.getString("Nome"),
                        rs.getString("Endereco"),
                        rs.getInt("Idade")
                ));
            }
        }
        return usuarios;
    }

    public static void delete(Connection conn, String CPF) throws SQLException {
        String sql = "DELETE FROM usuario WHERE CPF = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, CPF);
            stmt.executeUpdate();
        }
    }

}
