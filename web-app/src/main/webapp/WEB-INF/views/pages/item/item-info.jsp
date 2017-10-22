<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="EDIT LATER"/>
    <c:param name="description" value="EDIT LATER PAGE"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="page-header">
        <h3> Item Information </h3>
    </div>

    <c:if test="${not empty itemInfo.picName}">
        <div class="row col-md-12">
            <p class="text-center"><img width="400" src="${baseUrl}${itemInfo.picName}" alt="item image"/></p>
        </div>
    </c:if>


    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="col-md-11">
                        <p><label class="col-sm-2"> Name: </label> ${itemInfo.name}</p>
                    </div>
                    <div class="col-md-11">
                        <p><label class="col-sm-2"> Description: </label> ${itemInfo.description}</p>
                    </div>
                    <div class="col-md-11">
                        <p><label class="col-sm-2"> Item Type: </label> ${itemInfo.itemType}</p>
                    </div>
                    <div class="col-md-11">
                        <p><label class="col-sm-2"> Function Type: </label> ${itemInfo.functionType}</p>
                    </div>
                    <div class="col-md-11">
                        <p><label class="col-sm-2"> Thershold: </label> ${itemInfo.threshold}</p>
                    </div>
                    <div class="col-md-11">
                        <p><label class="col-sm-2"> Quantity: </label>
                            <c:choose>
                                <c:when test="${not empty itemInfo.quantity}">
                                    ${itemInfo.quantity}
                                </c:when>
                                <c:otherwise>
                                    not yet populated
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>

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