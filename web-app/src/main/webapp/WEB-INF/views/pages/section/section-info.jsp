<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Edit Section"/>
    <c:param name="description" value="This page allows the admin to edit an existing section information from the list of sections."/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel-heading"><h1>Edit Section</h1></div>
            <div class="panel panel-defualt">
                <table class="table no-margn">
                    <tbody>
                        <tr>
                            <div class="row">
                                <div class="col-sm-4">
                                    <p>Name: ${sectionInfo.name}</p>
                                </div>
                            </div>
                        </tr>
                        <tr>
                            <div class="row">
                                <div class="col-sm-4">
                                    <p>Description: ${sectionInfo.description}</p>
                                </div>
                            </div>
                        </tr>
                        <tr>
                            <div class="row">
                                <div class="col-sm-4">
                                    <p>Department: ${sectionInfo.department.name}</p>
                                </div>
                            </div>
                        </tr>
                        <tr>
                            <div>
                                <div class="col-sm-2">
                                    <a href="/section/update/${sectionId}" class="btn btn-success btn-block">update the Section</a>
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