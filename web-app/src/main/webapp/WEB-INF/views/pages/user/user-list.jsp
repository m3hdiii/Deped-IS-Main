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

        <div class="page-header">
            <h1>Manage Users
                <small>DepEd-Baguio City Division Office</small>
            </h1>
        </div>

        <div class="btn-group visible-lg-inline-block">
            <a href="/user/create" class="btn btn-default tooltip-btn" data-toggle="tooltip" data-placement="top"
               title="Add User"><i class="fa fa-plus"></i></a>
        </div>
        <hr class="clean">


        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="text-center clean"><i class="fa fa-users"></i> Users Account</h3>
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
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${user}" var="user">
                        <tr>
                            <td>

                                <c:choose>
                                    <c:when test="${not empty user.picUrl}">
                                        <img src="${sessionScope.baseUrl}${user.picUrl}" class="img-circle"
                                             alt="..." width="50px" height="50px">
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${user.gender eq 'FEMALE'}">
                                            <img src="${resourceURL}/images/avtar/user_female.png"
                                                 class="img-circle"
                                                 alt="..." width="50px" height="50px">
                                        </c:if>

                                        <c:if test="${user.gender eq 'MALE'}">
                                            <img src="${resourceURL}/images/avtar/user_male.png" class="img-circle"
                                                 alt="..." width="50px" height="50px">
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <div class="name">
                                        ${user.firstName} ${user.lastName}
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
                                <div class="btn-group visible-lg-inline-block">
                                    <a href="<c:url value='/user/${user.username}' />"
                                       class="btn btn-primary tooltip-btn" data-toggle="tooltip" data-placement="top"
                                       title="View User"><i class="fa fa-eye"></i></a>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <a href="<c:url value='/user/update/${user.username}'/>"
                                           class="btn btn-purple tooltip-btn"
                                           data-toggle="tooltip" data-placement="top" title="Edit User"><i
                                                class="fa fa-pencil"></i></a>
                                    </sec:authorize>
                                </div>
                            </td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
    <!-- Warper Ends Here (working area) -->
    <c:import url="../../includes/footer.jsp"/>
</body>
</html>