<%--
  Created by IntelliJ IDEA.
  User: kevin
  Date: 20/09/2017
  Time: 2:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">

<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Profile"/>
    <c:param name="description" value="Profile page for personnel"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>


    <div class="warper container-fluid">

        <div class="page-header">
            <h1>Profile
                <small>DepEd-Baguio City Division Office</small>
            </h1>
        </div>


        <div class="row profile-body">
            <div class="col-md-12 col-sm-6">
                <div class="prof-image-container">
                    <img src="${resourceURL}/images/avtar/user.png" class="img-circle prof-image" alt="...">
                </div>
                <div class="panel panel-default clearfix rounded icon-bar border-radius-20">
                    <div class="panel-container">
                        <div class="profile-option-container text-right">
                            <img src ="${resourceURL}/images/svg/ic_more_horiz_black_24px.svg"/>
                        </div>
                        <section class="user-information">
                            <div class="text-center">
                                <h4 class="user-name">${userInfo.firstName} ${userInfo.middleName} ${userInfo.lastName}</h4>
                                <a class="email-address text-center col-md-12" href="#"><u>${userInfo.emailAddress}</u></a>
                            </div>
                            <div class="container user-information-container">
                                <div id="prof-birthday">
                                    <label>Birthday:</label>
                                    ${userInfo.birthDate}
                                </div>
                                <hr class="dotted">

                                <div id="prof-gender">
                                    <label>Gender:</label>
                                    ${userInfo.gender}
                                </div>
                                <hr class="dotted">

                                <div id="prof-phon1">
                                    <label>Phone Number 1:</label>
                                    ${userInfo.phoneNo1}
                                </div>
                                <hr class="dotted">

                                <div id="prof-phon2">
                                    <label>Phone Number 2:</label>
                                    ${userInfo.phoneNo2}
                                </div>
                                <hr class="dotted">

                                <div id="prof-Address">
                                    <label>address:</label>
                                    ${userInfo.address}
                                </div>
                                <hr class="dotted">
                            </div>
                        </section>

                        <div class="button-group col-md-12 text-center">
                            <button class="btn btn-primary btn-md btn-purple" data-toggle="modal" data-target="#change-pass-modal" type="button">Change Password</button>
                            <a class="btn btn-primary btn-md btn-purple" href="/user/update/${userId}">Update Information</a>
                        </div>

                    </div>
                </div>
            </div>



        </div>
    </div>

    </div>
    <!-- Warper Ends Here (working area) -->

    <c:import url="../../modals/cart.jsp"/>
    <c:import url="../../modals/changePassword.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>