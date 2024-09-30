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

/**
 *
 * @author HP
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            this.insertUser(request, response);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException {
        UserDAO userDao = new UserDAO();
        try {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String fullName = request.getParameter("fullName");
            Date createdDate = new Date();
            String role = "user";
            int status = 1;
            if (isValidInput(username, email, password, fullName)) {
                User userExist = userDao.isExistUser(username);
                if (userExist == null) {
                    User newUser = new User(0, username, password, email, fullName, createdDate, role);
                    newUser.setStatus(status);
                    userDao.insertUser(newUser);
                    response.sendRedirect("login?success=Register successfully");
                } else {
                    request.setAttribute("errorMessage", "Username is exist.");
                    request.getRequestDispatcher("./register.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Invalid input data. Please ensure all fields are correctly filled.");
                request.getRequestDispatcher("./register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.out.println("Update error: " + e);
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
