<%-- 
    Document   : listCategories
    Created on : Jul 10, 2024, 3:31:18 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>List Categories</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <%@include file="./headerAdmin.jsp" %>
        <div class="container mt-4">
            <h1>List of Categories</h1>
            <a href="categories?action=showAddForm" class="btn btn-success">Add New Category</a>
            <table class="table table-striped" id="data-table">
                <thead class="thead-dark">
                    <tr>
                        <th>No.</th>
                        <th>Category Name</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${categories}" var="category" varStatus="index">
                        <tr>
                            <td>${index.index + 1}</td>
                            <td>${category.categoryName}</td>
                            <td><span class="badge
                                      ${category.status == 1 ? 'badge-success' : 'badge-danger'}">
                                    ${category.status == 1 ? 'Active' : 'Inactive'}
                                </span></td>
                            <td>
                                <a href="categories?action=edit&categoryId=${category.categoryId}" class="btn btn-primary btn-sm">Edit</a>
                                <a onclick="return confirm('Are you sure to delete?')" href="categories?action=delete&categoryId=${category.categoryId}" class="btn btn-danger btn-sm">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>

