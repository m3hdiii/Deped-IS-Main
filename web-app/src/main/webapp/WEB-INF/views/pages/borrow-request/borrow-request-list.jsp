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

    <div class="row">
        <div class="col col-lg-3"/>
        <div class="col col-lg-3">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Contact Number</th>
                    <th>Contact Number 2</th>
                    <th>Central Office Address</th>
                    <th>Service Center Address</th>
                    <th>Creation Date</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${borrowRequests}" var="borrowRequest">
                    <tr>
                        <td>${borrowRequest.borrowRequestId}</td>
                        <td>${borrowRequest.description}</td>
                        <td>${borrowRequest.requestDate}</td>
                        <td>${borrowRequest.approvalDate}</td>
                        <td>${borrowRequest.releaseDate}</td>
                        <td>${borrowRequest.borrowStatus}</td>
                        <td>${borrowRequest.disapprovalMessage}</td>
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






