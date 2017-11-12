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

    <div class="page-header">
        <h3>&nbsp;&nbsp; Order ${h1Placeholder} Page&nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small>
        </h3>
    </div>

    <hr class="style13">


    <div class="row">
        <table class="table table-hover">
            <thead>
            <th>Order Id</th>
            <th>User Message</th>
            <th>Order Date</th>
            <th>Required Date</th>
            <th>Order State</th>
            <th>Budget Amount</th>
            <th>Consideration</th>
            </thead>
            <c:forEach items="${orders}" var="order">

                <tr>
                    <td>${order.orderId}</td>
                    <td>${order.user.firstName} ${order.user.lastName}</td>
                    <td>${order.orderDate}</td>
                    <td>${order.requiredDate}</td>
                    <td>${order.orderState}</td>
                    <td>${order.budgetAmount}</td>
                    <td><a href="${orderUrl}${order.orderId}">${anchorName}</a></td>
                </tr>
            </c:forEach>

        </table>
    </div>


    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
    <script type="text/javascript" src="${resourceURL}/js/additional/order.js"></script>
</section>
</body>
</html>