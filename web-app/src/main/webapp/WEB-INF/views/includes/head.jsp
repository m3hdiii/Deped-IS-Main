<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${param.title}" /></title>
    <meta name="description" content="<c:out value="${param.description}" />">
    <link rel="stylesheet" href="${resourceURL}/css/bootstrap/bootstrap.css" />
    <link rel="stylesheet" href="${resourceURL}/css/plugins/calendar/calendar.css"/>
    <%--<link href='http://fonts.googleapis.com/css?family=Raleway:400,500,600,700,300' rel='stylesheet' type='text/css'>--%>
    <link rel="stylesheet" href="${resourceURL}/css/app/app.v1.css" />
    <link rel="stylesheet" href="${resourceURL}/css/profile/style.css" />
    <%--<link rel="stylesheet" href="${resourceURL}/css/items/style.css" />--%>
    <link rel="stylesheet" href="${resourceURL}/css/main/style.css"/>

    <%--<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>--%>
    <%--<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>--%>
    <style>
        .leftNavHeader {
            color: #ffd73e;
        }
        .list-unstyled>li  a {
            color: #1cff39;
        }
    </style>
</head>