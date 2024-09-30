<%-- 
    Document   : post-detail-user
    Created on : Jul 9, 2024, 2:44:57 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Post Detail</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <!-- Navbar -->
        <%@include file="header.jsp" %>

        <!-- Main Content -->
        <div class="container mt-5">
            <h1>Post Detail</h1>
            <div class="card mb-4">
                <div class="card-body position-relative">
                    <h5 class="card-title">${post.title}</h5>
                    <p class="card-text">${post.content}</p>
                    <p class="card-text"><small class="text-muted">Created on: ${post.createdDate}</small></p>
                    <p class="card-text">Likes: <span id="likeCount-${post.postId}" style="cursor: pointer;" onclick="openLikeListModal()">${post.like.size()}</span></p>
                        <c:if test="${userLogin != null && post.userId == userLogin.userId}">
                        <a href="PostServlet?action=edit&postId=${post.postId}" class="btn btn-danger btn-sm position-absolute" style="top: 10px; right: 10px">Edit post</a>
                    </c:if>
                    <button class="btn btn-primary" id="likeButton-${post.postId}" onclick="toggleLike('${post.postId}')">
                        <c:if test="${like != null}">
                            Unlike
                        </c:if>
                        <c:if test="${like == null}">
                            Like
                        </c:if>
                    </button>
                </div>
            </div>
                    <div class="modal fade" id="likeListModal-${post.postId}" tabindex="-1" role="dialog" aria-labelledby="likeListModalLabel-${post.postId}" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="likeListModalLabel-${post.postId}">Liked by</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <ul class="list-group list-group-flush">
                                <c:forEach var="likedUser" items="${post.like}">
                                    <li class="list-group-item">${likedUser.user.username}</li>
                                    </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <h3>Comments</h3>
            <hr>
            <c:if test="${param.error != null}">
                <div class="alert alert-danger" role="alert">
                    ${param.error}
                </div>
            </c:if>
            <c:if test="${param.success != null}">
                <div class="alert alert-success" role="alert">
                    ${param.success}
                </div>
            </c:if>
            <c:forEach var="comment" items="${comments}">
                <div class="card mb-3">
                    <div class="card-body">
                        <p class="card-text"><strong>${comment.user.username}</strong></p>
                        <p class="card-text">${comment.content}</p>
                        <p class="card-text"><small class="text-muted">Created on: ${comment.createdDate}</small></p>
                        <c:if test="${comment.user.userId == sessionScope.userLogin.userId}">
                            <a href="#" class="btn btn-primary btn-sm edit-comment" 
                               data-toggle="modal" data-target="#editCommentModal"
                               data-comment-id="${comment.commentId}"
                               data-comment-content="${comment.content}">
                                Edit
                            </a>
                            <a onclick="return confirm('Are you sure to delete?')" href="editComment?commentId=${comment.commentId}&postId=${post.postId}" class="btn btn-danger btn-sm ml-2">
                                Delete
                            </a>
                        </c:if>
                    </div>
                </div>
            </c:forEach>

            <hr>
            <div class="card mb-5">
                <div class="card-body">
                    <h3>Add a Comment</h3>
                    <c:if test="${sessionScope.userLogin != null}">
                        <form action="PostDetailServlet" method="post">
                            <input type="hidden" name="postId" value="${post.postId}">
                            <div class="form-group">
                                <label for="commentContent">Comment</label>
                                <textarea class="form-control" id="commentContent" name="commentContent" rows="3" required></textarea>
                            </div>
                            <button type="submit" name="action" value="comment" class="btn btn-primary">Add Comment</button>
                        </form>
                    </c:if>
                    <c:if test="${sessionScope.userLogin == null}">
                        <div class="alert alert-danger" role="alert">
                            <a href="login">You must login before comment</a>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="editCommentModal" tabindex="-1" role="dialog" aria-labelledby="editCommentModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editCommentModalLabel">Edit Comment</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form id="editCommentForm" action="editComment" method="post">
                        <div class="modal-body">
                            <input type="hidden" id="commentId" name="commentId" value="">
                            <input type="hidden" id="postId" name="postId" value="${post.postId}">
                            <div class="form-group">
                                <label for="editedContent">Content:</label>
                                <textarea class="form-control" id="editedContent" name="content" rows="3"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Save changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Footer -->
        <%@include file="footer.jsp" %>
        <script>
                                   function toggleLike(postId) {
                                       fetch('/ForumTechnology/like?postId=' + postId, {
                                           method: 'POST',
                                           headers: {
                                               'Content-Type': 'application/json'
                                           }
                                       }).then(response => response.json())
                                               .then(data => {
                                                   if (data.login) {
                                                       window.location.href = "/ForumTechnology/login?error=You must login before do action"
                                                   }
                                                   document.getElementById('likeCount-' + postId).innerText = data.likes;
                                                   document.getElementById('likeButton-' + postId).innerText = data.liked ? 'Unlike' : 'Like';
                                               });
                                   }
                                   function openLikeListModal() {
                                       $("#likeListModal-${post.postId}").modal('show');
                                   }
                                   $(document).ready(function () {
                                       $('.edit-comment').click(function () {
                                           var commentId = $(this).data('comment-id');
                                           var commentContent = $(this).data('comment-content');
                                           $('#commentId').val(commentId);
                                           $('#editedContent').val(commentContent);
                                       });
                                   });

        </script>
    </body>
</html>
