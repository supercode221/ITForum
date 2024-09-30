/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Comment;
import Model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO extends DBContext {

    private Connection connection;

    public CommentDAO() {
        this.connection = conn;
    }

    public Comment getCommentById(int commentId) throws SQLException {
        String query = "SELECT * FROM Comments WHERE commentId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, commentId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Comment c = new Comment(
                    resultSet.getInt("commentId"),
                    resultSet.getInt("postId"),
                    resultSet.getInt("userId"),
                    resultSet.getString("content"),
                    resultSet.getDate("createdDate"));
            c.setUser(this.getUserById(resultSet.getInt("userId")));
            return c;
        }
        return null;
    }

    public List<Comment> getAllComments() throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM Comments";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            Comment c = new Comment(
                    resultSet.getInt("commentId"),
                    resultSet.getInt("postId"),
                    resultSet.getInt("userId"),
                    resultSet.getString("content"),
                    resultSet.getDate("createdDate"));
            c.setUser(this.getUserById(resultSet.getInt("userId")));
            comments.add(c);
        }
        return comments;
    }

    public User getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM Users WHERE userId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new User(
                    resultSet.getInt("userId"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    resultSet.getString("fullName"),
                    resultSet.getDate("createdDate"),
                    resultSet.getString("role")
            );
        }
        return null;
    }

    public boolean insertComment(Comment comment) throws SQLException {
        String query = "INSERT INTO Comments (postId, userId, content) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, comment.getPostId());
        statement.setInt(2, comment.getUserId());
        statement.setString(3, comment.getContent());

        return statement.executeUpdate() > 0;
    }

    public boolean updateComment(Comment comment) throws SQLException {
        String query = "UPDATE Comments SET postId = ?, userId = ?, content = ? WHERE commentId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, comment.getPostId());
        statement.setInt(2, comment.getUserId());
        statement.setString(3, comment.getContent());
        statement.setInt(4, comment.getCommentId());

        return statement.executeUpdate() > 0;
    }

    public boolean deleteComment(int commentId) throws SQLException {
        String query = "DELETE FROM Comments WHERE commentId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, commentId);

        return statement.executeUpdate() > 0;
    }

    public List<Comment> getCommentsByPostId(int postId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM Comments WHERE postId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, postId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Comment comment = new Comment(
                            resultSet.getInt("commentId"),
                            resultSet.getInt("postId"),
                            resultSet.getInt("userId"),
                            resultSet.getString("content"),
                            resultSet.getDate("createdDate")
                    );
                    comment.setUser(this.getUserById(resultSet.getInt("userId")));
                    comments.add(comment);
                }
            }
        }
        return comments;
    }
}
