<%--
  Created by IntelliJ IDEA.
  User: mehdi
  Date: 10/22/17
  Time: 10:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:set var="errors" value="${requestScope['org.springframework.validation.BindingResult.request'].allErrors}"/>
<c:if test="${not empty errors}">
    <div>
        <ul class="list-group">
            <c:forEach items="${fieldErrors}" var="error" varStatus="loop">
                <li class="list-group-item list-group-item-warning text-danger"><span
                        class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;${error.defaultMessage}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>

</body>
</html>
