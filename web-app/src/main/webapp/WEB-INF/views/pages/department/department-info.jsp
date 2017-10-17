<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Department Information"/>
    <c:param name="description" value="Contains an information about a specific chosen department from the list"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel-heading"><h1>Department Info</h1></div>
            <div class="panel panel-default">
                <table class="table no-margn">
                   <tbody>
                   <tr>
                       <div class="row">
                           <div class="col-sm-4">
                               <p>Name: ${departmentInfo.name}</p>
                           </div>
                       </div>
                   </tr>
                   <tr>
                       <div class="row">
                           <div class="col-sm-4">
                               <p>Description: ${departmentInfo.description}</p>
                           </div>
                       </div>
                   </tr>
                   <tr>
                       <div class="row">
                           <div class="col-sm-2">
                               <p>Department Head: ${departmentInfo.departmentHead}</p>
                           </div>
                       </div>
                   </tr>
                   <tr>
                       <div class="row">
                           <div class="col-sm-4">
                               <a href="/department/update/${departmentId}" class="btn btn-success btn-block">Edit Department Info</a>
                           </div>
                       </div>
                   </tr>
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