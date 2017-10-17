<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">

<c:import url="includes/head.jsp" />

<c:import url="includes/head.jsp">
    <c:param name="title" value="404"/>
    <c:param name="description" value="Page not found"/>
</c:import>
<body>

<div class="container">

    <div class="row">
        <div class="col-lg-6 col-lg-offset-3">
            <h1 class="text-center" style="font-size:150px; font-weight:800;">404</h1>
            <p class="text-center lead">The Page you requested does not exist</p>
            <hr class="">
        </div>
    </div>

    <div class="row">
        <div class="col-lg-4 col-lg-offset-4">

            <form>
                <div class="input-group">
                    <input type="search" class="form-control" placeholder="Search Here...">
                    <span class="input-group-btn">
                <button type="submit" class="btn btn-purple">Search</button>
              </span>
                </div>
            </form>


        </div>
    </div>

    <div class="row">
        <div class="col-lg-6 col-lg-offset-3 text-center ">
            <hr class="">
            <p class="text-gray">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin iaculis quam sit amet nisi dictum, in iaculis dolor lobortis. Aenean placerat purus non faucibus aliquam.</p>
            <button type="submit" class="btn btn-default">Go Home</button>
        </div>
    </div>

</div>

<c:import url="includes/footer.jsp" />
</body>
</html>