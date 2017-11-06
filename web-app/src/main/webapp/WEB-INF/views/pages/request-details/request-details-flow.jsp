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

    <div class="page-header">
        <h3>&nbsp;&nbsp;&nbsprequest Request&nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small>
        </h3>
    </div>

    <div class="row">
        <p>Request Number: ${relatedRequest.requestId}</p>
        <p>Requested
            By: ${relatedRequest.user.firstName}&nbsp;${relatedRequest.user.middleName}&nbsp;${relatedRequest.user.lastName}</p>
    </div>

    <hr class="style13">


    <div class="row">
        <table class="table table-hover">
            <thead>
            <th>Name</th>
            <th>Item Type</th>
            <th>Available Quantity</th>
            <th>Item Picture</th>
            <th>Request Quantity</th>
            <td>Status</td>
            </thead>

            <c:set var="basketMap" value="${requestDetailsForm.map}"/>

            <form:form commandName="requestDetailsForm" method="post">

                <c:forEach items="${basketMap}" var="entry" varStatus="loop">
                    <c:set var="strKey" value="${entry.key}"/>
                    <c:set var="requestDet" value="${entry.value}"/>

                    <tr>
                        <td>${requestDet.item.name}</td>
                        <td>${requestDet.item.itemType}</td>
                        <td>${requestDet.item.quantity}</td>

                        <c:choose>
                            <c:when test="${not empty requestDet.item.picName}">
                                <td><img width="64" src="${baseUrl}${requestDet.item.picName}" alt="item image"/></td>
                            </c:when>
                            <c:otherwise>
                                <td><img width="64" src="${resourceURL}/images/shared-images/no-item.png"
                                         alt="item image"/>
                                </td>
                            </c:otherwise>
                        </c:choose>

                        <td>
                                ${requestDet.requestQuantity}
                        </td>
                        <td>
                            <form:select multiple="single" path="map['${strKey}'].requestDetailsStatus">
                                <form:options items="${nextRequestDetailsStatuses}"/>
                            </form:select>
                        </td>

                        <form:hidden path="map['${strKey}'].requestDetailsID.requestId"
                                     value="${requestDet.request.requestId}"/>
                        <form:hidden path="map['${strKey}'].requestDetailsID.itemId" value="${requestDet.item.itemId}"/>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="3">
                        <button>Approve</button>
                    </td>
                </tr>
            </form:form>

        </table>
    </div>


    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
    <script type="text/javascript" src="${resourceURL}/js/additional/request.js"></script>
</section>
</body>
</html>
