<%-- 
    Document   : userForm
    Created on : Jul 5, 2024, 1:13:14 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>${user != null ? "Edit User" : "Add User"}</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <%@include file="./headerAdmin.jsp" %>
        <div class="container mt-5">
            <h1 class="mb-4">${user != null ? "Edit User" : "Add User"}</h1>
            <c:if test="${errorMessage != null}">
                <div class="alert alert-danger" role="alert">
                    ${errorMessage}
                </div>
            </c:if>
            <form action="manager-user" method="post">
                <input type="hidden" name="action" value="${user != null ? 'update' : 'insert'}">
                <input type="hidden" name="userId" value="${user != null ? user.userId : ''}">
                <div class="form-group">
                    <label for="userName">User Name:</label>
                    <input ${user != null ? "readonly" : ''} type="text" class="form-control" id="username" name="username" value="${user != null ? user.username : ''}" required>
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" class="form-control" id="email" name="email" value="${user != null ? user.email : ''}" required>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <c:if test="${user != null}">
                        <input type="hidden" class="form-control" id="oldPassword" name="oldPassword" value="${user.password}" required>
                    </c:if>
                    <input type="password" class="form-control" id="password" name="password" value="" ${user != null ? "" : "required"}>
                </div>
                <div class="form-group">
                    <label for="phone">Fullname:</label>
                    <input type="text" class="form-control" id="fullName" name="fullName" value="${user != null ? user.fullName : ''}" required>
                </div>
                <div class="form-group">
                    <label for="role">Role:</label>
                    <c:if test="${param.type == null}">
                        <select class="form-control" id="role" name="role" required>
                            <option value="user" ${user != null && user.role == 'user' ? 'selected' : ''}>User</option>
                            <option value="admin" ${user != null && user.role == 'admin' ? 'selected' : ''}>Admin</option>
                        </select>
                    </c:if>
                    <c:if test="${param.type != null}">
                        <input type="text" class="form-control" id="role" name="role" value="${user != null ? user.role : ''}" readonly="">
                    </c:if>
                </div>
                <div class="form-group">
                    <c:if test="${param.type == null}">
                        <label for="status">Status:</label>
                        <select class="form-control" id="status" name="status" required>
                            <option value="1" ${user != null && user.status == 1 ? 'selected' : ''}>Active</option>
                            <option value="0" ${user != null && user.status == 0 ? 'selected' : ''}>Inactive</option>
                        </select>
                    </c:if>
                    <c:if test="${param.type != null}">
                        <input type="hidden" class="form-control" id="status" name="status" value="${user.status}">
                    </c:if>
                </div>
                <button type="submit" class="btn btn-primary">${user != null ? "Update" : "Add"} User</button>
            </form>
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
