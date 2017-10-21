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
            <th>Visibility</th>
            <th>Quantity</th>
        </thead>
        <c:forEach items="${itemList}" var="item">
            <tr>
                <td>${item.name}</td>
                <td>${item.description}</td>
                <td>${item.itemType}</td>
                <td>${item.visibility}</td>
                <td>${item.quantity}</td>
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
