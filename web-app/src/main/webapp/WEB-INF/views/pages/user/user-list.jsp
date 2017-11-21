<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Manage User"/>
    <c:param name="description" value="EDIT LATER PAGE"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header"><h1>Data Tables <small>Working controls</small></h1></div>



        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="text-center clean"><i class="fa fa-users"></i>  Users Account</h3>
            </div>
            <div class="panel-body">

                <table cellpadding="0" cellspacing="0" border="0" class="table table-hover" id="basic-datatable">
                    <thead>
                    <tr>
                        <th>User</th>
                        <th></th>
                        <th>Birthdate</th>
                        <th>Contact Number</th>
                        <th>Position</th>
                        <th>Section</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${user}" var="user">
                            <tr>
                                <c:choose>
                                    <c:when test="${not empty user.picUrl}">
                                        <td>
                                            <img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>
                                            <img src="../../${resourceURL}/images/avtar/user.png" class="img-circle" alt="User#1" width="50px" height="50px">
                                        </td>
                                    </c:otherwise>
                                </c:choose>

                                <%--<c:choose>--%>
                                    <%--<c:when test="${not empty user.picUrl}">--%>
                                        <%--<c:choose>--%>
                                            <%--<c:when test="${(user.accountStatus) eq ('EXPIRED')}">--%>
                                                <%--<td>--%>
                                                    <%--<div class="user-status invisibled pull-left">--%>
                                                        <%--<img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>--%>
                                                    <%--</div>--%>
                                                <%--</td>--%>
                                            <%--</c:when>--%>
                                            <%--<c:when test="${(user.accountStatus) eq ('LOCKED')}">--%>
                                                <%--<td>--%>
                                                    <%--<div class="user-status busy pull-left">--%>
                                                        <%--<img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>--%>
                                                    <%--</div>--%>
                                                <%--</td>--%>
                                            <%--</c:when>--%>
                                            <%--<c:when test="${(user.accountStatus) eq ('NOT_ACTIVE')}">--%>
                                                <%--<td>--%>
                                                    <%--<div class="user-status offline pull-left">--%>
                                                        <%--<img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>--%>
                                                    <%--</div>--%>
                                                <%--</td>--%>
                                            <%--</c:when>--%>
                                            <%--<c:otherwise>--%>
                                                <%--<td>--%>
                                                    <%--<div class="user-status online pull-left">--%>
                                                        <%--<img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>--%>
                                                    <%--</div>--%>
                                                <%--</td>--%>
                                            <%--</c:otherwise>--%>
                                        <%--</c:choose>--%>
                                    <%--</c:when>--%>
                                    <%--<c:otherwise>--%>
                                        <%--<c:when test="${(user.accountStatus) eq ('EXPIRED')}">--%>
                                            <%--<td>--%>
                                                <%--<div class="user-status invisibled pull-left">--%>
                                                    <%--<img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>--%>
                                                <%--</div>--%>
                                            <%--</td>--%>
                                        <%--</c:when>--%>
                                        <%--<c:when test="${(user.accountStatus) eq ('LOCKED')}">--%>
                                            <%--<td>--%>
                                                <%--<div class="user-status busy pull-left">--%>
                                                    <%--<img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>--%>
                                                <%--</div>--%>
                                            <%--</td>--%>
                                        <%--</c:when>--%>
                                        <%--<c:when test="${(user.accountStatus) eq ('NOT_ACTIVE')}">--%>
                                            <%--<td>--%>
                                                <%--<div class="user-status offline pull-left">--%>
                                                    <%--<img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>--%>
                                                <%--</div>--%>
                                            <%--</td>--%>
                                        <%--</c:when>--%>
                                        <%--<c:otherwise>--%>
                                            <%--<td>--%>
                                                <%--<div class="user-status online pull-left">--%>
                                                    <%--<img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>--%>
                                                <%--</div>--%>
                                            <%--</td>--%>
                                        <%--</c:otherwise>--%>
                                    <%--</c:otherwise>--%>
                                <%--</c:choose>--%>
                                <!-- <td>
                                   <div class="user-status busy pull-left">
                                       <img src="../../assets/images/avtar/user.png" class="img-circle" alt="User#1" width="50px" height="50px">
                                   </div>
                               </td> -->
                                <td>
                                    <div class="name">
                                            ${user.firstName} ${user.middleName} ${user.lastName}
                                    </div>
                                    <div class="email">
                                        <a href="#">${user.emailAddress}</a>
                                    </div>
                                </td>
                                <td>${user.birthDate}</td>
                                <td>
                                    <div class="fa phone1">
                                        <i class="fa-mobile"></i>
                                        <a href="#">
                                            <u>${user.phoneNo1}</u>
                                        </a>
                                    </div>
                                    <div class="fa phone2">
                                        <i class="fa-phone"></i>
                                        <a href="#">
                                            <u>${user.phoneNo2}</u>
                                        </a>
                                    </div>
                                </td>
                                <td>${user.position}</td>
                                <td>${user.section.name}</td>

                                <td>
                                    <a href="/user/${user.userId}" class="btn btn-primary"><i class="fa fa-eye"></i></a>
                                </td>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <td class="text-nowrap">
                                    <a href="/user/update/${user.userId}" class="btn btn-purple"><i class="fa fa-pencil"></i></a>
                                </td>
                                </sec:authorize>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
    <!-- Warper Ends Here (working area) -->





    <%--<div class="warper container-fluid">--%>

        <%--<div class="page-header">--%>
            <%--<h1>Manage Users--%>
                <%--<small>DepEd-Baguio City Division Office</small>--%>
            <%--</h1>--%>

        <%--</div>--%>


        <%--<div class="row item-body">--%>
            <%--<div class="panel no-padd">--%>
                <%--<div class="panel-heading">--%>
                    <%--<h3 class="text-center"><i class="fa fa-users"></i>  Users Account</h3>--%>
                <%--</div>--%>
                <%--<div class="panel-body">--%>
                    <%--<table class="table table-hover">--%>
                        <%--<thead>--%>
                        <%--<tr>--%>
                            <%--<th>User</th>--%>
                            <%--<th></th>--%>
                            <%--<th>Birthdate</th>--%>
                            <%--<th>Contact Number</th>--%>
                            <%--<th>Position</th>--%>
                            <%--<th>Section</th>--%>
                            <%--<th></th>--%>
                            <%--<th></th>--%>
                        <%--</tr>--%>
                        <%--</thead>--%>
                        <%--<tbody>--%>

                        <%--<c:forEach items="${user}" var="user">--%>
                            <%--<tr>--%>
                                <%--<c:choose>--%>
                                    <%--<c:when test="${not empty user.picUrl}">--%>
                                        <%--<c:choose>--%>
                                            <%--<c:when test="${(user.accountStatus) eq ('EXPIRED')}">--%>
                                                <%--<td>--%>
                                                   <%--<div class="user-status invisibled pull-left">--%>
                                                       <%--<img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>--%>
                                                   <%--</div>--%>
                                                <%--</td>--%>
                                            <%--</c:when>--%>
                                            <%--<c:when test="${(user.accountStatus) eq ('LOCKED')}">--%>
                                                <%--<td>--%>
                                                    <%--<div class="user-status busy pull-left">--%>
                                                        <%--<img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>--%>
                                                    <%--</div>--%>
                                                <%--</td>--%>
                                            <%--</c:when>--%>
                                            <%--<c:when test="${(user.accountStatus) eq ('NOT_ACTIVE')}">--%>
                                                <%--<td>--%>
                                                    <%--<div class="user-status offline pull-left">--%>
                                                        <%--<img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>--%>
                                                    <%--</div>--%>
                                                <%--</td>--%>
                                            <%--</c:when>--%>
                                            <%--<c:otherwise>--%>
                                                <%--<td>--%>
                                                    <%--<div class="user-status online pull-left">--%>
                                                        <%--<img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>--%>
                                                    <%--</div>--%>
                                                <%--</td>--%>
                                            <%--</c:otherwise>--%>
                                        <%--</c:choose>--%>


                                    <%--</c:when>--%>
                                    <%--<c:otherwise>--%>
                                        <%--<c:when test="${(user.accountStatus) eq ('EXPIRED')}">--%>
                                            <%--<td>--%>
                                                <%--<div class="user-status invisibled pull-left">--%>
                                                    <%--<img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>--%>
                                                <%--</div>--%>
                                            <%--</td>--%>
                                        <%--</c:when>--%>
                                        <%--<c:when test="${(user.accountStatus) eq ('LOCKED')}">--%>
                                            <%--<td>--%>
                                                <%--<div class="user-status busy pull-left">--%>
                                                    <%--<img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>--%>
                                                <%--</div>--%>
                                            <%--</td>--%>
                                        <%--</c:when>--%>
                                        <%--<c:when test="${(user.accountStatus) eq ('NOT_ACTIVE')}">--%>
                                            <%--<td>--%>
                                                <%--<div class="user-status offline pull-left">--%>
                                                    <%--<img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>--%>
                                                <%--</div>--%>
                                            <%--</td>--%>
                                        <%--</c:when>--%>
                                        <%--<c:otherwise>--%>
                                            <%--<td>--%>
                                                <%--<div class="user-status online pull-left">--%>
                                                    <%--<img src="${baseUrl}${user.picUrl}" class="img-circle" alt="user image" width="50px" height="50px"/>--%>
                                                <%--</div>--%>
                                            <%--</td>--%>
                                        <%--</c:otherwise>--%>

                                    <%--</c:otherwise>--%>
                                <%--</c:choose>--%>
                                 <%--<!-- <td>--%>
                                    <%--<div class="user-status busy pull-left">--%>
                                        <%--<img src="../../assets/images/avtar/user.png" class="img-circle" alt="User#1" width="50px" height="50px">--%>
                                    <%--</div>--%>
                                <%--</td> -->--%>
                                <%--<td>--%>
                                    <%--<div class="name">--%>
                                        <%--${user.firstName} ${user.middleName} ${user.lastName}--%>
                                    <%--</div>--%>
                                    <%--<div class="email">--%>
                                        <%--<a href="#">${user.emailAddress}</a>--%>
                                    <%--</div>--%>
                                <%--</td>--%>
                                <%--<td>${user.birthDate}</td>--%>
                                <%--<td>--%>
                                    <%--<div class="fa phone1">--%>
                                        <%--<i class="fa-mobile"></i>--%>
                                        <%--<a href="#">--%>
                                            <%--<u>${user.phoneNo1}</u>--%>
                                        <%--</a>--%>
                                    <%--</div>--%>
                                    <%--<div class="fa phone2">--%>
                                        <%--<i class="fa-phone"></i>--%>
                                        <%--<a href="#">--%>
                                            <%--<u>${user.phoneNo2}</u>--%>
                                        <%--</a>--%>
                                    <%--</div>--%>
                                <%--</td>--%>
                                <%--<td>${user.position}</td>--%>
                                <%--<td>${user.section.name}</td>--%>

                                <%--<td>--%>
                                    <%--<a href="#" class="btn btn-primary" data-toggle="modal" data-target="#view-user-modal"><i class="fa fa-eye"></i></a>--%>
                                <%--</td>--%>
                                <%--<td class="text-nowrap">--%>
                                    <%--<a href="#" class="btn btn-purple"><i class="fa fa-pencil"></i></a>--%>
                                <%--</td>--%>
                            <%--</tr>--%>
                        <%--</c:forEach>--%>

                        <%--</tbody>--%>
                    <%--</table>--%>
                    <%--<hr>--%>
                    <%--<div class="user-table-pagination">--%>

                        <%--<nav>--%>
                            <%--<ul class="pagination pagination-sm">--%>

                                <%--<li class="page-item">--%>
                                    <%--<a class="page-link" href="#" aria-label="Previous">--%>
                                        <%--<span aria-hidden="true">&laquo;</span>--%>
                                        <%--<span class="sr-only">Previous</span>--%>
                                    <%--</a>--%>
                                <%--</li>--%>
                                <%--<li class="page-item"><a class="page-link" href="#">1</a></li>--%>
                                <%--<li class="page-item"><a class="page-link" href="#">2</a></li>--%>
                                <%--<li class="page-item"><a class="page-link" href="#">3</a></li>--%>
                                <%--<li class="page-item">--%>
                                    <%--<a class="page-link" href="#" aria-label="Next">--%>
                                        <%--<span aria-hidden="true">&raquo;</span>--%>
                                        <%--<span class="sr-only">Next</span>--%>
                                    <%--</a>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                        <%--</nav>--%>
                    <%--</div>--%>


                <%--</div>--%>


            <%--</div>--%>
        <%--</div>--%>
    <%--</div> <!-- Warper Ends Here (working area) -->--%>
        <c:import url="../../includes/footer.jsp"/>
</body>
</html>