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
            <h1>Approve Request
                <small>DepEd-Baguio City Division Office</small>
            </h1>

        </div>

        <div class="row item-body">
            <c:set var="basketMap" value="${requestDetailsForm.map}"/>

            <form:form commandName="requestDetailsForm" method="post">
                <div class="panel panel-default">
                    <h3 class="text-center">Requested Items</h3>
                    <div class="panel-body">

                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>
                                    <label class="checkbox checkbox-inline">
                                        <input type="checkbox"/>
                                    </label>
                                </th>
                                <th>Image</th>
                                <th>Name</th>
                                <th data-toggle="tooltip" title="Quantity of Available Item">Available QTY</th>
                                <th data-toggle="tooltip" title="Quantity of Request">Requested QTY</th>
                                <th>Item Type</th>
                                <th>Note</th>
                                <th>State</th>
                            </tr>
                            </thead>
                            <tbody>

                                <c:forEach items="${basketMap}" var="entry" varStatus="loop">
                                    <c:set var="strKey" value="${entry.key}"/>
                                    <c:set var="requestDet" value="${entry.value}"/>
                                    <tr>
                                        <td>
                                            <label class="checkbox checkbox-inline">
                                                <input type="checkbox">
                                            </label>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty requestDet.item.picName}">
                                                    <img src="${baseUrl}${requestDet.item.picName}" alt="item image" width="76px" height="50px"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${resourceURL}/images/shared-images/no-item.png"
                                                             alt="item image" width="76px" height="50px"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <th><div>
                                                ${requestDet.item.name}
                                            </div>
                                            <div>
                                                <small>SONY</small>
                                            </div>
                                        </th>

                                        <td>${requestDet.item.quantity}</td>
                                        <td>${requestDet.requestQuantity}</td>
                                        <td>${requestDet.item.itemType}</td>
                                        <td>
                                            <input type="textarea" id="${strKey}" disabled/>
                                        </td>
                                        <td>
                                            <form:select id="requestDetailsStatus${loop.index}" multiple="single"
                                                         path="map['${strKey}'].requestDetailsStatus"
                                                         onchange="processReason('requestDetailsStatus${loop.index}', '${strKey}')">
                                                <form:options items="${nextRequestDetailsStatuses}"/>
                                            </form:select>
                                        </td>

                                        <form:hidden path="map['${strKey}'].requestDetailsID.requestId"
                                                     value="${requestDet.request.requestId}"/>
                                        <form:hidden path="map['${strKey}'].requestDetailsID.itemId" value="${requestDet.item.itemId}"/>

                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <a href="#" class="btn btn-default pull-left"><i class="fa fa-chevron-left"></i><span> Back</span></a>
                        <button type="submit" class="btn btn-purple pull-right">Confirm</button>
                    </div>
                </div>
            </form:form>

        </div>

    </div> <!-- Warper Ends Here (working area) -->



    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
    <script type="text/javascript" src="${resourceURL}/js/additional/request-operation.js"></script>
</section>
</body>
</html>
