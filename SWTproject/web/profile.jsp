<%-- 
    Document   : profile
    Created on : Jul 9, 2024, 5:08:37 PM
    Author     : HP
--%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Profile</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="styles.css">
    </head>
    <style>
        .profile{
            margin-bottom: 456px;
        }
    </style>
    <body>
        <%@include file="./header.jsp" %>
        <div class="profile">
            <div class="container mt-5">
                <c:if test="${error != null}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </c:if>
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
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="card">
                            <div class="card-header">
                                User Profile
                            </div>
                            <div class="card-body">
                                <p><strong>Username:</strong> <span id="username">${userLogin.username}</span></p>
                                <p><strong>Email:</strong> <span id="email">${userLogin.email}</span></p>
                                <p><strong>Full Name:</strong> <span id="fullname">${userLogin.fullName}</span></p>
                                <p><strong>Role:</strong> <span id="role">${userLogin.role}</span></p>
                                <p><strong>Created Date:</strong> <span id="created-date">${userLogin.createdDate}</span></p>
                                <button class="btn btn-primary" id="changeInfoBtn">Change Information</button>
                                <button class="btn btn-warning ml-2" id="changePasswordBtn">Change Password</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="changeInfoModal" tabindex="-1" role="dialog" aria-labelledby="changeInfoModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="changeInfoModalLabel">Change Information</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="changeInfoForm" action="profileController" method="post">
                            <input type="hidden" name="action" value="information" />
                            <div class="form-group">
                                <label for="username">Username</label>
                                <input type="text" class="form-control" id="username" name="username" readonly value="${userLogin.username}">
                            </div>
                            <div class="form-group">
                                <label for="email">Email:</label>
                                <input type="email" class="form-control" id="email" name="email" value="${userLogin.email}">
                            </div>
                            <div class="form-group">
                                <label for="fullname">FullName:</label>
                                <input type="text" class="form-control" id="fullname" name="fullname" value="${userLogin.fullName}">
                            </div>
                            <button type="submit" class="btn btn-primary">Save Changes</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="changePasswordModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="changePasswordModalLabel">Change Password</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="changePasswordForm" action="profileController" method="post">
                            <input type="hidden" name="action" value="password" />
                            <div class="form-group">
                                <label for="currentPassword">Current Password:</label>
                                <input type="password" class="form-control" id="currentPassword" name="currentPassword">
                            </div>
                            <div class="form-group">
                                <label for="newPassword">New Password:</label>
                                <input type="password" class="form-control" id="newPassword" name="newPassword">
                            </div>
                            <div class="form-group">
                                <label for="confirmPassword">Confirm New Password:</label>
                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword">
                            </div>
                            <button type="submit" class="btn btn-primary">Change Password</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="./footer.jsp" %>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
            document.getElementById('changeInfoBtn').addEventListener('click', function () {
                $('#changeInfoModal').modal('show');
            });

            document.getElementById('changePasswordBtn').addEventListener('click', function () {
                $('#changePasswordModal').modal('show');
            });
        </script>
    </body>
</html>
