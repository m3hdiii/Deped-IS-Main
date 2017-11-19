<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html lang="en">

<c:import url="../includes/head.jsp">
    <c:param name="title" value="Forbidden"/>
    <c:param name="description" value="Forbidden Page"/>
</c:import>
<body>

<div class="container">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4">
            <h3 class="text-center">Department of Education Baguio City Division</h3>
            <p class="text-center">Forbidden</p>
            <hr class="clean">

            <div>
                <ul class="list-group">
                    <li class="list-group-item list-group-item-warning text-danger"><span
                            class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;${failure}
                    </li>
                </ul>
            </div>
            <a href="${returnUrl}" class="btn btn-default btn-block" role="button">${returnName}</a>

        </div>
    </div>
</div>

<section class="navbar-fixed-bottom">
    <c:import url="../includes/footer.jsp"/>
</body>
</html>