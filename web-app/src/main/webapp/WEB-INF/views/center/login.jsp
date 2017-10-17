<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"  %>
<!DOCTYPE html>
<html lang="en">

<c:import url="../includes/head.jsp">
    <c:param name="title" value="Login"/>
    <c:param name="description" value="Login page for personnel"/>
</c:import>
<body>


<div class="container">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4">
            <h3 class="text-center">Department of Education Baguio City Division</h3>
            <p class="text-center">Inventory System Login</p>
            <hr class="clean">

            <c:url value="/performLogin" var="loginUrl" />

            <c:if test="${param.error != null}">
            <p style="color: red;">
                invalid username/password !
            </p>
            </c:if>
            <form action="${loginUrl}" method="post" role="form">

                <div class="form-group input-group">
                    <span class="input-group-addon"><i class="fa fa-envelope"></i></span>
                    <input id="login-username" name="username" type="text" class="form-control" placeholder="Username"/>
                </div>
                <div class="form-group input-group">
                    <span class="input-group-addon"><i class="fa fa-key"></i></span>
                    <input id="login-password" type="password"  name="password" class="form-control" placeholder="Password"/>
                </div>
                <div class="form-group">
                    <label class="cr-styled">
                        <input type="checkbox" ng-model="todo.done">
                        <i class="fa"></i>
                    </label>
                    Remember me?
                </div>
                <sec:csrfInput/>
                <button type="submit" class="btn btn-purple btn-block">Login</button>
            </form>
            <hr>

            <p class="text-center text-gray">Did you forget your password?</p>
            <button type="submit" class="btn btn-default btn-block">Contact Admin</button>
        </div>
    </div>
</div>

<section class="navbar-fixed-bottom">
    <c:import url="../includes/footer.jsp" />
</section>
<script src="${resourceURL}/js/additional/login.js" type="text/javascript"></script>
</body>
</html>