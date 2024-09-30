/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CategoryDAO;
import DAO.PostDAO;
import Model.Category;
import Model.Post;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
    
    private PostDAO postDAO;
    private CategoryDAO categoryDAO;
    private User userLogin;
    
    public void init() {
        postDAO = new PostDAO();
        categoryDAO = new CategoryDAO();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        userLogin = (User) session.getAttribute("userLogin");
        if (userLogin == null) {
            response.sendRedirect("login");
            return;
        }
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "insert":
                    insertPost(request, response);
                    break;
                case "update":
                    updatePost(request, response);
                    break;
                case "delete":
                    deletePost(request, response);
                    break;
                default:
                    listPosts(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        userLogin = (User) session.getAttribute("userLogin");
        if(userLogin == null) {
            response.sendRedirect("login");
            return;
        }
        String action = request.getParameter("action");
        try {
            if (action == null) {
                action = "list";
            }
            switch (action) {
                case "edit":
                    showEditForm(request, response);
                    break;
                case "add":
                    showAddForm(request, response);
                    break;
                default:
                    listPosts(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Category> categories = categoryDAO.getAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("add-post.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int postId = Integer.parseInt(request.getParameter("postId"));
        Post existingPost = postDAO.getPostById(postId);
        request.setAttribute("post", existingPost);
        
        List<Category> categories = categoryDAO.getAllCategories();
        request.setAttribute("categories", categories);
        
        request.getRequestDispatcher("edit-form.jsp").forward(request, response);
    }
    
    private void listPosts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        try {
            List<Post> posts = postDAO.getAllPostsByUserId(userLogin.getUserId());
            request.setAttribute("posts", posts);
            request.getRequestDispatcher("post-list.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("List post by user: " + e);
        }
    }
    
    private void insertPost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int status = Integer.parseInt(request.getParameter("status"));
        Post newPost = new Post(0, userId, categoryId, title, content, null, status);
        postDAO.insertPost(newPost);
        response.sendRedirect("PostServlet?action=list");
    }
    
    private void updatePost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int postId = Integer.parseInt(request.getParameter("postId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int status = Integer.parseInt(request.getParameter("status"));
        Post post = new Post(postId, userId, categoryId, title, content, null, status);
        postDAO.updatePost(post);
        response.sendRedirect("PostServlet?action=list");
    }
    
    private void deletePost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int postId = Integer.parseInt(request.getParameter("postId"));
        postDAO.deletePost(postId);
        response.sendRedirect("PostServlet?action=list");
    }
}
