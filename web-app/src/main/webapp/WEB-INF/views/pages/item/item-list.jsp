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
    <c:param name="title" value="Item"/>
    <c:param name="description" value="List of Item"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>
    <div class="warper container-fluid">
        <div class="page-header">
            <h1>Items
                <small>DepEd-Baguio City Division Office</small>
            </h1>

        </div>

        <div class="row item-body">

            <c:forEach items="${itemList}" var="item">
                <div class='col-xs-3 thumbnail item-content-thumbnail'>

                    <c:choose>
                        <c:when test="${not empty item.picName}">
                            <img width="304px" height="236px" src="${baseUrl}${item.picName}" alt="item image"/>
                        </c:when>
                        <c:otherwise>
                            <img width="304px" height="236px" src="${resourceURL}/images/shared-images/no-item.png"
                                 alt="item image"/>
                        </c:otherwise>
                    </c:choose>
                    <div class="item-infomation text-center">
                        <h4>${item.name}</h4>
                        <label>Description:</label>
                        <p>${item.description}</p>
                        <label>Item Type:</label>
                        <p>${item.itemType}</p>
                        <label>Quantity:</label>
                        <p>${item.quantity}</p>
                        <button class="btn btn-md btn-purple">Add to cart</button>
                        <button class="btn btn-md btn-danger">Check out</button>
                    </div>
                </div>
            </c:forEach>
        </div>
        <%--
            <a href="/item/${item.itemId}">
            ${item.functionType}
            ${item.threshold}
            <a href="/item/update/${item.itemId}"><img src="${resourceURL}/images/edit.png" width="16"/></a>
            <img src="${resourceURL}/images/delete.png" width="16"/>
        --%>

    </div>

    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>




