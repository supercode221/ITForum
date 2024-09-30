<%-- 
    Document   : post-list
    Created on : Jul 9, 2024, 2:11:29 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Post List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    </head>
    <body>
        <%@include file="./headerAdmin.jsp" %>
        <div class="container">
            <h2>Post List</h2>
            <a href="posts?action=add" class="btn btn-success">Add New Post</a>
            <table class="table" id="data-table">
                <thead class="thead-dark">
                    <tr>
                        <th>Post ID</th>
                        <th>User</th>
                        <th>Category</th>
                        <th>Title</th>
                        <th>Content</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="post" items="${posts}" varStatus="index">
                        <tr>
                             <td>${index.index + 1}</td>
                            <td>${post.user.username}</td>
                            <td>${post.category.categoryName}</td>
                            <td>${post.title}</td>
                            <td>${post.content}</td>
                            <td>
                                <c:if test="${post.status == 0}">
                                    <span class="badge badge-secondary">In active</span>
                                </c:if>
                                <c:if test="${post.status == 1}">
                                    <span class="badge badge-success">Active</span>
                                </c:if>
                                <c:if test="${post.status == 2}">
                                    <span class="badge badge-secondary">Ban</span>
                                </c:if>
                            </td>
                            <td>
                                <a href="posts?action=edit&postId=${post.postId}" class="btn btn-primary">Edit</a>
                                <form action="posts" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="postId" value="${post.postId}">
                                    <button type="submit" class="btn btn-danger">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
