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
        <h3> Supply List </h3>
    </div>

    <p>
        JSP location:
    </p>
    <p style="color: red; font-weight: bold;">
        WEB-INF/views/<c:out value="${jspLocation}"/>.jsp
    </p>
    <hr>
    <p>
        Controller Class:
    </p>
    <p style="color: red; font-weight: bold;">
        ${controllerClazz}
    </p>
    <hr>
    <p>
        Method Name:
    </p>
    <p style="color: red; font-weight: bold;">
        ${methodName}
    </p>

    <div class="row">
        <div class="col col-lg-3"/>
        <div class="col col-lg-9">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Supply ID</th>
                    <th>Description</th>


                </tr>
                </thead>
                <tbody>
                <c:forEach items="${supplies}" var="supply">
                    <tr>
                        <td>${supply.supplyId}</td>
                        <td>${supply.description}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>