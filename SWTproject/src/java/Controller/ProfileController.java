/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CategoryDAO;
import DAO.PostDAO;
import DAO.UserDAO;
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

/**
 *
 * @author HP
 */
@WebServlet(name = "ProfileController", urlPatterns = {"/profileController"})
public class ProfileController extends HttpServlet {

    private User userLogin;

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
            out.println("<title>Servlet ProfileController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProfileController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        userLogin = (User) session.getAttribute("userLogin");
        if (userLogin == null) {
            response.sendRedirect("login");
            return;
        }
        String action = request.getParameter("action");
        action = action != null ? action : "";
        try {
            switch (action) {
                case "insert":

                default:
                    request.setAttribute("userLogin", userLogin);
                    request.getRequestDispatcher("profile.jsp").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            System.out.println("Profile: " + ex);
        }
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
        HttpSession session = request.getSession();
        userLogin = (User) session.getAttribute("userLogin");
        if (userLogin == null) {
            response.sendRedirect("login");
            return;
        }
        String action = request.getParameter("action");
        action = action != null ? action : "";
        try {
            switch (action) {
                case "information":
                    this.updateInformation(request, response);
                    break;
                case "password":
                    this.updatePassword(request, response);
                    break;
                default:
                    response.sendRedirect("profileController");
                    break;
            }
        } catch (Exception ex) {
            System.out.println("Profile: " + ex);
        }
    }

    private void updateInformation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserDAO userDao = new UserDAO();
            String email = request.getParameter("email");
            String fullname = request.getParameter("fullname");
            userLogin.setFullName(fullname);
            userLogin.setEmail(email);
            boolean isEdit = userDao.updateInformationUser(userLogin);
            if (isEdit) {
                response.sendRedirect("profileController?success=Update information successfully");
            } else {
                response.sendRedirect("profileController?error=Update information fail");
            }
        } catch (Exception e) {
            response.sendRedirect("profileController?error=Have a error while error");
        }
    }

    private void updatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserDAO userDao = new UserDAO();
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            if (userLogin.getPassword().equals(currentPassword)) {
                response.sendRedirect("profileController?error=Old password is not correct");
                return;
            }

            if (newPassword.equals(confirmPassword)) {
                response.sendRedirect("profileController?error=Confirm password is not correct");
                return;
            }
            userLogin.setPassword(newPassword);
            boolean isEdit = userDao.updatePasswordUser(userLogin);
            if (isEdit) {
                response.sendRedirect("profileController?success=Update passsword successfully");
            } else {
                response.sendRedirect("profileController?error=Update password fail");
            }
        } catch (Exception e) {
            response.sendRedirect("profileController?error=Have a error while error");
        }
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
