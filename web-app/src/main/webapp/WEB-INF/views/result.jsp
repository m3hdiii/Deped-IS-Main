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
<c:import url="includes/head.jsp">
    <c:param name="title" value="${result.headTagTitle}"/>
    <c:param name="description" value="${result.headTagDescription}"/>
</c:import>

<body>
<c:import url="includes/left-nav.jsp"/>
<section class="content">
    <c:import url="includes/top-nav.jsp"/>

    <div class="page-header">
        <h3>${result.heading}</h3>
    </div>

    <hr class="style13">


    <div class="row">
        <div class="col-lg-3"></div>
        <c:choose>
            <c:when test="${not empty result.successMessage}">
                <h2 class="col-lg-6" style="color: green">${result.successMessage}</h2>
            </c:when>
            <c:when test="${not empty result.failureMessage}">
                <h2 class="col-lg-6" style="color: red">${result.failureMessage}</h2>
            </c:when>
        </c:choose>
        <div class="col-lg-3"></div>
    </div>


    <c:import url="modals/cart.jsp"/>
</section>
    <c:import url="includes/footer.jsp"/>
    <script type="text/javascript" src="${resourceURL}/js/additional/order.js"></script>
</body>
</html>
