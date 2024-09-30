<%-- 
    Document   : post-form
    Created on : Jul 9, 2024, 2:11:49 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Post Form</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    </head>
    <body>
        <%@include file="./headerAdmin.jsp" %>
        <div class="container">
            <h2>Post Form</h2>
            <form action="posts" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="postId" value="${post.postId}">
                <input type="hidden" class="form-control" id="userId" name="userId" value="${post.userId}" required>
                <div class="form-group">
                    <label for="categoryId">Category ID</label>
                    <select class="form-control" id="categoryId" name="categoryId" required>
                        <option value="">Select Category</option>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.categoryId}" ${category.categoryId == post.categoryId ? "selected" : ""}>${category.categoryName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="title">Title</label>
                    <input type="text" class="form-control" id="title" name="title" value="${post.title}" required>
                </div>
                <div class="form-group">
                    <label for="content">Content</label>
                    <textarea class="form-control" id="content" name="content" required>${post.content}</textarea>
                </div>
                <div class="form-group">
                    <label for="status">Status</label>
                    <select class="form-control" id="status" name="status" required>
                        <option value="" disabled selected>Select Status</option>
                        <option value="1" ${post.status == 1 ? "selected" : ""}>Active</option>
                        <option value="0" ${post.status == 0 ? "selected" : ""}>Inactive</option>
                        <option value="2" ${post.status == 2 ? "selected" : ""}>Ban</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Update Post</button>
            </form>
        </div>
    </body>
</html>
