<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Brand List"/>
    <c:param name="description" value="Brand List Page"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="page-header">
        <h3> &nbsp; Brand List </h3>
    </div>

    <div class="row">
        <div class="col col-lg-3"/>
        <div class="col col-lg-9">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="col-md-5">Name</th>
                    <th class="col-md-5">Description</th>
                    <th class="col-md-5">Contact Number</th>
                    <th class="col-md-5">Contact Number 2</th>
                    <th class="col-md-5">Contact Office Address</th>
                    <th class="col-md-5">Service Center Address</th>
                    <th class="col-md-5">Creation Date</th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${brands}" var="brand">
                    <tr>
                        <td class="col-md-2">${brand.name}</td>
                        <td class="col-md-2">${brand.description}</td>
                        <td class="col-md-2">${brand.contactNumber}</td>
                        <td class="col-md-2">${brand.contactNumber2}</td>
                        <td class="col-md-2">${brand.centralOfficeAddress}</td>
                        <td class="col-md-2">${brand.serviceCenterAddress}</td>
                        <td class="col-md-2">${brand.creationDate}</td>
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