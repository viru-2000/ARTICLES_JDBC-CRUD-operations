package com.example.articles.dao;

import com.example.articles.model.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArticleDAO {
    private Connection connection;

    public ArticleDAO(Connection connection) {
        this.connection = connection;
    }

    public void createArticle(Article article) throws SQLException {
        String sql = "INSERT INTO article_master (id, name, category, dateCreated, creatorName) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, article.getId());
            pstmt.setString(2, article.getName());
            pstmt.setString(3, article.getCategory());
            pstmt.setDate(4, new java.sql.Date(article.getDateCreated().getTime()));
            pstmt.setString(5, article.getCreatorName());
            pstmt.executeUpdate();
        }
    }

    public Article getArticleById(int id) throws SQLException {
        String sql = "SELECT * FROM article_master WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Article(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDate("dateCreated"),
                        rs.getString("creatorName")
                    );
                }
            }
        }
        return null;
    }

    public Collection<Article> getAllArticles() throws SQLException {
        String sql = "SELECT * FROM article_master";
        List<Article> articles = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                articles.add(new Article(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDate("dateCreated"),
                    rs.getString("creatorName")
                ));
            }
        }
        return articles;
    }

    public void updateArticle(Article article) throws SQLException {
        String sql = "UPDATE article_master SET name = ?, category = ?, dateCreated = ?, creatorName = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, article.getName());
            pstmt.setString(2, article.getCategory());
            pstmt.setDate(3, new java.sql.Date(article.getDateCreated().getTime()));
            pstmt.setString(4, article.getCreatorName());
            pstmt.setInt(5, article.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteArticle(int id) throws SQLException {
        String sql = "DELETE FROM article_master WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
