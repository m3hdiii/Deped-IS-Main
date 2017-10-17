<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/public" var="resourceURL" scope="request"/>
<html>
<head>
    <title>Title</title>

    <script type="text/javascript" src="${resourceURL}/js/jquery/jquery-1.9.1.min.js" ></script>
    <script type="text/javascript" src="${resourceURL}/js/additional/fetch-json.js" ></script>
</head>
<body>


<form >
    <button onclick="fetchJson()" ></button>
</form>
</body>
</html>
