/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DBContext {

    private Connection connection;

    public UserDAO() {
        this.connection = conn;
    }

    public User getUserById(int userId) {
        String query = "SELECT * FROM Users WHERE userId = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User u = new User(
                        resultSet.getInt("userId"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("fullName"),
                        resultSet.getDate("createdDate"),
                        resultSet.getString("role")
                );
                u.setStatus(resultSet.getInt("status"));
                return u;
            }
        } catch (Exception e) {
            System.out.println("Get user: " + e);
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            User u = new User(
                    resultSet.getInt("userId"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    resultSet.getString("fullName"),
                    resultSet.getDate("createdDate"),
                    resultSet.getString("role")
            );
            u.setStatus(resultSet.getInt("status"));
            users.add(u);
        }
        return users;
    }

    public boolean insertUser(User user) throws SQLException {
        try {
            String query = "INSERT INTO Users (username, password, email, fullName, role, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFullName());
            statement.setString(5, user.getRole());
            statement.setInt(6, user.getStatus());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return false;
    }

    public boolean updateUser(User user) throws SQLException {
        try {
            String query = "UPDATE Users SET username = ?, password = ?, email = ?, fullName = ?, role = ?, status=? WHERE userId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFullName());
            statement.setString(5, user.getRole());
            statement.setInt(6, user.getStatus());
            statement.setInt(7, user.getUserId());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return false;
    }

    public boolean updateInformationUser(User user) throws SQLException {
        String query = "UPDATE Users SET email = ?, fullName = ? WHERE userId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getFullName());
        statement.setInt(3, user.getUserId());
        return statement.executeUpdate() > 0;
    }

    public boolean updatePasswordUser(User user) throws SQLException {
        String query = "UPDATE Users SET password = ? WHERE userId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getPassword());
        statement.setInt(2, user.getUserId());
        return statement.executeUpdate() > 0;
    }

    public boolean deleteUser(int userId) throws SQLException {
        String query = "DELETE FROM Likes WHERE userId = ?;\n"
                + "DELETE FROM Comments WHERE userId = ?;\n"
                + "DELETE FROM Posts WHERE userId = ?;\n"
                + "DELETE FROM Users WHERE userId = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.setInt(2, userId);
        statement.setInt(3, userId);
        statement.setInt(4, userId);

        return statement.executeUpdate() > 0;
    }

    public User login(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User u = new User(
                        resultSet.getInt("userId"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("fullName"),
                        resultSet.getDate("createdDate"),
                        resultSet.getString("role")
                );
                u.setStatus(resultSet.getInt("status"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println("Login: " + e);
        }
        return null;
    }

    public User isExistUser(String username) {
        String query = "SELECT * FROM Users WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User u = new User(
                        resultSet.getInt("userId"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("fullName"),
                        resultSet.getDate("createdDate"),
                        resultSet.getString("role")
                );
                u.setStatus(resultSet.getInt("status"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println("Login: " + e);
        }
        return null;
    }
}
