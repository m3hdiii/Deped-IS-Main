<%--
  Created by IntelliJ IDEA.
  User: mehdi
  Date: 7/7/17
  Time: 11:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Registration"/>
    <c:param name="description" value="Registeration page for new personnel and employees"/>
</c:import>
<html>
<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Register Emplooyee
                <small>Department of Education Division</small>
            </h3>
        </div>

        <div class="row">

            <div class="col-md-12">
                <form:form commandName="user" method="post" class="form-horizontal validator-form" role="form" enctype="multipart/form-data">
                    <div class="panel panel-default">

                        <div class="panel-heading text-center">User Information</div>
                        <div class="panel-body">

                            <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="text-red">*</span> Username</label>
                                <div class="col-sm-7">
                                    <form:input path="username" id="username" type="text" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="text-red">*</span> Email Address</label>
                                <div class="col-sm-7">
                                    <form:input path="emailAddress" name="email" id="emailAddress" type="emailAddress" class="form-control" placeholder="example@gmail.com"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="text-red">*</span> Password</label>
                                <div class="col-sm-7">
                                    <form:password path="password" id="password" name="password" class="form-control" placeholder="* * * * * * * * *"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="text-red">*</span> Re-type Password</label>
                                <div class="col-sm-7">
                                    <form:input path="repassword" name="confirmPassword" type="password" class="form-control" placeholder="* * * * * * * * *"/>
                                </div>
                            </div>

                            <hr class="dotted">
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="text-red">*</span> Full Name </label>
                                <div class="col-sm-7">
                                    <form:input path="firstName" id="firstName" type="text" class="form-control" placeholder="First Name"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label"> </label>
                                <div class="col-sm-7">
                                    <form:input path="middleName" id="middleName" type="text" class="form-control" placeholder="Middle Name"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label"> </label>
                                <div class="col-sm-7">
                                    <form:input path="lastName" id="lastName" type="text" class="form-control" placeholder="Last Name"/>
                                </div>
                            </div>

                            <hr class="dotted">
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="text-red">*</span> Address</label>
                                <div class="col-sm-7">
                                    <form:textarea path="address" id="address" type="text" class="form-control" placeholder="House #, Street, barangay"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-7 col-sm-offset-2">
                                    <select onchange="getCities(this)" class="form-control chosen-select">
                                        <option>---Choose a Country</option>
                                        <c:forEach items="${countries}" var="country">
                                            <option value="${country.countryCode}">${country.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-7 col-sm-offset-2">
                                    <form:select id="cities" path="cityOfBorn" class="form-control">
                                        <form:option value="">---Choose a City</form:option>
                                    </form:select>
                                </div>
                            </div>

                            <hr class="dotted">
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="text-red">*</span> Birthdate</label>
                                <div class="col-sm-3">
                                    <div class='input-group date' id="datepicker" >
                                        <form:input path="birthDate" id="birthDate" type='date' class="form-control"
                                                    data-date-format="YYYY-MM-DD" placeholder="YYYY-MM-DD"/>
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="text-red">*</span> Gender</label>
                                <div class="col-sm-3">
                                    <form:select path="gender" class="form-control">
                                        <form:option value="-" label="---Choose a Gender"/>
                                        <form:options items="${genders}"/>
                                    </form:select>
                                </div>
                            </div>

                            <hr class="dotted">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Mobile Number</label>
                                <div class="col-sm-3">
                                    <form:input path="phoneNo1" id="phoneNo1" type="number" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">Telephone Number</label>
                                <div class="col-sm-3">
                                    <form:input path="phoneNo2" id="phoneNo2" type="number" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">Website</label>
                                <div class="col-sm-7">
                                    <form:input path="website" id="website" type='text' class="form-control"/>
                                </div>
                            </div>

                            <hr class="dotted">
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="text-red">*</span> Postion</label>
                                <div class="col-sm-7">
                                    <form:select path="position" class="form-control">
                                        <form:option value="-" label="---Choose a Position"/>
                                        <form:options items="${positions}" />
                                    </form:select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="text-red">*</span> Department</label>
                                <div class="col-sm-7">
                                    <select onchange="getSections(this)" class="form-control">
                                        <option>--Choose a Department</option>
                                        <c:forEach items="${departments}" var="departmet">
                                            <option value="${departmet.departmentId}">${departmet.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="text-red">*</span> Section</label>
                                <div class="col-sm-7">
                                    <form:select id="section" path="section" class="form-control">
                                        <form:options items="${sections}"/>
                                    </form:select>
                                </div>
                            </div>

                            <hr class="dotted">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Referrer Info</label>
                                <div class="col-sm-7">
                                    <form:input path="referrerName" type="text" id="referrerName" class="form-control" placeholder="Full Name"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-7 col-sm-offset-2">
                                    <form:textarea path="referrerAddress" id="referrerAddress" class="form-control" placeholder="Full Address..."/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-7 col-sm-offset-2">
                                    <form:input path="referrerPhoneNo1" type="number" id="referrerPhoneNo1" class="form-control" placeholder="Phone Number"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-7 col-sm-offset-2">
                                    <form:input path="referrerPhoneNo2" type="number" id="referrerPhoneNo2" class="form-control" placeholder="Telephone Number"/>
                                </div>
                            </div>

                            <hr class="dotted">
                            <div class="form-group text-center">
                                <label>My Picture</label>
                                <div class="form-group">
                                    <div class="bg-gray col-md-2 col-sm-offset-5">
                                        <i class="fa fa-user fa-5x text-white" aria-hidden="true"></i>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-7 col-md-offset-2">
                                        <input type="file" name="userPic" id="profilePicture" class="form-control files"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <a href="#" class="btn btn-default pull-left"><i class="fa fa-chevron-left"></i> Back</a>
                            <div class="button-footer pull-right">
                                <input type="reset" class="btn btn-default" value="Clear"/>
                                <button type="submit" class="btn btn-primary">Register</button>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

    </div>
    <!-- Warper Ends Here (working area) -->


</section>


    <%--<p>--%>
    <%--Countries:--%>
    <%--<select onchange="getCities(this)">--%>
        <%--<option>--Select a country</option>--%>
        <%--<c:forEach items="${countries}" var="country">--%>
            <%--<option value="${country.countryCode}">${country.name}</option>--%>
        <%--</c:forEach>--%>
    <%--</select>--%>
<%--</p>--%>
<%--<div class="text-center"><h1>Employee Registration</h1></div>--%>

<%--<form:form commandName="user" method="post" class="form-horizontal" role="form" enctype="multipart/form-data">--%>

    <%--<form:input path="username" id="username" type="text" class="form-control typeahead"/>--%>

    <%--<form:input path="firstName" id="firstName" type="text" class="form-control typeahead"/>--%>

    <%--<form:input path="lastName" id="lastName" type="text" class="form-control typeahead" placeholder="Last Name"/>--%>

    <%--<form:input path="middleName" id="middleName" type="text" class="form-control typeahead"/>--%>

    <%--<form:input path="emailAddress" value="mehdi@me.com" id="emailAddress" type="text" class="form-control typeahead"/>--%>

    <%--<form:input path="phoneNo1" value="(074) 2460975" id="phoneNo1" type="text" class="form-control typeahead"/>--%>

    <%--<form:input path="phoneNo2" value="(074) 2460975" id="phoneNo2" type="text" class="form-control typeahead"/>--%>

    <%--<form:select path="gender">--%>
        <%--<form:option value="-" label="--Please Select"/>--%>
        <%--<form:options items="${genders}"/>--%>
    <%--</form:select>--%>

    <%--<form:input path="birthDate" value="1986-07-29" id="birthDate" type='date' class="form-control"--%>
                <%--data-date-format="YYYY-MM-DD" placeholder="YYYY-MM-DD"/>--%>

    <%--<form:password path="password" id="password" class="form-control tagsinput"/>--%>

    <%--<form:input path="repassword" class="form-control tagsinput" placeholder="* * * * * *"/>--%>


    <%--<form:select path="position">--%>
        <%--<form:option value="-" label="--Please Select"/>--%>
        <%--<form:options items="${positions}" />--%>
    <%--</form:select>--%>

    <%--<select onchange="getCities(this)">--%>
        <%--<option>--Select a country</option>--%>
        <%--<c:forEach items="${countries}" var="country">--%>
            <%--<option value="${country.countryCode}">${country.name}</option>--%>
        <%--</c:forEach>--%>
    <%--</select>--%>

    <%--<form:select id="cities" path="cityOfBorn">--%>
        <%--<form:option value="">--Please Select</form:option>--%>
    <%--</form:select>--%>

    <%--<select onchange="getSections(this)">--%>
        <%--<option>--Select a department</option>--%>
        <%--<c:forEach items="${departments}" var="departmet">--%>
            <%--<option value="${departmet.departmentId}">${departmet.name}</option>--%>
        <%--</c:forEach>--%>
    <%--</select>--%>

    <%--<form:select id="section" path="section">--%>
        <%--<form:options items="${sections}"/>--%>
    <%--</form:select>--%>


    <%--<form:textarea path="address" value="Tehran - Iran" id="address" class="form-control typeahead"/>--%>

    <%--<form:input path="website" value="www.your-domain.com" id="website" class="form-control typeahead"/>--%>

    <%--<input type="file" name="userPic" id="profilePicture" class="form-control file"/>--%>

    <%--<form:input path="referrerName" value="Morteza AfsariKashi" id="referrerName" class="form-control typeahead"/>--%>

    <%--<form:textarea path="referrerAddress" id="referrerAddress" cols="30" rows="10" value="Tehran Iran"--%>
                   <%--class="form-control typeahead" placeholder="Tehran Iran"/>--%>

    <%--<form:input path="referrerPhoneNo1" value="09335787555" id="referrerPhoneNo1" class="form-control typeahead"/>--%>

    <%--<form:input path="referrerPhoneNo2" value="09335787777" id="referrerPhoneNo2" class="form-control typeahead"/>--%>

    <%--<div class="form-group">--%>
        <%--<div class="col-lg-3 col-lg-offset-5">--%>
            <%--<button type="submit" class="btn btn-purple btn-block">Register Employee</button>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</form:form>--%>

<c:import url="../../includes/footer.jsp"/>
<script src="${resourceURL}/js/additional/signup.js" type="text/javascript"></script>

<c:url var="findCitiesURL" value="/city/list"/>
<c:url var="findSectionURL" value="/section/list"/>
<c:url var="findCountriesURL" value="/country/list"/>
<script type="text/javascript">
    function getSections(selectSt) {
        $.post("${findSectionURL}/" + selectSt.value, "", function (data, textStatus) {
//            alert(JSON.stringify(data));

            var html = '<option value="">--Please Select</option>';
            var len = data.length;
            for (var i = 0; i < len; i++) {
                html += '<option value="' + data[i].sectionId + '">'
                    + data[i].name + '</option>';
            }
            html += '</option>';

            $('#section').html(html);

        }, "json");
    }

    function getCities(selectSt) {
        $.post("${findCitiesURL}/" + selectSt.value, "", function (data, textStatus) {
//            alert(JSON.stringify(data));

            var html = '<option value="">--Please Select</option>';
            var len = data.length;
            for (var i = 0; i < len; i++) {
                html += '<option value="' + data[i].cityId + '">'
                    + data[i].name + '</option>';
            }
            html += '</option>';

            $('#cities').html(html);
        }, "json");
    }
</script>
    <c:import url="../../includes/footer.jsp"/>
</body>
</html>