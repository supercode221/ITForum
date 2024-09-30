/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.UserDAO;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author HP
 */
@WebServlet(name = "ManagerUserController", urlPatterns = {"/manager-user"})
public class ManagerUserController extends HttpServlet {

    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            action = action != null ? action : "";
            switch (action) {
                case "view":
                    this.viewUser(request, response);
                    break;
                case "edit":
                    this.showEditForm(request, response);
                    break;
                case "delete":
                    this.deleteUser(request, response);
                    break;
                case "new":
                    this.showNewForm(request, response);
                    break;
                default:
                    this.listUsers(request, response);
                    break;
            }
        } catch (Exception e) {

        }
    }

    private void viewUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            User existingUser = userDAO.getUserById(userId);
            if (existingUser != null) {
                request.setAttribute("user", existingUser);
                request.getRequestDispatcher("./viewUser.jsp").forward(request, response);
            } else {
                response.sendRedirect("manager-user?error=User not found");
            }
        } catch (Exception e) {
            response.sendRedirect("manager-user?error=Hava a error");
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<User> userList = userDAO.getAllUsers();
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("./managerUser.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("./userForm.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            User existingUser = userDAO.getUserById(userId);
            if (existingUser != null) {
                request.setAttribute("user", existingUser);
                request.getRequestDispatcher("./userForm.jsp").forward(request, response);
            } else {
                response.sendRedirect("manager-user?error=Can not found this user");
            }
        } catch (Exception e) {
            response.sendRedirect("manager-user?error=Hava a error");
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        userDAO.deleteUser(userId);
        response.sendRedirect("manager-user?success=User deleted successfully");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            action = action != null ? action : "";
            switch (action) {
                case "insert":
                    this.insertUser(request, response);
                    break;
                case "update":
                    this.updateUser(request, response);
                    break;
                default:
                    throw new AssertionError();
            }
        } catch (Exception e) {

        }

    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException {
        try {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String fullName = request.getParameter("fullName");
            Date createdDate = new Date();
            String role = request.getParameter("role");
            int status = Integer.parseInt(request.getParameter("status"));

            if (isValidInput(username, email, password, fullName)) {
                User newUser = new User(0, username, password, email, fullName, createdDate, role);
                newUser.setStatus(status);
                userDAO.insertUser(newUser);
                response.sendRedirect("manager-user?success=User added successfully");
            } else {
                request.setAttribute("errorMessage", "Invalid input data. Please ensure all fields are correctly filled.");
                request.getRequestDispatcher("./userForm.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.out.println("Update error: " + e);
        }

    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String fullName = request.getParameter("fullName");
            Date createdDate = new Date();
            String role = request.getParameter("role");
            int status = Integer.parseInt(request.getParameter("status"));

            if (password.trim().isEmpty()) {
                password = request.getParameter("oldPassword");
            }

            if (isValidInput(username, email, password, fullName)) {
                User user = new User(userId, username, password, email, fullName, createdDate, role);
                user.setStatus(status);
                userDAO.updateUser(user);
                response.sendRedirect("manager-user?success=User updated successfully");
            } else {
                request.setAttribute("errorMessage", "Invalid input data. Please ensure all fields are correctly filled.");
                User existingUser = new User(userId, username, password, email, fullName, createdDate, role);
                existingUser.setStatus(status);
                request.setAttribute("user", existingUser);
                request.getRequestDispatcher("./userForm.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private boolean isValidInput(String username, String email, String password, String fullName) {
        String userNamePattern = "^[a-zA-Z0-9]{5,}$";
        String emailPattern = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        String passwordPattern = "^.{8,}$";
        String fullNamePattern = "^[a-zA-Z\\s]+$";

        return username.matches(userNamePattern)
                && email.matches(emailPattern)
                && password.matches(passwordPattern)
                && fullName.matches(fullNamePattern);
    }
}
