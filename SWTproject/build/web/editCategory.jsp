<%-- 
    Document   : editCategory
    Created on : Jul 10, 2024, 3:33:08 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Category</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <%@include file="./headerAdmin.jsp" %>
        <div class="container mt-4">
            <h1>Edit Category</h1>
            <form action="categories" method="post">
                <input type="hidden" name="categoryId" value="${category.categoryId}">
                <input type="hidden" name="action" value="update" />
                <div class="form-group">
                    <label for="categoryName">Category Name:</label>
                    <input type="text" class="form-control" id="categoryName" name="categoryName" value="${category.categoryName}">
                </div>
                <div class="form-group">
                    <label for="status">Status:</label>
                    <select class="form-control" id="status" name="status">
                        <option value="1" ${category.status == 1 ? "selected" : ""}>Active</option>
                        <option value="0" ${category.status == 0 ? "selected" : ""}>Inactive</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Update Category</button>
            </form>
        </div>
    </body>
</html>
