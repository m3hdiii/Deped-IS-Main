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
            <h3>Summary Request&nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small>
            </h3>
        </div>

        <div class="row">
            <h4>Request Information</h4>
            <p>Request Number: ${relatedRequest.requestId}</p>
            <p>Requested
                By: ${relatedRequest.user.firstName}&nbsp;${relatedRequest.user.lastName}</p>
            <p>Reason: ${relatedRequest.userMessage}</p>
        </div>


        <div class="panel panel-default">
            <div class="panel-body">

                <table cellpadding="0" cellspacing="0" border="0" class="table table-hover"
                       id="toggleColumn-datatable">
                    <thead>
                    <tr>
                        <th>Image</th>
                        <th>Name</th>
                        <th data-toggle="tooltip" title="Quantity Requested">Requested QTY</th>
                        <th>Item Type</th>
                        <th>Note</th>
                        <th>State</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach items="${requestDetailsList}" var="requestDet" varStatus="loop">

                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty requestDet.item.picName}">
                                        <img src="${baseUrl}${requestDet.item.picName}" alt="item image" width="76px"
                                             height="50px"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${resourceURL}/images/shared-images/no-item.png"
                                             alt="item image" width="76px" height="50px"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <th>
                                    ${requestDet.item.name}
                            </th>
                            <td>${requestDet.requestQuantity}</td>
                            <td>${requestDet.item.itemType}</td>
                            <td>
                                <c:if test="${not empty requestDet.disapprovalMessage}">
                                    <input type="textarea" id="${requestDet.disapprovalMessage}" disabled/>
                                </c:if>

                                <c:if test="${not empty requestDet.cancellationReason}">
                                    <input type="textarea" id="${requestDet.cancellationReason}" disabled/>
                                </c:if>

                            </td>
                            <td>
                                    ${requestDet.requestDetailsStatus}
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <hr class="clean">
            </div>
        </div>
    </div>


    <c:import url="../../includes/footer.jsp"/>

</body>
</html>
