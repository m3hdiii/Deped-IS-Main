<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="List of Departments"/>
    <c:param name="description" value="This page retrieves all the list of existing departments"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="row">
        <div class="col col-lg-12">
            <div class="panel-heading"><h1>Department List</h1></div>
            <div class="panel panel-default">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th class="col-md-3">Name</th>
                        <th class="col-md-3">Description</th>
                        <th class="col-md-3">Department Head</th>
                        <th class="col-md-3">Edit</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${departments}" var="department">
                        <tr>
                            <td><a href="/department/${department.departmentId}">${department.name}</a></td>
                            <td>${department.description}</td>
                            <td>${department.departmentHead}</td>
                            <td><a href="/department/update/${department.departmentId}"><img
                                    src="${resourceURL}/images/edit.png" width="16"/></a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <c:import url="../../modals/cart.jsp"/>

</section>

<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>