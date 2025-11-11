<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Reports</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Reports</h3>
        <div>
            <a href="${pageContext.request.contextPath}/books" class="btn btn-outline-secondary btn-sm">Back to Books</a>
            <a href="${pageContext.request.contextPath}/login?logout=true" class="btn btn-outline-secondary btn-sm">Logout</a>
        </div>
    </div>

    <h5>Overdue Books</h5>
    <table class="table table-striped">
        <thead><tr><th>Record</th><th>Book</th><th>User</th><th>Due</th><th>Days Overdue</th><th>Fine</th></tr></thead>
        <tbody>
        <c:forEach var="o" items="${requestScope.overdues}">
            <tr>
                <td><c:out value="${o.id}"/></td>
                <td><c:out value="${o.bookId}"/></td>
                <td><c:out value="${o.userId}"/></td>
                <td><c:out value="${o.dueDate}"/></td>
                <td><c:out value="${o.daysOverdue}"/></td>
                <td><c:out value="${o.fine}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>


