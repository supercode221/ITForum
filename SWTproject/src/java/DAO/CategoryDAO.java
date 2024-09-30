/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO extends DBContext {

    private Connection connection;

    public CategoryDAO() {
        this.connection = conn;
    }

    public Category getCategoryById(int categoryId) throws SQLException {
        try {
            String query = "SELECT * FROM Categories WHERE categoryId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Category(
                        resultSet.getInt("categoryId"),
                        resultSet.getString("categoryName"),
                        resultSet.getInt("status")
                );
            }
        } catch (Exception e) {
            System.out.println("Get category by id: " + e);
        }

        return null;
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM Categories";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            categories.add(new Category(
                    resultSet.getInt("categoryId"),
                    resultSet.getString("categoryName"),
                    resultSet.getInt("status")
            ));
        }
        return categories;
    }
    
    public List<Category> getAllCategoriesActive() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM Categories where status = 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            categories.add(new Category(
                    resultSet.getInt("categoryId"),
                    resultSet.getString("categoryName"),
                    resultSet.getInt("status")
            ));
        }
        return categories;
    }

    public boolean insertCategory(Category category) throws SQLException {
        String query = "INSERT INTO Categories (categoryName, status) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, category.getCategoryName());
        statement.setInt(2, category.getStatus());

        return statement.executeUpdate() > 0;
    }

    public boolean updateCategory(Category category) throws SQLException {
        String query = "UPDATE Categories SET categoryName = ?, status = ? WHERE categoryId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, category.getCategoryName());
        statement.setInt(2, category.getStatus());
        statement.setInt(3, category.getCategoryId());

        return statement.executeUpdate() > 0;
    }

    public boolean deleteCategory(int categoryId) throws SQLException {
        String query = "DELETE FROM Categories WHERE categoryId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, categoryId);

        return statement.executeUpdate() > 0;
    }
}
