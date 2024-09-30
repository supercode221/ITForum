<%-- 
    Document   : add-category
    Created on : Jul 9, 2024, 2:30:33 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Add New Category</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Add New Category</h1>
    <form action="CategoryServlet?action=insert" method="post">
        <div class="form-group">
            <label for="categoryName">Category Name</label>
            <input type="text" class="form-control" id="categoryName" name="categoryName" required>
        </div>
        <div class="form-group">
            <label for="status">Status</label>
            <input type="number" class="form-control" id="status" name="status" required>
        </div>
        <button type="submit" class="btn btn-primary">Add Category</button>
    </form>
</div>
</body>
</html>
