package br.Inatel.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Livro {

    public int IDlivro;
    public String Titulo;
    public String Autor;
    public String Genero;
    public Date AnoPublicacao;
    public int QuantidadeDisponivel;

    public Livro(int IDlivro, String Titulo, String Autor, String Genero, Date AnoPublicacao, int QuantidadeDisponivel) {
        this.IDlivro = IDlivro;
        this.Titulo = Titulo;
        this.Autor = Autor;
        this.Genero = Genero;
        this.AnoPublicacao = AnoPublicacao;
        this.QuantidadeDisponivel = QuantidadeDisponivel;
    }


    public static int create(Connection conn, Livro livro) throws SQLException {
        String sql = "INSERT INTO livro (Titulo, Autor, Genero, `Ano de publicacao`, `Quantidade disponivel`) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, livro.Titulo);
            stmt.setString(2, livro.Autor);
            stmt.setString(3, livro.Genero);
            stmt.setDate(4, livro.AnoPublicacao);
            stmt.setInt(5, livro.QuantidadeDisponivel);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public static Livro read(Connection conn, int IDlivro) throws SQLException {
        String sql = "SELECT * FROM livro WHERE IDlivro = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, IDlivro);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Livro(
                        rs.getInt("IDlivro"),
                        rs.getString("Titulo"),
                        rs.getString("Autor"),
                        rs.getString("Genero"),
                        rs.getDate("Ano de publicacao"),
                        rs.getInt("Quantidade disponivel")
                );
            }
        }
        return null;
    }

    public static List<Livro> readAll(Connection conn) throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livro";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                livros.add(new Livro(
                        rs.getInt("IDlivro"),
                        rs.getString("Titulo"),
                        rs.getString("Autor"),
                        rs.getString("Genero"),
                        rs.getDate("Ano de publicacao"),
                        rs.getInt("Quantidade disponivel")
                ));
            }
        }
        return livros;
    }

    public static void delete(Connection conn, int IDlivro) throws SQLException {
        String sql = "DELETE FROM livro WHERE IDlivro = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, IDlivro);
            stmt.executeUpdate();
        }
    }

}
