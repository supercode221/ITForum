/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.LikeDAO;
import Model.Like;
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
import org.json.JSONObject;

/**
 *
 * @author HP
 */
@WebServlet("/like")
public class LikeServlet extends HttpServlet {

    private LikeDAO likeDAO;
    private User userLogin;

    @Override
    public void init() {
        likeDAO = new LikeDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonResponse = new JSONObject();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        userLogin = (User) session.getAttribute("userLogin");
        if (userLogin == null) {
            jsonResponse.put("login", true);
            out.print(jsonResponse.toString());
            out.flush();
            return;
        }
        System.out.println(request.getParameter("postId") + "11111");
        int postId = Integer.parseInt(request.getParameter("postId"));
        
        
        int userId = userLogin.getUserId();
        
        try {
            String liked = toggleLike(postId, userId);
            int likes = getLikeCount(postId);
            jsonResponse.put("liked", liked);
            jsonResponse.put("likes", likes);
            out.print(jsonResponse.toString());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String toggleLike(int postId, int userId) throws SQLException {
        Like like = likeDAO.getLikeByPostAndUser(postId, userId);
        if (like != null) {
            boolean isUnlike = likeDAO.deleteLike(like.getLikeId());
            if (isUnlike) {
                return "Like";
            }
        } else {
            Like newLike = new Like(0, postId, userId);
            likeDAO.insertLike(newLike);
            return "UnLike";
        }
        return "";
    }

    private int getLikeCount(int postId) throws SQLException {
        return likeDAO.getLikesCountByPostId(postId);
    }
}
