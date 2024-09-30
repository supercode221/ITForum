/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DAO;

import Model.Like;
import Model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikeDAO extends DBContext {
    private Connection connection;

    public LikeDAO() {
        this.connection = conn;
    }

    public Like getLikeById(int likeId) throws SQLException {
        String query = "SELECT * FROM Likes WHERE likeId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, likeId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Like like = new Like(
                resultSet.getInt("likeId"),
                resultSet.getInt("postId"),
                resultSet.getInt("userId")
            );
            like.setUser(this.getUserById(resultSet.getInt("userId")));
            return like;
        }
        return null;
    }

    public Like getLikeByPostAndUser(int postId, int userId) throws SQLException {
        String query = "SELECT * FROM Likes WHERE postId = ? AND userId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, postId);
        statement.setInt(2, userId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Like like = new Like(
                resultSet.getInt("likeId"),
                resultSet.getInt("postId"),
                resultSet.getInt("userId")
            );
            like.setUser(this.getUserById(resultSet.getInt("userId")));
            return like;
        }
        return null;
    }

    public List<Like> getAllLikes() throws SQLException {
        List<Like> likes = new ArrayList<>();
        String query = "SELECT * FROM Likes";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            Like like = new Like(
                resultSet.getInt("likeId"),
                resultSet.getInt("postId"),
                resultSet.getInt("userId")
            );
            like.setUser(this.getUserById(resultSet.getInt("userId")));
            likes.add(like);
        }
        return likes;
    }

    public boolean insertLike(Like like) throws SQLException {
        String query = "INSERT INTO Likes (postId, userId) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, like.getPostId());
        statement.setInt(2, like.getUserId());

        return statement.executeUpdate() > 0;
    }

    public boolean deleteLike(int likeId) throws SQLException {
        String query = "DELETE FROM Likes WHERE likeId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, likeId);

        return statement.executeUpdate() > 0;
    }

    public int getLikesCountByPostId(int postId) throws SQLException {
        String query = "SELECT COUNT(*) AS likeCount FROM Likes WHERE postId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, postId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("likeCount");
        }
        return 0;
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
        }catch(Exception e) {
            System.out.println("Get user: " + e);
        }
        return null;
    }
}
