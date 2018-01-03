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
                    <button type="submit" class="btn btn-primary">Search</button>
                </p>
            </form:form>

            <br/>
            <br/>
            <br/>
        </div>

        <%--<div class="row item-body">

            <c:forEach items="${requests}" var="request" varStatus="loop">
                <form:form class="panel panel-warning col-md-4 no-padd">
                    <div class="panel-heading no-padd">
                    </div>
                    ${request.requestId}
                    ${request.user.firstName}
                    ${request.requestDetails}
                    <div class="panel-body no-padd">
                        <nav class="navbar-default hidden-xs col-md-12" role="navigation">
                            <ul class="nav navbar-nav">
                                <li><a href="#"><img src="../../assets/images/avtar/user.png" class="img-circle"
                                                     alt="user#2" width="40"></a></li>
                                <li><a href="#"><strong>adhwheb u. gfffgf</strong></a></li>
                                <li class="dropdown">
                                    <a href="#" data-toggle="dropdown" class="col-md-2"><i class="fa fa-ellipsis-v"></i></a>
                                    <ul role="menu" class="dropdown-menu">
                                        <li><a href="#">Approve All</a></li>
                                        <li><a href="#">Reject All</a></li>
                                        <li class="divider">
                                        <li><a href="#">Notify User</a></li>
                                        </li>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                            &lt;%&ndash;<c:forEach items="${requestDetails}">&ndash;%&gt;
                            &lt;%&ndash;<div class="panel panel-default no-padd col-md-12">&ndash;%&gt;
                            &lt;%&ndash;<div class="panel-body">&ndash;%&gt;
                            &lt;%&ndash;<label class="label label-info pull-left padd-xs text-right">HBW Ballpen</label>&ndash;%&gt;
                            &lt;%&ndash;<p class="pull-right"><label>QTY: </label> 3 box</p>&ndash;%&gt;
                            &lt;%&ndash;<div class="request-info col-md-12">&ndash;%&gt;

                            &lt;%&ndash;<p>This Ballpen is for teachers of Boys High Baguio.</p>&ndash;%&gt;
                            &lt;%&ndash;<div class="req-footer margn-t-sm">&ndash;%&gt;
                            &lt;%&ndash;<p class="pull-left margn-t-xs text-gray">12/04/2017 11:21:00</p>&ndash;%&gt;
                            &lt;%&ndash;<div class="btn-group pull-right">&ndash;%&gt;
                            &lt;%&ndash;<button class="btn btn-danger btn-sm"><i class="fa fa-close"></i></button>&ndash;%&gt;
                            &lt;%&ndash;<button class="btn btn-success btn-sm"><i class="fa fa-check"></i> </button>&ndash;%&gt;
                            &lt;%&ndash;</div>&ndash;%&gt;
                            &lt;%&ndash;</div>&ndash;%&gt;

                            &lt;%&ndash;</div>&ndash;%&gt;
                            &lt;%&ndash;</div>&ndash;%&gt;
                            &lt;%&ndash;</div>&ndash;%&gt;
                            &lt;%&ndash;</c:forEach>&ndash;%&gt;


                    </div>
                </form:form>
            </c:forEach>

            &lt;%&ndash;
                <a href="/item/${item.itemId}">
                ${item.functionType}
                ${item.threshold}
                <a href="/item/update/${item.itemId}"><img src="${resourceURL}/images/edit.png" width="16"/></a>
                <img src="${resourceURL}/images/delete.png" width="16"/>
            &ndash;%&gt;

        </div>--%>
        <c:import url="../../includes/footer.jsp"/>
</body>
</html>