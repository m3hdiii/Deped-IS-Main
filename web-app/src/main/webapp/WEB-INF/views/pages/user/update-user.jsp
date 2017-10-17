<%--
  Created by IntelliJ IDEA.
  User: Kieth
  Date: 9/23/2017
  Time: 12:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Edit Profile"/>
    <c:param name="description" value="Edit Profile Page"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="page-header">
        <h3> Edit Profile </h3>
    </div>

    <div class="row">

        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <form class="form-horizontal" role="form">

                        <div class="image-group">
                            <img src="assets/images/avtar/user2.png" height="128" width="128"/>
                            <br>
                            <button type="button"> Upload Image </button>
                        </div>

                        <div class="panel panel-default">
                            <div class="panel-heading"> User Account Information </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> Username: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> Current Password: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> New Password: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> Confirm New Password: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="panel panel-default">
                            <div class="panel-heading"> Personal Information </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> First Name: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> Middle Name: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> Last Name: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> E-mail Address: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> Phone Number 1: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> Phone Number 2: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> Address: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> Website: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> Referrer Name: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> Referrer Address: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> Referrer Phone Number 1: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> Referrer Phone Number 2: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> Years of Experience: </label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control typeahead" placeholder="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> Birthdate: </label>
                                    <div class="col-sm-3">
                                        <div class='input-group date' id="datepicker" >
                                            <input type='date' class="form-control" data-date-format="YYYY/MM/DD" />
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"> Gender: </label>
                                    <div class="col-sm-7">
                                        <div class="switch-button showcase-switch-button">
                                            <input id="switch-button-6" name="switch-radio" type="radio">
                                            <label for="switch-button-6">Male</label>
                                        </div>
                                        <div class="switch-button showcase-switch-button">
                                            <input id="switch-button-7" checked name="switch-radio" type="radio">
                                            <label for="switch-button-7">Female</label>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>

                        <div class="col-lg-4 col-lg-offset-4">
                            <button type="submit" class="btn btn-purple btn-block"> Confirm Changes </button>
                        </div>

                    </form>
                </div>
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