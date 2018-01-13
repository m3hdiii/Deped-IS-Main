<%--
  Created by IntelliJ IDEA.
  User: mehdi
  Date: 10/22/17
  Time: 10:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%
    //System.out.println(getServletContext().getRealPath("/"));
%>
<c:url value="/public" var="resourceURL" scope="request"/>
<c:url value="/" var="routePath" scope="request"/>

<html>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Checkout Request"/>
    <c:param name="description" value="Checkout Request Page"/>
</c:import>

<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>


    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Checkout Request&nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small>
            </h3>
        </div>

        <c:set var="requestName" value="requestSessionNo${requestId}"/>
        <c:set var="relatedRequest" value="${sessionScope[requestName]}"/>

        <div class="row item-body">

            <c:set var="requestIdValue" value="${relatedRequest.requestId}"/>
            <c:set var="basketName" value="requestDetailsMap-RequestNo${requestIdValue}"/>
            <c:set var="basketMap" value="${sessionScope[basketName]}"/>

            <div class="panel panel-default">
                <div class="panel-heading">
                    Item Requested
                </div>
                <c:choose>
                    <c:when test="${not empty sessionScope[basketName]}">
                        <div class="panel-body">

                        <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Image</th>
                            <th>Item Name</th>
                            <th>Available Quantity</th>
                            <th>Quantity</th>
                            <th>Item Type</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <form:form commandName="requestDetailsForm" method="post">
                            <c:forEach items="${basketMap}" var="entry" varStatus="loop">
                                <c:set var="strKey" value="${entry.key}"/>
                                <c:set var="requestDet" value="${entry.value}"/>

                                <tr>
                                    <td>${loop.index + 1}</td>
                                    <c:choose>
                                        <c:when test="${not empty requestDet.item.picName}">
                                            <td><img src="${baseUrl}${requestDet.item.picName}" alt="item image"
                                                     width="70" height="50"/></td>
                                        </c:when>
                                        <c:otherwise>
                                            <td><img src="${resourceURL}/images/shared-images/no-item.png"
                                                     alt="item image" width="70" height="50"/>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>

                                    <td>${requestDet.item.name}</td>
                                    <td>${requestDet.item.quantity}</td>
                                    <td>
                                        <form:input type="number" path="map['${strKey}'].requestQuantity"
                                                    value="${requestDet.requestQuantity}"
                                                    max="${requestDet.item.quantity}"/>
                                    </td>
                                    <!--
                                    <td>
                                        <div class="input-group up-down-select">
                                            <span class="btn-default btn-up-down fa-border down"><i class="glyphicon glyphicon-minus up-down-icon"></i></span>
                                            <select class="item-quantity">
                                                <option selected>1</option>
                                                <option>2</option>
                                                <option>3</option>
                                                <option>4</option>
                                                <option>5</option>
                                            </select>
                                            <span class="btn-default btn-up-down fa-border up"><i class="glyphicon glyphicon-plus up-down-icon"></i></span>
                                        </div>

                                    </td> -->
                                    <form:hidden path="map['${strKey}'].request"
                                                 value="${relatedRequest.requestId}"/>
                                    <form:hidden path="map['${strKey}'].item" value="${requestDet.item.name}"/>
                                    <form:hidden path="map['${strKey}'].requestDetailsID.itemName"
                                                 value="${requestDet.item.name}"/>
                                    <form:hidden path="map['${strKey}'].requestDetailsID.requestId"
                                                 value="${relatedRequest.requestId}"/>

                                    <td>${requestDet.item.itemType}</td>

                                    <td>
                                        <a href="#" class="text-danger" aria-label="Close"><i
                                                class="fa fa-trash"> </i></a>
                                        <!-- <button type="button" class="close" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button> -->
                                    </td>
                                </tr>


                            </c:forEach>
                            </tbody>
                            </table>
                            </div>
                            <div class=" modal-footer ">
                                <button name="actionParam" value="UPDATE_ALL" class="pull-left btn btn-link"
                                        data-toggle="tooltip" title="Update the data and Request more"><i
                                        class="fa fa-arrow-left"></i> <strong>Request
                                    more</strong></button>

                                <button name="actionParam" value="DELETE_ALL" class="btn btn-danger"
                                        data-toggle="tooltip"
                                        title="Delete the list and re-request">Delete All
                                </button>
                                <button name="actionParam" value="SAVE_ALL" class="btn btn-primary"
                                        data-toggle="tooltip"
                                        title="Save the list">Save
                                </button>
                                <button name="actionParam" value="REQUEST_ALL" class="btn btn-success"
                                        data-toggle="tooltip"
                                        title="Send the Request">Send Request
                                </button>
                            </div>
                        </form:form>
                    </c:when>
                    <c:otherwise>
                        <div class="panel-body">
                            <h1 class="text-center padd-t-lg">No Request Available</h1>
                        </div>
                        <div class=" modal-footer ">
                            <button name="actionParam" class="btn btn-danger disabled">Checkout</button>
                        </div>
                    </c:otherwise>

                </c:choose>


            </div>

        </div>

    </div><!-- Warper Ends Here (working area) -->

    <!-- Confirmation to Delete -->
    <div class="modal fade" id="deleteCondition" role="dialog">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4>Confirmation</h4>
                </div>
                <div class="modal-body">
                    <p>Do you really want to delete this item(s)?</p>
                </div>
                <div class="modal-footer text-center">
                    <button type="button" class="btn btn-default btn-sm" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-danger btn-sm" data-dismiss="modal">Delete</button>
                </div>
            </div>
        </div>
    </div>

            <c:import url="../../includes/footer.jsp"/>

</body>
</html>