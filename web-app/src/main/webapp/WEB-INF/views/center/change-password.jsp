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
    <c:param name="title" value="Change Password"/>
    <c:param name="description" value="Change Password Page"/>
</c:import>
<body>

<div class="container">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4">
            <h3 class="text-center">Department of Education Baguio City Division</h3>
            <p class="text-center">Change Password</p>
            <hr class="clean">

            <c:if test="${success != null}">
                <p style="color: green;">
                        ${success}
                </p>
            </c:if>

            <c:set var="errors"
                   value="${requestScope['org.springframework.validation.BindingResult.user'].allErrors}"/>
            <c:if test="${not empty errors}">
                <div>
                    <ul class="list-group">
                        <c:forEach items="${errors}" var="error" varStatus="loop">
                            <li class="list-group-item list-group-item-warning text-danger"><span
                                    class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;${error.defaultMessage}
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>


            <form:form commandName="user" method="post" role="form">

                <div class="form-group input-group">
                    <span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
                    <form:password id="password" path="password" class="form-control"
                                   placeholder="Put your new password here"/>
                </div>

                <div class="form-group input-group">
                    <span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
                    <form:password id="password" path="repassword" class="form-control"
                                   placeholder="repeat it here"/>
                </div>

                <sec:csrfInput/>
                <button class="btn btn-purple btn-block">Reset My Password</button>
            </form:form>

            <c:if test="${success != null}">
                <a href="/" class="btn btn-default btn-block" role="button">Login with your new password</a>
            </c:if>
        </div>
    </div>
</div>

<section class="navbar-fixed-bottom">
    <c:import url="../includes/footer.jsp"/>
</body>
</html>