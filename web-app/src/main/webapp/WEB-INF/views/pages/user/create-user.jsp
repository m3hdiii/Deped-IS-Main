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
<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">

<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Registration"/>
    <c:param name="description" value="Registeration page for new personnel and employees"/>
</c:import>

<body>

<p>
    Countries:
    <select onchange="getCities(this)">
        <option>--Select a country</option>
        <c:forEach items="${countries}" var="country">
            <option value="${country.countryCode}">${country.name}</option>
        </c:forEach>
    </select>
</p>
<div class="text-center"><h1>Employee Registration</h1></div>

<form:form commandName="user" method="post" class="form-horizontal" role="form" enctype="multipart/form-data">

    <form:input path="username" id="username" type="text" class="form-control typeahead"/>

    <form:input path="firstName" id="firstName" type="text" class="form-control typeahead"/>

    <form:input path="lastName" id="lastName" type="text" class="form-control typeahead" placeholder="Last Name"/>

    <form:input path="middleName" id="middleName" type="text" class="form-control typeahead"/>

    <form:input path="emailAddress" value="mehdi@me.com" id="emailAddress" type="text" class="form-control typeahead"/>

    <form:input path="phoneNo1" value="(074) 2460975" id="phoneNo1" type="text" class="form-control typeahead"/>

    <form:input path="phoneNo2" value="(074) 2460975" id="phoneNo2" type="text" class="form-control typeahead"/>

    <form:select path="gender">
        <form:option value="-" label="--Please Select"/>
        <form:options items="${genders}"/>
    </form:select>

    <form:input path="birthDate" value="1986-07-29" id="birthDate" type='date' class="form-control"
                data-date-format="YYYY-MM-DD" placeholder="YYYY-MM-DD"/>

    <form:password path="password" id="password" class="form-control tagsinput"/>

    <form:input path="repassword" class="form-control tagsinput" placeholder="* * * * * *"/>


    <form:select path="position">
        <form:option value="-" label="--Please Select"/>
        <form:options items="${positions}" />
    </form:select>

    <select onchange="getCities(this)">
        <option>--Select a country</option>
        <c:forEach items="${countries}" var="country">
            <option value="${country.countryCode}">${country.name}</option>
        </c:forEach>
    </select>

    <form:select id="cities" path="cityOfBorn">
        <form:option value="">--Please Select</form:option>
    </form:select>

    <select onchange="getSections(this)">
        <option>--Select a department</option>
        <c:forEach items="${departments}" var="departmet">
            <option value="${departmet.departmentId}">${departmet.name}</option>
        </c:forEach>
    </select>

    <form:select id="section" path="section">
        <form:options items="${sections}"/>
    </form:select>


    <form:textarea path="address" value="Tehran - Iran" id="address" class="form-control typeahead"/>

    <form:input path="website" value="www.your-domain.com" id="website" class="form-control typeahead"/>

    <input type="file" name="userPic" id="profilePicture" class="form-control file"/>

    <form:input path="referrerName" value="Morteza AfsariKashi" id="referrerName" class="form-control typeahead"/>

    <form:textarea path="referrerAddress" id="referrerAddress" cols="30" rows="10" value="Tehran Iran"
                   class="form-control typeahead" placeholder="Tehran Iran"/>

    <form:input path="referrerPhoneNo1" value="09335787555" id="referrerPhoneNo1" class="form-control typeahead"/>

    <form:input path="referrerPhoneNo2" value="09335787777" id="referrerPhoneNo2" class="form-control typeahead"/>

    <div class="form-group">
        <div class="col-lg-3 col-lg-offset-5">
            <button type="submit" class="btn btn-purple btn-block">Register Employee</button>
        </div>
    </div>
</form:form>

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
</body>
</html>