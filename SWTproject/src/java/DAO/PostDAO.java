/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Category;
import Model.Comment;
import Model.Like;
import Model.Post;
import Model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO extends DBContext {

    private Connection connection;

    public PostDAO() {
        this.connection = conn;
    }

    public Post getPostById(int postId) throws SQLException {
        String query = "SELECT * FROM Posts WHERE postId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, postId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Post post = new Post(
                    resultSet.getInt("postId"),
                    resultSet.getInt("userId"),
                    resultSet.getInt("categoryId"),
                    resultSet.getString("title"),
                    resultSet.getString("content"),
                    resultSet.getDate("createdDate"),
                    resultSet.getInt("status")
            );
            post.setLike(getLikesByPostId(postId));
            post.setComment(getCommentsByPostId(postId));
            post.setUser(this.getUserById(resultSet.getInt("userId")));
            post.setCategory(this.getCategoryById(resultSet.getInt("categoryId")));
            return post;
        }
        return null;
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

    public List<Post> getAllPosts() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM Posts";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Post post = new Post(
                    resultSet.getInt("postId"),
                    resultSet.getInt("userId"),
                    resultSet.getInt("categoryId"),
                    resultSet.getString("title"),
                    resultSet.getString("content"),
                    resultSet.getDate("createdDate"),
                    resultSet.getInt("status")
            );
            post.setLike(getLikesByPostId(post.getPostId()));
            post.setComment(getCommentsByPostId(post.getPostId()));
            post.setUser(this.getUserById(resultSet.getInt("userId")));
            post.setCategory(this.getCategoryById(resultSet.getInt("categoryId")));
            posts.add(post);
        }
        return posts;
    }

    public List<Post> getAllPostsByUserId(int userId) throws SQLException {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM Posts where userId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Post post = new Post(
                    resultSet.getInt("postId"),
                    resultSet.getInt("userId"),
                    resultSet.getInt("categoryId"),
                    resultSet.getString("title"),
                    resultSet.getString("content"),
                    resultSet.getDate("createdDate"),
                    resultSet.getInt("status")
            );
            post.setLike(getLikesByPostId(post.getPostId()));
            post.setComment(getCommentsByPostId(post.getPostId()));
            post.setUser(this.getUserById(resultSet.getInt("userId")));
            post.setCategory(this.getCategoryById(resultSet.getInt("categoryId")));
            posts.add(post);
        }
        return posts;
    }

    public List<Post> getAllPostsActive() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM Posts WHERE status = 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            Post post = new Post(
                    resultSet.getInt("postId"),
                    resultSet.getInt("userId"),
                    resultSet.getInt("categoryId"),
                    resultSet.getString("title"),
                    resultSet.getString("content"),
                    resultSet.getDate("createdDate"),
                    resultSet.getInt("status")
            );
            post.setLike(getLikesByPostId(post.getPostId()));
            post.setComment(getCommentsByPostId(post.getPostId()));
            post.setUser(this.getUserById(resultSet.getInt("userId")));
            post.setCategory(this.getCategoryById(resultSet.getInt("categoryId")));
            posts.add(post);
        }
        return posts;
    }

    public List<Post> getAllPostsActiveByCategory(int category, String key) throws SQLException {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM Posts WHERE status = 1 and title like ?";
        if(category > 0) {
            query += " and categoryId = ?";
        }
        
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + key + "%");
        if(category > 0) {
            statement.setInt(2, category);
        }
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Post post = new Post(
                    resultSet.getInt("postId"),
                    resultSet.getInt("userId"),
                    resultSet.getInt("categoryId"),
                    resultSet.getString("title"),
                    resultSet.getString("content"),
                    resultSet.getDate("createdDate"),
                    resultSet.getInt("status")
            );
            post.setLike(getLikesByPostId(post.getPostId()));
            post.setComment(getCommentsByPostId(post.getPostId()));
            post.setUser(this.getUserById(resultSet.getInt("userId")));
            post.setCategory(this.getCategoryById(resultSet.getInt("categoryId")));
            posts.add(post);
        }
        return posts;
    }

    public List<Comment> getCommentsByPostId(int postId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM Comments WHERE postId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, postId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Comment comment = new Comment(
                    resultSet.getInt("commentId"),
                    resultSet.getInt("postId"),
                    resultSet.getInt("userId"),
                    resultSet.getString("content"),
                    resultSet.getTimestamp("createdDate")
            );
            comments.add(comment);
        }
        return comments;
    }

    public List<Like> getLikesByPostId(int postId) throws SQLException {
        List<Like> likes = new ArrayList<>();
        String query = "SELECT * FROM Likes WHERE postId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, postId);
        ResultSet resultSet = statement.executeQuery();

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

    public boolean insertPost(Post post) throws SQLException {
        String query = "INSERT INTO Posts (userId, categoryId, title, content, status) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, post.getUserId());
        statement.setInt(2, post.getCategoryId());
        statement.setString(3, post.getTitle());
        statement.setString(4, post.getContent());
        statement.setInt(5, post.getStatus());

        return statement.executeUpdate() > 0;
    }

    public boolean updatePost(Post post) throws SQLException {
        String query = "UPDATE Posts SET userId = ?, categoryId = ?, title = ?, content = ?, status = ? WHERE postId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, post.getUserId());
        statement.setInt(2, post.getCategoryId());
        statement.setString(3, post.getTitle());
        statement.setString(4, post.getContent());
        statement.setInt(5, post.getStatus());
        statement.setInt(6, post.getPostId());

        return statement.executeUpdate() > 0;
    }

    public boolean deletePost(int postId) throws SQLException {
        String query = "DELETE FROM Posts WHERE postId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, postId);

        return statement.executeUpdate() > 0;
    }
}
