<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Category List"/>
    <c:param name="description" value="Category List Page"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="page-header">
        <h3> &nbsp; Category </h3>
    </div>

    <div class="row">
        <div class="col col-lg-3"/>
        <div class="col col-lg-9">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="col-md-5">Name</th>
                    <th class="col-md-5">Description</th>
                    <th class="col-md-5">Parent Category</th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${categories}" var="category">
                    <tr>
                        <td class="col-md-2">${category.name}</td>
                        <td class="col-md-2">${category.description}</td>
                        <td class="col-md-2">${category.parentCategory.name}</td>
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