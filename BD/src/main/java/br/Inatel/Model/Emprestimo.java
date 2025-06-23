package br.Inatel.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Emprestimo {

    public int IDemprestimo;
    public Date DataInicio;
    public Date DataFinal;
    public String Status;
    public String Usuario_CPF;
    public int Livro_IDlivro;
    public int Funcionario_IDcracha;

    public Emprestimo(int IDemprestimo, Date DataInicio, Date DataFinal, String Status, String Usuario_CPF, int Livro_IDlivro, int Funcionario_IDcracha) {
        this.IDemprestimo = IDemprestimo;
        this.DataInicio = DataInicio;
        this.DataFinal = DataFinal;
        this.Status = Status;
        this.Usuario_CPF = Usuario_CPF;
        this.Livro_IDlivro = Livro_IDlivro;
        this.Funcionario_IDcracha = Funcionario_IDcracha;
    }

    public static int create(Connection conn, Emprestimo emprestimo) throws SQLException {
        String sql = "INSERT INTO emprestimo (`Data inicio`, `Data final`, Status, Usuario_CPF, Livro_IDlivro, Funcionario_IDcracha) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, emprestimo.DataInicio);
            stmt.setDate(2, emprestimo.DataFinal);
            stmt.setString(3, emprestimo.Status);
            stmt.setString(4, emprestimo.Usuario_CPF);
            stmt.setInt(5, emprestimo.Livro_IDlivro);
            stmt.setInt(6, emprestimo.Funcionario_IDcracha);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public static Emprestimo read(Connection conn, int IDemprestimo) throws SQLException {
        String sql = "SELECT * FROM emprestimo WHERE IDemprestimo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, IDemprestimo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Emprestimo(
                        rs.getInt("IDemprestimo"),
                        rs.getDate("Data inicio"),
                        rs.getDate("Data final"),
                        rs.getString("Status"),
                        rs.getString("Usuario_CPF"),
                        rs.getInt("Livro_IDlivro"),
                        rs.getInt("Funcionario_IDcracha")
                );
            }
        }
        return null;
    }

    public static List<Emprestimo> readAll(Connection conn) throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                emprestimos.add(new Emprestimo(
                        rs.getInt("IDemprestimo"),
                        rs.getDate("Data inicio"),
                        rs.getDate("Data final"),
                        rs.getString("Status"),
                        rs.getString("Usuario_CPF"),
                        rs.getInt("Livro_IDlivro"),
                        rs.getInt("Funcionario_IDcracha")
                ));
            }
        }
        return emprestimos;
    }

    public static void delete(Connection conn, int IDemprestimo) throws SQLException {
        String sql = "DELETE FROM emprestimo WHERE IDemprestimo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, IDemprestimo);
            stmt.executeUpdate();
        }
    }
}

