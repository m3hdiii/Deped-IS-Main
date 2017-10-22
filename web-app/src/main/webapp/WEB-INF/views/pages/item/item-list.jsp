<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="EDIT LATER"/>
    <c:param name="description" value="EDIT LATER PAGE"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="page-header">
        <h3> Edit </h3>
    </div>
    <table class="table table-hover">
        <thead>
            <th>Name</th>
            <th>Description</th>
            <th>Item Type</th>
            <th>Function Type</th>
            <th>Threshold</th>
            <th>Quantity</th>
            <th>Item Picture</th>
            <th>Edit</th>
            <th>Delete</th>
        </thead>
        <c:forEach items="${itemList}" var="item">
            <tr>
                <td><a href="/item/${item.itemId}">${item.name}</a></td>
                <td>${item.description}</td>
                <td>${item.itemType}</td>
                <th>${item.functionType}</th>
                <td>${item.threshold}</td>
                <td>${item.quantity}</td>

                <c:choose>
                    <c:when test="${not empty item.picName}">
                        <td><img width="64" src="${baseUrl}${item.picName}" alt="item image"/></td>
                    </c:when>
                    <c:otherwise>
                        <td><img width="64" src="${resourceURL}/images/shared-images/no-item.png" alt="item image"/>
                        </td>
                    </c:otherwise>
                </c:choose>

                <td><a href="/item/update/${item.itemId}"><img src="${resourceURL}/images/edit.png"
                                                               width="16"/></a></td>
                <td><img src="${resourceURL}/images/delete.png" width="16"/></td>

            </tr>
        </c:forEach>
    </table>

    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>
