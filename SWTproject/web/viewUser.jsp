<%-- 
    Document   : viewUser
    Created on : Jul 5, 2024, 1:29:31 AM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>View User</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <%@include file="./headerAdmin.jsp" %>
        <div class="container mt-5">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h1 class="card-title">User Details</h1>
                </div>
                <div class="card-body">
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <p class="font-weight-bold">User ID:</p>
                            <p class="text-primary">${user.userId}</p>
                        </div>
                        <div class="col-md-6">
                            <p class="font-weight-bold">User Name:</p>
                            <p class="text-success">${user.username}</p>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <p class="font-weight-bold">Email:</p>
                            <p class="text-info">${user.email}</p>
                        </div>
                        <div class="col-md-6">
                            <p class="font-weight-bold">Fullname</p>
                            <p class="text-warning">${user.fullName}</p>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <p class="font-weight-bold">Create at</p>
                            <p class="text-info">${user.createdDate}</p>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <p class="font-weight-bold">Role:</p>
                            <p class="badge badge-secondary">${user.role}</p>
                        </div>
                        <div class="col-md-6">
                            <p class="font-weight-bold">Status:</p>
                            <p class="${user.status == 1 ? 'badge badge-success' : 'badge badge-danger'}">
                                ${user.status == 1 ? "Active" : "Inactive"}
                            </p>
                        </div>
                    </div>
                    <a href="manager-user" class="btn btn-primary">Back to User Management</a>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
