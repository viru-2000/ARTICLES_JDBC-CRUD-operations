package com.example.article;

import com.example.articles.dao.ArticleDAO;
import com.example.articles.model.Article;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Scanner;

public class ArticleManager {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/JDBC", "root", "password")) {
            ArticleDAO articleDAO = new ArticleDAO(connection);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Create Article");
                System.out.println("2. Get Article by ID");
                System.out.println("3. Get All Articles");
                System.out.println("4. Update Article");
                System.out.println("5. Delete Article");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1: {
                        System.out.print("Enter ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Category (painting/sculpture/artifact): ");
                        String category = scanner.nextLine();
                        System.out.print("Enter Date Created (yyyy-mm-dd): ");
                        Date dateCreated = Date.valueOf(scanner.nextLine());
                        System.out.print("Enter Creator Name: ");
                        String creatorName = scanner.nextLine();

                        Article article = new Article(id, name, category, dateCreated, creatorName);
                        articleDAO.createArticle(article);
                        System.out.println("Article created successfully.");
                        break;
                    }
                    case 2: {
                        System.out.print("Enter ID: ");
                        int id = scanner.nextInt();
                        Article article = articleDAO.getArticleById(id);
                        System.out.println(article != null ? article : "Article not found.");
                        break;
                    }
                    case 3: {
                        Collection<Article> articles = articleDAO.getAllArticles();
                        articles.forEach(System.out::println);
                        break;
                    }
                    case 4: {
                        System.out.print("Enter ID to Update: ");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter New Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter New Category (painting/sculpture/artifact): ");
                        String category = scanner.nextLine();
                        System.out.print("Enter New Date Created (yyyy-mm-dd): ");
                        Date dateCreated = Date.valueOf(scanner.nextLine());
                        System.out.print("Enter New Creator Name: ");
                        String creatorName = scanner.nextLine();

                        Article article = new Article(id, name, category, dateCreated, creatorName);
                        articleDAO.updateArticle(article);
                        System.out.println("Article updated successfully.");
                        break;
                    }
                    case 5: {
                        System.out.print("Enter ID to Delete: ");
                        int id = scanner.nextInt();
                        articleDAO.deleteArticle(id);
                        System.out.println("Article deleted successfully.");
                        break;
                    }
                    case 6:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
