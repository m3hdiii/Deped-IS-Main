<%--
  Created by IntelliJ IDEA.
  User: mehdi
  Date: 10/22/17
  Time: 10:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
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
    <c:param name="title" value="Insert Equipment Info"/>
    <c:param name="description" value="Insert Equipment Info Page"/>
</c:import>
<html>
<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Lend Equipment
                <small>to a user</small>
            </h3>
        </div>

        <c:set var="errors"
               value="${requestScope['org.springframework.validation.BindingResult.requestDetails'].allErrors}"/>
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

        <div class="row">

            <div class="request-body-container">
                <nav class="col-md-12 padd-t-lg">

                    <form role="search" class="col-md-6 col-lg-offset-3 hidden-xs">
                        <div class="app-search">
                            <input type="text" placeholder="Search for an item..."
                                   class="form-control form-control-circle">
                            <hr class="clean">
                        </div>

                    </form>
                </nav>

                <div class="item-body col-lg-12">

                    <form:form commandName="borrowRequestDetailsForm" method="post">
                    <table cellspacing="0" border="0"
                           class="table table-striped table-hover"
                           id="basic-datatable">
                        <thead>
                        <th>#</th>
                        <th>Item</th>
                        <th>Item Serial</th>
                        <th>Release Date</th>
                        <th>Tracking Status</th>
                        <th>Request #</th>
                        <th>Requested By</th>
                        </thead>
                        <tbody>

                        <c:forEach items="${borrowRequestDetailsForm.listOfRequestTacker}" var="reqTracker"
                                   varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td>${reqTracker.requestDetails.item.name}</td>
                                <td>
                                    <form:select path="listOfRequestTacker['${loop.index}'].itemDetails.officeSerialNo"
                                                 class="form-control">
                                        <form:option value="-" label="---Choose a Status"/>
                                        <form:options items="${reqTracker.itemDetailsList}" itemLabel="officeSerialNo"/>
                                    </form:select>
                                </td>
                                <td><form:input id="releaseDate" onclick="clickOnDateInput('releaseDate')"
                                                path="listOfRequestTacker['${loop.index}'].releaseDate"/></td>
                                <td>
                                    <form:select path="listOfRequestTacker['${loop.index}'].trackingStatus"
                                                 class="form-control">
                                        <form:option value="-" label="---Choose a Status"/>
                                        <form:options items="${trackingStatuses}" itemLabel="name"/>
                                    </form:select>
                                </td>
                                <td>
                                    <form:input disabled="true"
                                                path="listOfRequestTacker['${loop.index}'].requestDetails.request"
                                                value="${reqTracker.requestDetails.request.requestId}"/>
                                </td>

                                <td>
                                    <form:input disabled="true"
                                                path="listOfRequestTacker['${loop.index}'].requestDetails.request.user"
                                                value="${reqTracker.requestDetails.request.user.username}"/>
                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>
                        <button class="col-sm-2 btn btn-success btn-block" type="submit">Add To Order</button>
                        </form:form>
                </div>
            </div>
        </div>
    </div>
    <br/>
    <br/>
    <!-- Warper Ends Here (working area) -->
    <c:import url="../../includes/footer.jsp"/>
    <script src="${resourceURL}/js/additional/item-info.js" type="text/javascript"></script>
        <script type="text/javascript" src="${resourceURL}/js/main/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="${resourceURL}/js/main/main.js"></script>

</body>
</html>

