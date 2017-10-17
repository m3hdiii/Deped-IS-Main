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
    <c:choose>
        <c:when test="${not empty notUpdated}">
            <p style="color: red;">${notUpdated}</p>
        </c:when>


        <c:when test="${not empty successfullyUpdated}">
            <p style="color: green;">${successfullyUpdated}</p>
            &nbsp;&nbsp;<a href="/equipment/create">Create Equipment</a>
        </c:when>

    </c:choose>


    <form:form commandName="equipment" method="post">
        <p><span>name: </span><form:input path="name"/></p>
        <p><span>description: </span><form:textarea path="description"/></p>
        <p><span>Equipment Status:
            <form:select path="equipmentStatus">
                <form:options/>
            </form:select>
        </p>

        <p><span>Model Number: </span><form:input path="modelNumber"/></p>
        <p><span>Purchase Price: </span><form:input path="purchasePrice"/></p>

        <form:select path="equipmentInfo">
            <form:options/>
        </form:select>

        <button type="submit">Create Brand</button>
    </form:form>

    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>