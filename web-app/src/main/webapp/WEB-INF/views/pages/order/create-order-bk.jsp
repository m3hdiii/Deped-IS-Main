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

    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Order Items&nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small>
            </h3>
        </div>


        <div class="row new-item-body">
            <div class="col-md-12">
                <div class="col-md-8 col-md-offset-2">
                    <div class="panel panel-default">
                        <c:set var="errors"
                               value="${requestScope['org.springframework.validation.BindingResult.order'].allErrors}"/>

                        <form:form commandName="order" method="post" class="form-horizontal" role="form"
                                   enctype="multipart/form-data">
                            <c:if test="${not empty errors}">
                                <div>
                                    <ul class="list-group">
                                        <c:forEach items="${fieldErrors}" var="error" varStatus="loop">
                                            <li class="list-group-item list-group-item-warning text-danger"><span
                                                    class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;${error.defaultMessage}
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                            <h3 class="text-center">Create Order</h3>
                            <div class="panel-body">
                                <div class="col-md-10 col-sm-offset-1">

                                    <div class="form-group">
                                        <label for="newDateRequired">Required Date</label>
                                        <form:input path="requiredDate" id="newDateRequired" type='date' class="form-control"
                                                    data-date-format="YYYY-MM-DD" placeholder="YYYY-MM-DD"/>
                                    </div>
                                    <hr class="style13">

                                    <div class="form-group">
                                        <label for="newSchedule">Schedule</label>
                                        <form:select id="newSchedule" path="orderSchedule" items="${schedules}" class="form-control"/>
                                    </div>

                                    <div class="form-group">
                                            <label for="newBudget">Budget Amount</label>
                                            <div class="input-group" id="newBudget">
                                                <span class="input-group-addon">₱</span>
                                                <form:input id="newBudget" type="number" path="budgetAmount" class="form-control" min="0"/>
                                                <span class="input-group-addon">.00</span>
                                            </div>
                                    </div>

                                </div>
                            </div>
                            <div class="modal-footer">
                                <div class="button-footer pull-right">
                                    <input type="reset" class="btn btn-default" value="Reset"/>
                                    <button type="submit" class="btn btn-primary">Next</button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div> <!-- New Item Body closing -->
        </div>
    </div>
    <!-- Warper Ends Here (working area) -->


    <%--<div class="page-header">--%>
        <%--<h3>&nbsp;&nbsp;&nbspOrder Request&nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small>--%>
        <%--</h3>--%>
    <%--</div>--%>

    <%--<div>--%>


        <%--<c:choose>--%>
            <%--<c:when test="${not empty notCreated}">--%>
                <%--<p style="color: red;">${notCreated}</p>--%>
            <%--</c:when>--%>


            <%--<c:when test="${not empty successfullyCreated}">--%>
                <%--<p style="color: green;">${successfullyCreated}</p>--%>
                <%--&nbsp;&nbsp;<a href="/order-details/create">Add Item to YourOrder</a>--%>
            <%--</c:when>--%>

        <%--</c:choose>--%>
    <%--</div>--%>

    <%--<div class="row">--%>

        <%--<div class="col-md-12">--%>
            <%--<div class="panel panel-default">--%>
                <%--<div class="panel-heading">Item Information</div>--%>
                <%--<div class="panel-body">--%>
                    <%--<form:form commandName="order" method="post" class="form-horizontal" role="form"--%>
                               <%--enctype="multipart/form-data">--%>
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">Required Date</label>--%>
                            <%--<div class="col-sm-2">--%>
                                <%--<form:input path="requiredDate" id="requiredDate" type='date' class="form-control"--%>
                                            <%--data-date-format="YYYY-MM-DD" placeholder="YYYY-MM-DD"/>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                        <%--<hr class="style13">--%>

                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">Schedule</label>--%>
                            <%--<div class="col-sm-2">--%>
                                <%--<form:select path="orderSchedule" items="${schedules}" class="form-control"/>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">Budget Amount</label>--%>
                            <%--<div class="col-sm-2">--%>
                                <%--<form:input path="budgetAmount" class="form-control"/>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                        <%--<hr class="style13">--%>

                        <%--<div class="btn-group-sm row">--%>
                            <%--<div class="col-sm-2">--%>
                                <%--<button type="submit" class="btn btn-success btn-block">Confirm</button>--%>
                            <%--</div>--%>
                            <%--<div class="form-group col-sm-2">--%>
                                <%--<button type="reset" class="btn btn-primary btn-block">Reset Fields</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                        <%--<hr class="style13">--%>

                    <%--</form:form>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>

    <%--</div>--%>

        <section class="navbar-fixed-bottom">
            <c:import url="../../includes/footer.jsp"/>
</body>
</html>

