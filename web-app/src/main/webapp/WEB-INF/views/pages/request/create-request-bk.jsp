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
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Item Registration"/>
    <c:param name="description" value="Item Registration Page"/>
</c:import>
<html>
<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

    <div class="page-header">
        <h3>&nbsp;&nbsp;&nbspOrder Request&nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small>
        </h3>
    </div>

    <div>


        <c:choose>
            <c:when test="${not empty notCreated}">
                <p style="color: red;">${notCreated}</p>
            </c:when>


            <c:when test="${not empty successfullyCreated}">
                <p style="color: green;">${successfullyCreated}</p>
                &nbsp;&nbsp;<a href="/order-details/create">Add Item to YourOrder</a>
            </c:when>

        </c:choose>
    </div>

    <div class="row">

        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">Item Information</div>
                <div class="panel-body">
                    <form:form commandName="request" method="post" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Explain Your Reason For Requesting</label>
                            <div class="col-sm-2">
                                <form:textarea path="userMessage" class="form-control"/>
                            </div>
                        </div>

                        <hr class="style13">

                        <div class="btn-group-sm row">
                            <div class="col-sm-2">
                                <button type="submit" class="btn btn-success btn-block">Confirm</button>
                            </div>
                            <div class="form-group col-sm-2">
                                <button type="reset" class="btn btn-primary btn-block">Reset Fields</button>
                            </div>
                        </div>

                        <hr class="style13">

                    </form:form>
                </div>
            </div>
        </div>

    </div>

            <c:import url="../../includes/footer.jsp"/>
</body>
</html>

