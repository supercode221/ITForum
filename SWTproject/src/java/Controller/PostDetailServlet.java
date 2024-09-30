/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CommentDAO;
import DAO.LikeDAO;
import DAO.PostDAO;
import DAO.UserDAO;
import Model.Comment;
import Model.Like;
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
import java.util.Date;
import java.util.List;

/**
 *
 * @author HP
 */
@WebServlet("/PostDetailServlet")
public class PostDetailServlet extends HttpServlet {

    private PostDAO postDAO;
    private CommentDAO commentDAO;
    private User userLogin;

    public void init() {
        postDAO = new PostDAO();
        commentDAO = new CommentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        userLogin = (User) session.getAttribute("userLogin");
        int postId = Integer.parseInt(request.getParameter("postId"));
        UserDAO userDao = new UserDAO();
        LikeDAO likeDao = new LikeDAO();
        try {
            Post post = postDAO.getPostById(postId);
            List<Comment> comments = commentDAO.getCommentsByPostId(postId);
            Like like = null;
            if(userLogin != null) {
                like = likeDao.getLikeByPostAndUser(postId, userLogin.getUserId());
            }
            request.setAttribute("like", like);
            request.setAttribute("userLogin", userLogin);
            request.setAttribute("post", post);
            request.setAttribute("comments", comments);
            request.getRequestDispatcher("post-detail-user.jsp").forward(request, response);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postId = Integer.parseInt(request.getParameter("postId"));
        HttpSession session = request.getSession();
        userLogin = (User) session.getAttribute("userLogin");
        if (userLogin == null) {
            response.sendRedirect("login");
            return;
        }
        String action = request.getParameter("action");
        if (action != null) {
            try {
                switch (action) {
                    case "comment":
                        String content = request.getParameter("commentContent");
                        Comment comment = new Comment(0, postId, userLogin.getUserId(), content, new Date());
                        commentDAO.insertComment(comment);
                        break;
                    default:
                        break;
                }
                response.sendRedirect("PostDetailServlet?postId=" + postId);
            } catch (Exception ex) {
                throw new ServletException(ex);
            }
        }
    }
}
