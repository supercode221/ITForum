<%-- 
    Document   : add-post
    Created on : Jul 9, 2024, 2:22:19 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Add New Post</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    </head>
    <body>
        <%@include file="./header.jsp" %>
        <div class="container" style="margin-top: 200px;">
            <h1>Add New Post</h1>
            <form action="PostServlet?action=insert" method="post">
                <div class="form-group">
                    <label for="userId">User ID</label>
                    <input type="number" class="form-control" id="userId" name="userId" required>
                </div>
                <div class="form-group">
                    <label for="categoryId">Category</label>
                    <select class="form-control" id="categoryId" name="categoryId" required>
                        <option value="">Select Category</option>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.categoryId}">${category.categoryName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="title">Title</label>
                    <input type="text" class="form-control" id="title" name="title" required>
                </div>
                <div class="form-group">
                    <label for="content">Content</label>
                    <textarea class="form-control" id="content" name="content" rows="5" required></textarea>
                </div>
                <div class="form-group">
                    <label for="status">Status</label>
                    <input type="number" class="form-control" id="status" name="status" required>
                </div>
                <button type="submit" class="btn btn-primary">Add Post</button>
            </form>
        </div>
        <%--<%@include file="./footer.jsp.jsp" %>--%>
    </body>
</html>