/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CommentDAO;
import Model.Comment;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author HP
 */
@WebServlet(name = "EditCommentController", urlPatterns = {"/editComment"})
public class EditCommentController extends HttpServlet {

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
            out.println("<title>Servlet EditCommentController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditCommentController at " + request.getContextPath() + "</h1>");
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
        String postId = request.getParameter("postId");
        try {
            HttpSession session = request.getSession();
            User userLogin = (User) session.getAttribute("userLogin");
            if (userLogin != null) {
                CommentDAO commentDao = new CommentDAO();
                String commentId = request.getParameter("commentId");
                commentDao.deleteComment(Integer.parseInt(commentId));
                response.sendRedirect("PostDetailServlet?postId=" + postId + "&success=Delete comment successfully");
            } else {
                response.sendRedirect("login?error=Please login to do action");
            }
        } catch (Exception e) {
            response.sendRedirect("PostDetailServlet?postId=" + postId);
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
        String postId = request.getParameter("postId");
        try {
            HttpSession session = request.getSession();
            User userLogin = (User) session.getAttribute("userLogin");
            if (userLogin != null) {
                String commentId = request.getParameter("commentId");
                String content = request.getParameter("content");
                CommentDAO commentDao = new CommentDAO();
                Comment c = commentDao.getCommentById(Integer.parseInt(commentId));
                c.setContent(content);
                commentDao.updateComment(c);
                response.sendRedirect("PostDetailServlet?postId=" + postId + "&success=Update comment successfully");
            } else {
                response.sendRedirect("login?error=Please login to do action");
            }
        } catch (Exception e) {
            response.sendRedirect("PostDetailServlet?postId=" + postId);
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
