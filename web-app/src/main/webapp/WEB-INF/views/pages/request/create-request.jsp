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
    <c:param name="title" value="Create Request"/>
    <c:param name="description" value="Item Registration Page"/>
</c:import>
<html>
<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Request Item&nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small>
            </h3>
        </div>


        <div class="row new-item-body">
            <div class="col-md-12">
                <div class="col-md-8 col-md-offset-2">
                    <div class="panel panel-default">
                        <c:set var="errors"
                               value="${requestScope['org.springframework.validation.BindingResult.request'].allErrors}"/>

                        <form:form commandName="request" method="post" class="form-horizontal" role="form">
                            <c:if test="${not empty errors}">
                                <div>
                                    <ul class="list-group">
                                        <c:forEach items="${errors}" var="error" varStatus="loop">
                                            <li class="list-group-item list-group-item-warning text-danger"><span
                                                    class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;${error.defaultMessage}
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                            <h3 class="text-center">Create Request</h3>
                            <div class="panel-body">
                                <div class="col-md-10 col-sm-offset-1">

                                    <div class="form-group">
                                        <label for="newReason">Explain Your Reason For Requesting</label>
                                        <form:textarea path="userMessage" class="form-control" id="newReason"/>
                                    </div>

                                </div>

                                <div class="col-md-10 col-sm-offset-1">

                                    <div class="form-group">
                                        <label for="newReason">* Type of your Item</label>
                                        <form:select path="itemType" id="newItemType" class="form-control chosen-select"
                                                     data-placeholder="Select a Type">
                                            <form:option value="" label="--Please Select"/>
                                            <form:options items="${itemType}" itemLabel="name"/>
                                        </form:select>
                                    </div>

                                </div>
                            </div>
                            <div class="modal-footer">
                                <div class="button-footer pull-right">
                                    <input type="reset" class="btn btn-default" value="Clear"/>
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
        <%--<h3>Create Request&nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small>--%>
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
        <%--<form:form commandName="request" method="post" class="form-horizontal" role="form">--%>
        <%--<div class="form-group">--%>
        <%--<label class="col-sm-2 control-label">Explain Your Reason For Requesting</label>--%>
        <%--<div class="col-sm-2">--%>
        <%--<form:textarea path="userMessage" class="form-control"/>--%>
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
        <c:import url="../../includes/footer.jsp"/>

</body>
</html>

