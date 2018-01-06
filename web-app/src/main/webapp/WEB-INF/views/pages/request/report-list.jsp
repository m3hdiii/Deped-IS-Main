<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Request Manager"/>
    <c:param name="description" value="List of Item"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>
    <div class="warper container-fluid">
        <div class="page-header">
            <h1>Requested Items
                <small>DepEd-Baguio City Division Office</small>
            </h1>

        </div>

        <div>
            <form:form cssClass="form-inline" commandName="requestSearch">
                <table class="table-bordered">
                    <tbody>
                        <tr>
                            <td style="padding: 10px;">
                                <div class="form-group">
                                    <label for="requestDateForm">Request Date From: </label><form:input type="date" id="requestDateForm" path="requestDateFrom" cssClass="form-control"
                                                                                                        placeholder="request date fro"/>
                                </div>
                            </td>
                            <td style="padding: 10px;">
                                <div class="form-group">
                                    <label for="requestDateTo">Request Date To: </label><form:input type="date" path="requestDateTo" cssClass="form-control"
                                                                                                    placeholder="requested date to"/>
                                </div>
                            </td>
                            <td style="padding: 10px;">
                                <div class="form-group">
                                    <label for="items">Items:</label>
                                    <form:select multiple="true" id="items" path="items" items="${itemList}" itemLabel="name" itemValue="name"
                                                 cssClass="form-control"/>
                                </div>
                            </td>
                            <td style="padding: 10px;">
                                <div class="form-group">
                                    <label for="requestedUsers">Requested Users:</label>
                                    <form:select multiple="true" path="requestedUsers" id="requestedUsers" items="${userList}" itemLabel="username"
                                                 itemValue="username" cssClass="form-control" placeholder="requested users"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 10px;">
                                <div class="form-group">
                                    <label for="consideredByUsers">Considered By Users: </label><form:select multiple="true" id="consideredByUsers" path="consideredByUsers" items="${userList}"
                                                                                                             itemLabel="username" itemValue="username"
                                                                                                             cssClass="form-control"/>
                                </div>
                            </td>
                            <td style="padding: 10px;">
                                <label for="issuedByUsers">Issued By Users: </label><form:select multiple="true" id="issuedByUsers" path="issuedByUsers" items="${userList}"
                                                                                                 itemLabel="username" itemValue="username"
                                                                                                 cssClass="form-control"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="userMessage">User Message Contain: </label><form:input path="userMessage" id="userMessage" cssClass="form-control"
                                                                        placeholder="user message contains"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="adminNotice">Admin Notice Contains: <form:input path="adminNotice" id="adminNotice" cssClass="form-control"
                                                                         placeholder="admin notice"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 10px;">
                                <label for="disapprovalMessage">Disapproval Message Contains: </label><form:input path="disapprovalMessage" id="disapprovalMessage" cssClass="form-control"
                                                                                placeholder="Disapproval Message Contains"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="cancellationReason">Cancellation Reason Contains: </label><form:input path="cancellationReason" id="cancellationReason" cssClass="form-control"
                                                                                placeholder="Cancellation Reason Contains"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="supplyOfficeRemark">Supply Officer Remark Contains: </label><form:input path="supplyOfficeRemark" id="supplyOfficeRemark" cssClass="form-control"
                                                                                  placeholder="Supply Officer Remark Contains"/>
                            </td>
                            <td style="padding: 10px;">
                                <label>Item Type:</label>
                                     <form:select multiple="true" path="itemTypes" items="${itemTypes}" itemLabel="name"
                                                  class="form-control"/>

                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 10px;">
                                <label for="requestQuantityFrom">Requested Quantity From: </label><form:input path="requestQuantityFrom" id="requestQuantityFrom" cssClass="form-control"
                                                                           placeholder="Requested Quantity From"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="requestQuantityTo">Requested Quantity To: </label><form:input path="requestQuantityTo" id="requestQuantityTo" cssClass="form-control"
                                                                         placeholder="Requested Quantity To"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="approvedQuantityFrom">Approved Quantity From: </label><form:input path="approvedQuantityFrom" id="approvedQuantityFrom" cssClass="form-control"
                                                                          placeholder="Approved Quantity From"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="approvedQuantityTo">Approved Quantity To: </label><form:input path="approvedQuantityTo" id="approvedQuantityTo" cssClass="form-control"
                                                                        placeholder="Approved Quantity To"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 10px;">
                                <label for="approvalDisapprovalDateFrom"></label>Consideration Date From: <form:input type="date" path="approvalDisapprovalDateFrom"
                                                                           class="form-control" id="approvalDisapprovalDateFrom"
                                                                           placeholder="Consideration Date From"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="approvalDisapprovalDateTo">Consideration Date To: </label><form:input type="date" path="approvalDisapprovalDateTo"
                                                                         class="form-control" id="approvalDisapprovalDateTo"
                                                                         placeholder="Consideration Date To"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="releaseDateFrom">Release Date From: </label><form:input type="date" id="releaseDateFrom" path="releaseDateFrom" class="form-control"
                                                                     placeholder="Release Date From"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="releaseDateTo">Release Date To: </label><form:input type="date" path="releaseDateTo" id="releaseDateTo" class="form-control"
                                                                   placeholder="Release Date To"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 10px;">
                                <label for="requestStatuses">Request Status: </label>
                                    <form:select multiple="true" path="requestStatuses" id="requestStatuses" class="form-control"
                                                 items="${statuses}" itemLabel="name"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="requestDetailsStatuses">Request Details Status: </label>
                                <form:select multiple="true" path="requestDetailsStatuses" id="requestDetailsStatuses" class="form-control"
                                             items="${detailsStatuses}" itemLabel="name"/>
                            </td>
                            <td style="padding: 10px;">
                                <button name="web" value="WEB" class="btn btn-primary"
                                        data-toggle="tooltip"
                                        title="Save the list">Report Web
                                </button>
                            </td>
                            <td style="padding: 10px;">
                                <button name="xml" value="XML" class="btn btn-success"
                                        data-toggle="tooltip"
                                        title="Send the Request">Report XMl
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>

            </form:form>

            <br/>
            <br/>
            <br/>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading clean text-center">Report Result</div>
            <div class="panel-body">

                <table cellpadding="0" cellspacing="0" border="0" class="table table-hover" id="basic-datatable">
                    <thead>
                    <tr>
                        <th>Request ID</th>
                        <th>User</th>
                        <th>User Message</th>
                        <th>Date Requested</th>
                        <th>Item Type</th>
                        <th>Status</th>
                        <th>More Info</th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requests}" var="request">
                        <tr>
                            <td>${request.requestId}</td>
                            <td>${request.user.firstName} ${request.user.middleName} ${request.user.lastName}</td>
                            <td>${request.userMessage}</td>
                            <td>${request.requestDate}</td>
                            <td>${request.itemType}</td>
                            <td>${request.requestStatus}</td>
                            <td>
                                <a class="btn btn-info" role="button"
                                   href="<c:url value='${requestDetailsInfo}${request.requestId}' />">request
                                    details</a>
                            </td>
                            <td>
                                <a class="btn btn-info" role="button"
                                   href="<c:url value='${requestDetailsInfo}${request.requestId}' />">request
                                    details</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>

        <c:import url="../../includes/footer.jsp"/>
</body>
</html>