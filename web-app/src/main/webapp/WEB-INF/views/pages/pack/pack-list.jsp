<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Pack List"/>
    <c:param name="description" value="Pack List Page"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="page-header">
        <h3> Pack List </h3>
    </div>

    <div class="row">
        <div class="col col-lg-3"/>
        <div class="col col-lg-9">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="col-md-5">Name</th>
                    <th class="col-md-5">Description</th>
                    <th class="col-md-5">Associated Item</th>
                    <th class="col-md-5">Edit Section</th>
                    <th class="col-md-5">Remove Section</th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${packs}" var="pack">
                    <tr>
                        <td class="col-md-2">${pack.name}</td>
                        <td class="col-md-2">${pack.description}</td>
                        <td class="col-md-2">${pack.item.name}</td>
                        <td class="col-md-2"><a href="/pack/update/${pack.packId}"><img src="${resourceURL}/images/edit.png"
                                                                       width="16"/></a></td>
                        <td class="col-md-2"><img src="${resourceURL}/images/delete.png" width="16"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>