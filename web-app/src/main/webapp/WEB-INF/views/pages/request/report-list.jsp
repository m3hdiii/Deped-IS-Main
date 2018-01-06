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
            <form:form commandName="requestSearch">

                    <span>Request Date From: <form:input type="date" path="requestDateFrom" class="form-control"
                                                         placeholder="request date fro"/></span>
                <span>Request Date To: <form:input type="date" path="requestDateTo" class="form-control"
                                                   placeholder="requested date to"/></span>
                <span>Items:
                    <form:select multiple="true" path="items" items="${itemList}" itemLabel="name" itemValue="name"
                                 class="form-control"/>
                </span>


                <span>Requested Users:
                    <form:select multiple="true" path="requestedUsers" items="${userList}" itemLabel="username"
                                 itemValue="username" class="form-control" placeholder="requested users"/>
                    </span>

                <span>Considered By Users: <form:select multiple="true" path="consideredByUsers" items="${userList}"
                                                        itemLabel="username" itemValue="username"
                                                        class="form-control"/></span>
                <span>Issued By Users: <form:select multiple="true" path="issuedByUsers" items="${userList}"
                                                    itemLabel="username" itemValue="username"
                                                    class="form-control"/></span>


                <span>User Message Contain: <form:input path="userMessage" class="form-control"
                                                        placeholder="user message contains"/></span>
                <span>Admin Notice Contains: <form:input path="adminNotice" class="form-control"
                                                         placeholder="admin notice"/></span>

                <span>Disapproval Message Contains: <form:input path="disapprovalMessage" class="form-control"
                                                                placeholder="Disapproval Message Contains"/></span>
                <span>Cancellation Reason Contains: <form:input path="cancellationReason" class="form-control"
                                                                placeholder="Cancellation Reason Contains"/></span>
                <span>Supply Officer Remark Contains: <form:input path="supplyOfficeRemark" class="form-control"
                                                                  placeholder="Supply Officer Remark Contains"/></span>


                <span>Item Type:
                     <form:select multiple="true" path="itemTypes" items="${itemTypes}" itemLabel="name"
                                  class="form-control"/>
                </span>

                <span>Requested Quantity From: <form:input path="requestQuantityFrom" class="form-control"
                                                           placeholder="Requested Quantity From"/></span>
                <span>Requested Quantity To: <form:input path="requestQuantityTo" class="form-control"
                                                         placeholder="Requested Quantity To"/></span>

                <span>Approved Quantity From: <form:input path="approvedQuantityFrom" class="form-control"
                                                          placeholder="Approved Quantity From"/></span>
                <span>Approved Quantity To: <form:input path="approvedQuantityTo" class="form-control"
                                                        placeholder="Approved Quantity To"/></span>
                <span>Consideration Date From: <form:input type="date" path="approvalDisapprovalDateFrom"
                                                           class="form-control"
                                                           placeholder="Consideration Date From"/></span>
                <span>Consideration Date To: <form:input type="date" path="approvalDisapprovalDateTo"
                                                         class="form-control"
                                                         placeholder="Consideration Date To"/></span>


                <span>Release Date From: <form:input type="date" path="releaseDateFrom" class="form-control"
                                                     placeholder="Release Date From"/></span>
                <span>Release Date To: <form:input type="date" path="releaseDateTo" class="form-control"
                                                   placeholder="Release Date To"/></span>
                <span>Request Status:
                    <form:select multiple="true" path="requestStatuses" class="form-control"
                                 items="${statuses}" itemLabel="name"/>
                        </span>

                <span>Request Details Status:
                <form:select multiple="true" path="requestDetailsStatuses" class="form-control"
                             items="${detailsStatuses}" itemLabel="name"/>

                </span>


                <p>
                    <button name="web" value="WEB" class="btn btn-primary"
                            data-toggle="tooltip"
                            title="Save the list">Report Web
                    </button>

                    <button name="xml" value="XML" class="btn btn-success"
                            data-toggle="tooltip"
                            title="Send the Request">Report XMl
                    </button>
                </p>
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