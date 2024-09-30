/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CategoryDAO;
import Model.Category;
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
@WebServlet(name = "ManagerCategoryController", urlPatterns = {"/categories"})
public class ManagerCategoryController extends HttpServlet {

    private CategoryDAO categoryDAO;

    public void init() throws ServletException {
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listCategories(request, response);
                break;
            case "showAddForm":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteCategory(request, response);
                break;
            default:
                listCategories(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                addCategory(request, response);
                break;
            case "update":
                updateCategory(request, response);
                break;
            default:
                listCategories(request, response);
        }
    }

    private void listCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Category> categories = categoryDAO.getAllCategories();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/listCategories.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error fetching categories: " + e.getMessage());
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/addCategory.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        try {
            Category category = categoryDAO.getCategoryById(categoryId);
            request.setAttribute("category", category);
            request.getRequestDispatcher("/editCategory.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error fetching category details: " + e.getMessage());
        }
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryName = request.getParameter("categoryName");
        int status = Integer.parseInt(request.getParameter("status"));

        Category newCategory = new Category(0, categoryName, status);

        try {
            boolean success = categoryDAO.insertCategory(newCategory);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/categories");
            } else {
                response.getWriter().println("Failed to add category.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error adding category: " + e.getMessage());
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String categoryName = request.getParameter("categoryName");
        int status = Integer.parseInt(request.getParameter("status"));

        Category category = new Category(categoryId, categoryName, status);

        try {
            boolean success = categoryDAO.updateCategory(category);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/categories");
            } else {
                response.getWriter().println("Failed to update category.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error updating category: " + e.getMessage());
        }
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        try {
            boolean success = categoryDAO.deleteCategory(categoryId);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/categories");
            } else {
                response.getWriter().println("Failed to delete category.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error deleting category: " + e.getMessage());
        }
    }
}
