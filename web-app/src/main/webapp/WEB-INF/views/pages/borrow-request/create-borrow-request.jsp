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

        <c:if test="${not empty errorMessage}">
            <div>
                <ul class="list-group">

                    <li class="list-group-item list-group-item-warning text-danger">
                            <span class="glyphicon glyphicon-exclamation-sign">
                            &nbsp;${errorMessage}
                        </span>
                    </li>
                    <li class="list-group-item list-group-item-warning text-danger">
                            <span class="glyphicon glyphicon-exclamation-sign">
                            &nbsp;Insert equipment info from <a href="<c:url value="${insertUrl}" />">-> Here <-</a>
                        </span>
                    </li>
                </ul>
            </div>
        </c:if>

        <div class="row">

            <div class="request-body-container">
                <form:form commandName="borrowRequestDetailsForm" method="post">
                    <div class="panel panel-default">
                        <div class="panel-heading clean text-center">Equipment Information</div>
                        <div class="panel-body">
                            <div class="item-body col-lg-12">
                                <table cellspacing="0" border="0"
                                       class="table table-striped table-hover">
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

                                    <c:forEach items="${borrowRequestDetailsForm.listOfRequestTracker}" var="reqTracker"
                                               varStatus="loop">
                                        <tr>
                                            <td>${loop.index + 1}</td>
                                            <td>${reqTracker.requestDetails.item.name}</td>
                                            <td>
                                                <form:select
                                                        path="listOfRequestTracker['${loop.index}'].itemDetails.officeSerialNo"
                                                        class="form-control">
                                                    <form:option value="-" label="---Choose a Status"/>
                                                    <form:options items="${reqTracker.itemDetailsList}"
                                                                  itemLabel="officeSerialNo"
                                                                  itemValue="officeSerialNo"/>
                                                </form:select>
                                            </td>
                                            <td><form:input id="releaseDate${loop.index}"
                                                            onclick="clickOnDateInput('releaseDate${loop.index}')"
                                                            path="listOfRequestTracker['${loop.index}'].releaseDate"/></td>

                                            <td>
                                                GOING TO BE HELD
                                            </td>
                                            <td>
                                                    ${reqTracker.requestDetails.request.requestId}
                                            </td>

                                            <td>
                                                    ${reqTracker.requestDetails.request.user.username}
                                                <form:hidden
                                                        path="listOfRequestTracker['${loop.index}'].trackingStatus"
                                                        value="IN_USE"/>
                                                <form:hidden
                                                        path="listOfRequestTracker['${loop.index}'].requestDetails.request.requestId"/>
                                                <form:hidden
                                                        path="listOfRequestTracker['${loop.index}'].requestDetails.request.requestId"/>
                                                <form:hidden
                                                        path="listOfRequestTracker['${loop.index}'].acquiredUser"
                                                        value="${reqTracker.requestDetails.request.user.username}"/>

                                                <form:hidden
                                                        path="listOfRequestTracker['${loop.index}'].requestDetails.requestDetailsID.requestId"
                                                        value="${reqTracker.requestDetails.request.requestId}"/>

                                                <form:hidden
                                                        path="listOfRequestTracker['${loop.index}'].itemDetails.item.name"
                                                        value="${reqTracker.requestDetails.item.name}"/>

                                                <form:hidden path="listOfRequestTracker['${loop.index}'].requestId"
                                                             value="${reqTracker.requestDetails.request.requestId}"/>

                                                <form:hidden path="listOfRequestTracker['${loop.index}'].itemName"
                                                             value="${reqTracker.requestDetails.item.name}"/>

                                            </td>

                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button class="col-sm-2 btn btn-purple pull-right" type="submit">Lend The Equipment</button>
                        </div>
                    </div>

                </form:form>
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

