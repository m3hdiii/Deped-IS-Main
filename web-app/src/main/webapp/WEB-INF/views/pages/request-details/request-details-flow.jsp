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
            <h3><c:choose>
                <c:when test="${not empty nextRequestDetailsStatuses}">
                    Manage Request&nbsp;
                </c:when>
                <c:otherwise>
                    Request Details
                </c:otherwise>
            </c:choose>
                <small>&nbsp;for Goods, Semi-Expendable and Equipment</small>

            </h3>
        </div>

        <div class="row">
            <h4>Order Information</h4>
            <p>Request Number: ${relatedRequest.requestId}</p>
            <p>Requested
                By: ${relatedRequest.user.firstName}&nbsp;${relatedRequest.user.middleName}&nbsp;${relatedRequest.user.lastName}</p>
            <p>Reason: ${relatedRequest.userMessage}</p>
        </div>


        <c:set var="basketMap" value="${requestDetailsForm.map}"/>

        <form:form commandName="requestDetailsForm" method="post">

            <div class="panel panel-default">
                <div class="panel-body">

                    <table cellpadding="0" cellspacing="0" border="0" class="table table-hover"
                           id="basic-datatable">
                        <thead>
                        <tr>
                            <c:if test="${not empty nextRequestDetailsStatuses}">
                                <th>#</th>
                            </c:if>
                            <th>Image</th>
                            <th>Name</th>
                            <th data-toggle="tooltip" title="Quantity of Available Item">Available QTY</th>
                            <th data-toggle="tooltip" title="Quantity of Request">Requested QTY</th>
                            <th>Item Type</th>
                            <c:if test="${not empty nextRequestDetailsStatuses}">
                                <th>Note</th>
                                <th>State</th>
                            </c:if>
                        </tr>
                        </thead>

                        <tbody>

                        <c:forEach items="${basketMap}" var="entry" varStatus="loop">
                            <c:set var="strKey" value="${entry.key}"/>
                            <c:set var="requestDet" value="${entry.value}"/>
                            <tr>

                                <c:if test="${not empty nextRequestDetailsStatuses}">
                                    <td>${loop.index + 1}
                                            <%--<label class="cr-styled">
                                                <input type="checkbox" ng-model="todo.done">
                                                <i class="fa"></i>
                                            </label>--%>
                                    </td>
                                </c:if>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty requestDet.item.picName}">
                                            <img src="${baseUrl}${requestDet.item.picName}" alt="item image"
                                                 width="76px" height="50px"/>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${resourceURL}/images/shared-images/no-item.png"
                                                 alt="item image" width="76px" height="50px"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <th>
                                    <div>
                                            ${requestDet.item.name}
                                    </div>
                                    <div>
                                        <small>${requestDet.item.brand.name}</small>
                                    </div>
                                </th>

                                <td>${requestDet.item.quantity}</td>
                                <td>${requestDet.requestQuantity}</td>
                                <td>${requestDet.item.itemType}</td>

                                <c:if test="${not empty nextRequestDetailsStatuses}">
                                    <td>
                                        <input type="textarea" id="${strKey}" disabled/>
                                    </td>

                                    <c:choose>
                                        <c:when test="${requestDet.requestDetailsStatus == 'DISAPPROVED' || requestDet.requestDetailsStatus == 'CANCELED'}">
                                            <td>
                                                    ${requestDet.requestDetailsStatus.name}
                                                <form:hidden path="map['${strKey}'].requestDetailsStatus"/>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>
                                                <form:select id="requestDetailsStatus${loop.index}" multiple="single"
                                                             path="map['${strKey}'].requestDetailsStatus"
                                                             onchange="processReason('requestDetailsStatus${loop.index}', '${strKey}')">
                                                    <form:options items="${nextRequestDetailsStatuses}"
                                                                  itemLabel="action"/>
                                                </form:select>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>


                                    <form:hidden path="map['${strKey}'].requestDetailsID.requestId"
                                                 value="${requestDet.request.requestId}"/>
                                    <form:hidden path="map['${strKey}'].requestDetailsID.itemName"
                                                 value="${requestDet.item.name}"/>
                                    <form:hidden path="map['${strKey}'].requestQuantity"
                                                 value="${requestDet.requestQuantity}"/>

                                </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <hr class="clean">
                </div>
                <c:if test="${not empty nextRequestDetailsStatuses}">
                    <div class="modal-footer">
                        <a href="/request/approval-list" class="btn btn-default pull-left"><i
                                class="fa fa-chevron-left"></i><span> Back</span></a>
                        <button type="submit" class="btn btn-purple pull-right">Confirm</button>
                    </div>
                </c:if>
            </div>
        </form:form>
    </div>
    <c:import url="../../includes/footer.jsp"/>
    <script type="text/javascript" src="${resourceURL}/js/additional/request-operation.js"></script>

</body>
</html>
