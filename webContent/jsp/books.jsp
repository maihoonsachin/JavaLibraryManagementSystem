<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Books</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Books</h3>
        <div>
            <span class="me-3">User: <strong><c:out value="${sessionScope.username}"/></strong> (<c:out value="${sessionScope.role}"/>)</span>
            <a href="${pageContext.request.contextPath}/login?logout=true" class="btn btn-outline-secondary btn-sm">Logout</a>
        </div>
    </div>

    <form class="row g-2 mb-3" method="get" action="${pageContext.request.contextPath}/books">
        <div class="col-md-3">
            <input class="form-control" type="text" name="q" placeholder="Search by title/author/category" value="${param.q}">
        </div>
        <div class="col-md-2">
            <button class="btn btn-primary" type="submit">Search</button>
            <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/books">Reset</a>
        </div>
    </form>

    <div class="card mb-4">
        <div class="card-header">Add / Edit Book</div>
        <div class="card-body">
            <form method="post" action="${pageContext.request.contextPath}/books">
                <input type="hidden" name="action" value="save">
                <div class="row g-3">
                    <div class="col-md-2"><input class="form-control" name="id" placeholder="ID" required></div>
                    <div class="col-md-3"><input class="form-control" name="title" placeholder="Title" required></div>
                    <div class="col-md-3"><input class="form-control" name="author" placeholder="Author" required></div>
                    <div class="col-md-2"><input class="form-control" name="category" placeholder="Category"></div>
                    <div class="col-md-2"><input class="form-control" name="isbn" placeholder="ISBN"></div>
                    <div class="col-md-2"><input class="form-control" name="publishedYear" placeholder="Year" type="number"></div>
                    <div class="col-md-2"><input class="form-control" name="totalCopies" placeholder="Total" type="number" required></div>
                    <div class="col-md-2"><input class="form-control" name="availableCopies" placeholder="Available" type="number" required></div>
                    <div class="col-md-3">
                        <button class="btn btn-success" type="submit">Save</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th><th>Title</th><th>Author</th><th>Category</th><th>ISBN</th><th>Year</th><th>Total</th><th>Avail</th><th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="b" items="${requestScope.books}">
            <tr>
                <td><c:out value="${b.id}"/></td>
                <td><c:out value="${b.title}"/></td>
                <td><c:out value="${b.author}"/></td>
                <td><c:out value="${b.category}"/></td>
                <td><c:out value="${b.isbn}"/></td>
                <td><c:out value="${b.publishedYear}"/></td>
                <td><c:out value="${b.totalCopies}"/></td>
                <td><c:out value="${b.availableCopies}"/></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/books" class="d-inline">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${b.id}">
                        <button class="btn btn-sm btn-danger" type="submit" onclick="return confirm('Delete book?')">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="mt-3">
        <a href="${pageContext.request.contextPath}/issue" class="btn btn-outline-primary">Issue/Return</a>
        <a href="${pageContext.request.contextPath}/reports" class="btn btn-outline-secondary">Reports</a>
    </div>
</div>
</body>
</html>


