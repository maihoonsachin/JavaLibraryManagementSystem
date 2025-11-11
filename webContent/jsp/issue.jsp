<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Issue / Return</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Issue / Return</h3>
        <div>
            <a href="${pageContext.request.contextPath}/books" class="btn btn-outline-secondary btn-sm">Back to Books</a>
            <a href="${pageContext.request.contextPath}/login?logout=true" class="btn btn-outline-secondary btn-sm">Logout</a>
        </div>
    </div>

    <div class="row g-4">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">Issue Book</div>
                <div class="card-body">
                    <form method="post" action="${pageContext.request.contextPath}/issue">
                        <input type="hidden" name="action" value="issue">
                        <div class="mb-3"><input class="form-control" name="recordId" placeholder="Record ID" required></div>
                        <div class="mb-3"><input class="form-control" name="bookId" placeholder="Book ID" required></div>
                        <div class="mb-3"><input class="form-control" name="userId" placeholder="User ID" required></div>
                        <button class="btn btn-success" type="submit">Issue</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">Return Book</div>
                <div class="card-body">
                    <form method="post" action="${pageContext.request.contextPath}/issue">
                        <input type="hidden" name="action" value="return">
                        <div class="mb-3"><input class="form-control" name="recordId" placeholder="Record ID" required></div>
                        <button class="btn btn-primary" type="submit">Return</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <h5 class="mt-4">Currently Issued</h5>
    <table class="table table-striped">
        <thead><tr><th>ID</th><th>Book</th><th>User</th><th>Issue</th><th>Due</th><th>Return</th><th>Fine</th></tr></thead>
        <tbody>
        <c:forEach var="r" items="${requestScope.issued}">
            <tr>
                <td><c:out value="${r.id}"/></td>
                <td><c:out value="${r.bookId}"/></td>
                <td><c:out value="${r.userId}"/></td>
                <td><c:out value="${r.issueDate}"/></td>
                <td><c:out value="${r.dueDate}"/></td>
                <td><c:out value="${r.returnDate}"/></td>
                <td><c:out value="${r.fineAmount}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>


