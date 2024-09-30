/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.CategoryDAO;
import DAO.PostDAO;
import Model.Category;
import Model.Post;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author HP
 */
@WebServlet("/PostListServlet")
public class PostListServlet extends HttpServlet {
    private PostDAO postDAO;

    public void init() {
        postDAO = new PostDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            CategoryDAO categoryDao = new CategoryDAO();
            List<Category> categoies = categoryDao.getAllCategoriesActive();
            List<Post> posts = postDAO.getAllPostsActive();
            request.setAttribute("categories", categoies);
            request.setAttribute("posts", posts);
            request.getRequestDispatcher("post-list-user.jsp").forward(request, response);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String category = request.getParameter("categoryId");
            String key = request.getParameter("searchKeyword");
            CategoryDAO categoryDao = new CategoryDAO();
            List<Category> categoies = categoryDao.getAllCategoriesActive();
            request.setAttribute("categories", categoies);
            List<Post> posts = postDAO.getAllPostsActiveByCategory(Integer.parseInt(category), key);
            request.setAttribute("currentCategory", category);
            request.setAttribute("searchKeyword", key);
            request.setAttribute("posts", posts);
            request.getRequestDispatcher("post-list-user.jsp").forward(request, response);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}