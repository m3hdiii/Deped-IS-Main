<%--
  Created by IntelliJ IDEA.
  User: kevin
  Date: 27/09/2017
  Time: 11:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:url value="/public" var="resourceURL" scope="request"/>
<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Semi-Expendable Items"/>
    <c:param name="description" value="List of Semi-Expendables"/>
</c:import>
<html>
<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">
        <div class="page-header">
            <h1>Semi-Expendable
                <small>DepEd-Baguio City Division Office</small>
            </h1>

        </div>

        <div class="row item-body">

            <c:forEach items="${semiExpendable}" var="semiExpendable">
                <div class='col-xs-3 thumbnail item-content-thumbnail'>
                    <img src="${resourceURL}/images/carousel/1.jpg" alt="1-jpg" width="304px" height="236px">
                    <div class="item-infomation text-center">
                        <h4>${semiExpendable.name}</h4>
                        <label>Description:</label><p>${semiExpendable.description}</p>
                        <label>Item Type:</label><p>${semiExpendable.itemType}</p>
                        <label>Quantity:</label><p>${semiExpendable.quantity}</p>
                        <button class="btn btn-md btn-purple">Add to cart</button>
                        <button class="btn btn-md btn-danger">Check out</button>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>
