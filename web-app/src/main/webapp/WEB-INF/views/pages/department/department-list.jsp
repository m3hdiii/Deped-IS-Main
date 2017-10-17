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
                        <th>Name</th>
                        <th>Description</th>
                        <th>Department Head</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${departments}" var="department">
                        <tr>
                            <td>${department.name}</td>
                            <td>${department.description}</td>
                            <td>${department.departmentHead}</td>
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