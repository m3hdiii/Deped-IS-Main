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
    <c:param name="title" value="Password Recovery"/>
    <c:param name="description" value="Forgot Password Page"/>
</c:import>
<body>

<div class="container">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4">
            <h3 class="text-center">Department of Education Baguio City Division</h3>
            <p class="text-center">Forgot Password</p>
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
                    <span class="input-group-addon"><i class="fa fa-envelope"></i></span>
                    <form:input id="login-email" path="emailAddress" class="form-control"
                                placeholder="please put your email address here"/>
                </div>
                <sec:csrfInput/>
                <button class="btn btn-purple btn-block">Send Me Recovery Email</button>
            </form:form>
        </div>
    </div>
</div>


    <c:import url="../includes/footer.jsp"/>
</body>
</html>