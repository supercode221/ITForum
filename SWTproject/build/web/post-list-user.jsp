<%-- 
    Document   : post-list-user
    Created on : Jul 9, 2024, 2:42:30 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Post List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <style>
            .card {
                transition: all 0.3s ease;
                border: none;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                background-color: #ffffff;
            }

            .card-title {
                font-size: 1.5rem;
                font-weight: bold;
                color: #333;
                font-family: 'Roboto', sans-serif;
            }

            .card-text {
                font-size: 1rem;
                line-height: 1.5;
                color: #666;
            }

            .card-footer {
                background-color: #f8f9fa;
                border: none;
            }


            .text-muted {
                font-size: 0.875rem;
                color: #999;
                font-family: 'Roboto', sans-serif;
            }

            .btn-primary {
                font-size: 0.875rem;
                background-color: #2196F3;
                border-color: #2196F3;
                font-family: 'Roboto', sans-serif;
            }

            .btn-primary:hover {
                background-color: #0d47a1;
                border-color: #0d47a1;
            }

            .img-no-post {
                margin: 0 auto;
                display: block;
                max-width: 100%;
                height: auto;
                border-radius: 5px;
            }
        </style>
    </head>
    <body>
        <%@include file="./header.jsp" %>
        <div class="container">
            <h1 class="my-4">Post List</h1>
            <form action="PostListServlet" method="post" class="mb-4">
                <div class="form-row">
                    <div class="col-md-6 mb-3">
                        <input value="${searchKeyword}" type="text" class="form-control" id="searchKeyword" name="searchKeyword" placeholder="Enter keyword">
                    </div>
                    <div class="col-md-4 mb-3">
                        <select class="form-control" id="categoryFilter" name="categoryId">
                            <option value="0">All Categories</option>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.categoryId}" ${currentCategory == category.categoryId ? "selected" : ""}>${category.categoryName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2 mb-3">
                        <button type="submit" class="btn btn-primary btn-block">Search</button>
                    </div>
                </div>
            </form>

            <div class="row">
                <c:forEach var="post" items="${posts}">
                    <div class="col-lg-4 col-md-6 mb-4">
                        <div class="card h-100 shadow-sm">
                            <div class="card-body">
                                <h5 class="card-title">${post.title}</h5>
                                <p class="card-text">${post.content}</p>
                            </div>
                            <div class="card-footer">
                                <small class="text-muted">Created Date: ${post.createdDate}</small> <br>
                                <small class="text-muted">Post by: ${post.user.username}</small>
                                <a href="PostDetailServlet?postId=${post.postId}" class="btn btn-primary btn-sm float-right">View Details</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${posts.size() == 0}">
                    <img src="https://letankim.id.vn/uploads/no-post.png" class="img-no-post" alt="No posts"/>
                </c:if>
            </div>
        </div>
    </body>
</html>
