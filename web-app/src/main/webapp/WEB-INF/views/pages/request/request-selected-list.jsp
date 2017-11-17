<%--
  Created by IntelliJ IDEA.
  User: mehdi
  Date: 10/22/17
  Time: 10:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%
    //System.out.println(getServletContext().getRealPath("/"));
%>
<c:url value="/public" var="resourceURL" scope="request"/>
<c:url value="/" var="routePath" scope="request"/>

<html>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Item Registration"/>
    <c:param name="description" value="Item Registration Page"/>
</c:import>

<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Manage Request &nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small></h3>
        </div>


        <div class="panel panel-default">
            <div class="panel-heading clean text-center">Information of Request</div>
            <div class="panel-body">

                <table cellpadding="0" cellspacing="0" border="0" class="table table-hover" id="basic-datatable">
                    <thead>
                    <tr>
                        <th>User</th>
                        <th>Reason</th>
                        <th>Date Requested</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requests}" var="request">
                        <tr>
                            <th>${request.user.firstName} ${request.user.middleName} ${request.user.lastName}</th>
                            <td>${request.userMessage}</td>
                            <td>${request.requestDate}</td>
                            <td>${request.requestStatus}</td>
                            <td>
                                <a href="${requestUrl}${request.requestId}">${anchorName}</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
    <!-- Warper Ends Here (working area) -->

    <%--<div class="warper container-fluid">--%>

        <%--<div class="page-header">--%>
            <%--<h1>Manage Request--%>
                <%--<small>DepEd-Baguio City Division Office</small>--%>
            <%--</h1>--%>

        <%--</div>--%>

        <%--<div class="row item-body">--%>
            <%--<div class="panel panel-default">--%>
                <%--<h3 class="text-center">Request Info</h3>--%>
                <%--<div class="panel-body">--%>

                    <%--<table class="table table-hover">--%>
                        <%--<thead>--%>
                        <%--<tr>--%>
                            <%--<th>User</th>--%>
                            <%--<th>Reason</th>--%>
                            <%--<th>Date Requested</th>--%>
                            <%--<th>Status</th>--%>
                            <%--<th>Action</th>--%>
                        <%--</tr>--%>
                        <%--</thead>--%>
                        <%--<tbody>--%>
                            <%--<c:forEach items="${requests}" var="request">--%>
                                <%--<tr>--%>
                                    <%--<th>${request.user.firstName} ${request.user.middleName} ${request.user.lastName}</th>--%>
                                    <%--<td>${request.userMessage}</td>--%>
                                    <%--<td>${request.requestDate}</td>--%>
                                    <%--<td>${request.requestStatus}</td>--%>
                                    <%--<td>--%>
                                        <%--<a href="${requestUrl}${request.requestId}" class="btn btn-primary btn-sm">${anchorName}</a>--%>
                                    <%--</td>--%>
                                <%--</tr>--%>
                            <%--</c:forEach>--%>
                        <%--</tbody>--%>
                    <%--</table>--%>
                <%--</div>--%>
            <%--</div>--%>

        <%--</div>--%>
    <%--</div> <!-- Warper Ends Here (working area) -->--%>

    <c:import url="../../includes/footer.jsp"/>
    <script type="text/javascript" src="${resourceURL}/js/additional/request.js"></script>

</body>
</html>