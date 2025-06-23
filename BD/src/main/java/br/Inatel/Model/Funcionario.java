package br.Inatel.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Funcionario {

    public int IDcracha;
    public String Nome;
    public String Turno;
    public int Idade;
    public String Cargo;

    public Funcionario(int IDcracha, String Nome, String Turno, int Idade, String Cargo) {
        this.IDcracha = IDcracha;
        this.Nome = Nome;
        this.Turno = Turno;
        this.Idade = Idade;
        this.Cargo = Cargo;
    }

    public static int create(Connection conn, Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO funcionario (Nome, Turno, Idade, Cargo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, funcionario.Nome);
            stmt.setString(2, funcionario.Turno);
            stmt.setInt(3, funcionario.Idade);
            stmt.setString(4, funcionario.Cargo);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public static Funcionario read(Connection conn, int IDcracha) throws SQLException {
        String sql = "SELECT * FROM funcionario WHERE IDcracha = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, IDcracha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Funcionario(
                        rs.getInt("IDcracha"),
                        rs.getString("Nome"),
                        rs.getString("Turno"),
                        rs.getInt("Idade"),
                        rs.getString("Cargo")
                );
            }
        }
        return null;
    }

    public static List<Funcionario> readAll(Connection conn) throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionario";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                funcionarios.add(new Funcionario(
                        rs.getInt("IDcracha"),
                        rs.getString("Nome"),
                        rs.getString("Turno"),
                        rs.getInt("Idade"),
                        rs.getString("Cargo")
                ));
            }
        }
        return funcionarios;
    }

    public static void delete(Connection conn, int IDcracha) throws SQLException {
        String sql = "DELETE FROM funcionario WHERE IDcracha = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, IDcracha);
            stmt.executeUpdate();
        }
    }
}

