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
        <c:when test="${not empty notCreated}">
            <p style="color: red;">${notCreated}</p>
        </c:when>


        <c:when test="${not empty successfullyCreated}">
            <p style="color: green;">${successfullyCreated}</p>
            &nbsp;&nbsp;<a href="/borrow-request/create">Create New Borrow Request</a>
        </c:when>

    </c:choose>
    <form:form commandName="borrowRequest" method="post">
        <p><span>Description: </span><form:textarea path="description"/></p>
        <p><span>Request Date: </span><form:input path="requestDate"/></p>
        <p><span>Approval Date: </span><form:input path="approvalDate"/></p>
        <p><span>Release Date: </span><form:input path="releaseDate"/></p>
        <p><span>Borrow Status: </span><form:input path="borrowStatus"/></p>
        <p><span>Disapproval Message: </span><form:input path="disapprovalMessage"/></p>
        <button type="submit">Create Borrow Request</button>
    </form:form>


    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>