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
    <c:param name="title" value="${pageTitle}"/>
    <c:param name="description" value="${topHeading}"/>
</c:import>

<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

        <div class="warper container-fluid">

            <div class="page-header">
                <h3>Order ${h1Placeholder} Page
                    <small>for Goods, Semi-Expendable and Equipment</small>
                </h3>
            </div>


            <div class="panel panel-default">
                <div class="panel-heading text-center clean">Order Information</div>
                <div class="panel-body">

                    <table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered" id="basic-datatable">
                        <thead>
                        <tr>
                            <th>User</th>
                            <th>Date Ordered</th>
                            <th>Date Required</th>
                            <th>Order State</th>
                            <th>Budget Amount</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${orders}" var="order">
                            <tr>
                                <th>${order.user.firstName} ${order.user.middleName} ${order.user.lastName}</th>
                                <td>${order.orderDate}</td>
                                <td>${order.requiredDate}</td>
                                <td>${order.orderState}</td>
                                <td>${order.budgetAmount}</td>
                                <td>
                                    <a href="${orderUrl}${order.orderId}" class="btn btn-primary btn-sm">${anchorName}</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>
        </div>
            <!-- Warper Ends Here (working area) -->

            <c:import url="../../includes/footer.jsp"/>
    <script type="text/javascript" src="${resourceURL}/js/additional/order.js"></script>

</body>
</html>