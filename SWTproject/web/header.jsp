<%-- 
    Document   : header
    Created on : Jul 9, 2024, 3:34:17 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="PostListServlet">IT Forum</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="PostListServlet">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="PostServlet">Posts</a>
            </li>
            <c:if test="${sessionScope.userLogin != null}">
                <li class="nav-item">
                    <a class="nav-link" href="profileController">${sessionScope.userLogin.username}</a>
                </li>
                <li class="nav-item">
                    <a onclick="return confirm('Are you sure to logout?')" class="nav-link" href="logout?action=user">Logout</a>
                </li>
            </c:if>
            <c:if test="${sessionScope.userLogin == null}">
                <li class="nav-item">
                    <a class="nav-link" href="login">Login</a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>