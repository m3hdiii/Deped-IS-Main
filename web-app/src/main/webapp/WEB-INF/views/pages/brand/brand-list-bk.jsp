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
                    <th class="col-md-5">Creation Date</th>
                    <th class="col-md-2">Edit</th>
                    <th class="col-md-2">Delete</th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${brands}" var="brand">
                    <tr>
                        <td class="col-md-2"><a href="/brand/${brand.brandId}">${brand.name}</a></td>
                        <td class="col-md-2">${brand.description}</td>
                        <td class="col-md-2">${brand.creationDate}</td>
                        <td><a href="/brand/update/${brand.brandId}"><img src="${resourceURL}/images/edit.png"
                                                                          width="16"/></a></td>
                        <td><img src="${resourceURL}/images/delete.png" width="16"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
            <c:import url="../../includes/footer.jsp"/>
</body>
</html>