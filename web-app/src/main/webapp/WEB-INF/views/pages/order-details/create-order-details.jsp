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
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Item Registration"/>
    <c:param name="description" value="Item Registration Page"/>
</c:import>
<html>
<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Order Request <small>&nbsp;for Goods, Semi-Expendable and Equipment</small></h3>
        </div>

        <c:set var="orderName" value="orderSessionNo${orderId}"/>
        <c:set var="relatedOrder" value="${sessionScope[orderName]}"/>

        <div class="row">
            <p>Order Number: ${relatedOrder.orderId}</p>
            <p>Order
                By: ${relatedOrder.user.firstName}&nbsp;${relatedOrder.user.middleName}&nbsp;${relatedOrder.user.lastName}</p>
            <p>Schedule For: ${relatedOrder.orderSchedule}</p>
            <p>Budget: ${relatedOrder.budgetAmount}</p>
        </div>

        <c:set var="orderIdValue" value="${relatedOrder.orderId}"/>
        <c:set var="basketName" value="orderDetailsMap-OrderNo${orderIdValue}"/>

        <div class="panel panel-default">
            <div class="panel-heading">Basic DataTable</div>
            <div class="panel-body">

                <table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered" id="basic-datatable">
                    <thead>
                    <tr>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Available QTY</th>
                        <th>Package Capacity</th>
                        <th>Packages</th>
                        <th>No. of Packs</th>
                        <th>Item QTY</th>
                        <th>Category</th>
                        <th>Item Type</th>
                        <th>Unit Price</th>
                        <th>Suppliers</th>
                        <th>Add To Order</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${itemList}" var="item" varStatus="loop">
                        <form:form commandName="orderDetail" method="post">
                            <tr>
                                <c:choose>
                                    <c:when test="${not empty item.picName}">
                                        <td><img width="64" src="${baseUrl}${item.picName}" alt="item image"/></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><img width="64" src="${resourceURL}/images/shared-images/no-item.png"
                                                 alt="item image"/>
                                        </td>
                                    </c:otherwise>
                                </c:choose>

                                <td>${item.name}</td>

                                <td>${item.quantity}</td>

                                <td>
                                    <div class="col-md-2">
                                        <form:input type="number" min="0" path="packCapacity"/>
                                    </div>
                                </td>

                                <td>
                                    <form:select path="pack">
                                        <form:options items="${packs}" itemLabel="name" itemValue="packId"/>
                                    </form:select>
                                </td>

                                <td>
                                    <form:input type="number" min="0" path="noOfPacks"/>
                                </td>

                                <td>
                                    <form:input type="number" min="0" path="totalQuantityRequestNo"/>
                                </td>


                                <td>
                                    <form:select path="category">
                                        <form:options items="${categories}" itemLabel="name" itemValue="categoryId"/>
                                    </form:select>
                                </td>

                                <td>${item.itemType}</td>

                                <td>
                                    <form:input path="unitPrice"/>
                                </td>

                                <td>
                                    <form:select path="supplier">
                                        <form:options items="${suppliers}" itemLabel="name" itemValue="supplierId"/>
                                    </form:select>
                                </td>

                                <form:hidden path="order" value="${relatedOrder.orderId}"/>
                                <form:hidden path="item" value="${item.itemId}"/>

                                <td>
                                    <button class="btn btn-success btn-block">Add To Order</button>
                                </td>
                            </tr>
                        </form:form>
                    </c:forEach>

                    <%--<tr class="odd gradeX">--%>
                        <%--<td>Trident</td>--%>
                        <%--<td>Internet--%>
                            <%--Explorer 4.0</td>--%>
                        <%--<td>Win 95+</td>--%>
                        <%--<td class="center"> 4</td>--%>
                        <%--<td class="center">X</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="even gradeC">--%>
                        <%--<td>Trident</td>--%>
                        <%--<td>Internet--%>
                            <%--Explorer 5.0</td>--%>
                        <%--<td>Win 95+</td>--%>
                        <%--<td class="center">5</td>--%>
                        <%--<td class="center">C</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="odd gradeA">--%>
                        <%--<td>Trident</td>--%>
                        <%--<td>Internet--%>
                            <%--Explorer 5.5</td>--%>
                        <%--<td>Win 95+</td>--%>
                        <%--<td class="center">5.5</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="even gradeA">--%>
                        <%--<td>Trident</td>--%>
                        <%--<td>Internet--%>
                            <%--Explorer 6</td>--%>
                        <%--<td>Win 98+</td>--%>
                        <%--<td class="center">6</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="odd gradeA">--%>
                        <%--<td>Trident</td>--%>
                        <%--<td>Internet Explorer 7</td>--%>
                        <%--<td>Win XP SP2+</td>--%>
                        <%--<td class="center">7</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="even gradeA">--%>
                        <%--<td>Trident</td>--%>
                        <%--<td>AOL browser (AOL desktop)</td>--%>
                        <%--<td>Win XP</td>--%>
                        <%--<td class="center">6</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Firefox 1.0</td>--%>
                        <%--<td>Win 98+ / OSX.2+</td>--%>
                        <%--<td class="center">1.7</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Firefox 1.5</td>--%>
                        <%--<td>Win 98+ / OSX.2+</td>--%>
                        <%--<td class="center">1.8</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Firefox 2.0</td>--%>
                        <%--<td>Win 98+ / OSX.2+</td>--%>
                        <%--<td class="center">1.8</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Firefox 3.0</td>--%>
                        <%--<td>Win 2k+ / OSX.3+</td>--%>
                        <%--<td class="center">1.9</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Camino 1.0</td>--%>
                        <%--<td>OSX.2+</td>--%>
                        <%--<td class="center">1.8</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Camino 1.5</td>--%>
                        <%--<td>OSX.3+</td>--%>
                        <%--<td class="center">1.8</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Netscape 7.2</td>--%>
                        <%--<td>Win 95+ / Mac OS 8.6-9.2</td>--%>
                        <%--<td class="center">1.7</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Netscape Browser 8</td>--%>
                        <%--<td>Win 98SE+</td>--%>
                        <%--<td class="center">1.7</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Netscape Navigator 9</td>--%>
                        <%--<td>Win 98+ / OSX.2+</td>--%>
                        <%--<td class="center">1.8</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Mozilla 1.0</td>--%>
                        <%--<td>Win 95+ / OSX.1+</td>--%>
                        <%--<td class="center">1</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Mozilla 1.1</td>--%>
                        <%--<td>Win 95+ / OSX.1+</td>--%>
                        <%--<td class="center">1.1</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Mozilla 1.2</td>--%>
                        <%--<td>Win 95+ / OSX.1+</td>--%>
                        <%--<td class="center">1.2</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Mozilla 1.3</td>--%>
                        <%--<td>Win 95+ / OSX.1+</td>--%>
                        <%--<td class="center">1.3</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Mozilla 1.4</td>--%>
                        <%--<td>Win 95+ / OSX.1+</td>--%>
                        <%--<td class="center">1.4</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Mozilla 1.5</td>--%>
                        <%--<td>Win 95+ / OSX.1+</td>--%>
                        <%--<td class="center">1.5</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Mozilla 1.6</td>--%>
                        <%--<td>Win 95+ / OSX.1+</td>--%>
                        <%--<td class="center">1.6</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Mozilla 1.7</td>--%>
                        <%--<td>Win 98+ / OSX.1+</td>--%>
                        <%--<td class="center">1.7</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Mozilla 1.8</td>--%>
                        <%--<td>Win 98+ / OSX.1+</td>--%>
                        <%--<td class="center">1.8</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Seamonkey 1.1</td>--%>
                        <%--<td>Win 98+ / OSX.2+</td>--%>
                        <%--<td class="center">1.8</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Gecko</td>--%>
                        <%--<td>Epiphany 2.20</td>--%>
                        <%--<td>Gnome</td>--%>
                        <%--<td class="center">1.8</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Webkit</td>--%>
                        <%--<td>Safari 1.2</td>--%>
                        <%--<td>OSX.3</td>--%>
                        <%--<td class="center">125.5</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Webkit</td>--%>
                        <%--<td>Safari 1.3</td>--%>
                        <%--<td>OSX.3</td>--%>
                        <%--<td class="center">312.8</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Webkit</td>--%>
                        <%--<td>Safari 2.0</td>--%>
                        <%--<td>OSX.4+</td>--%>
                        <%--<td class="center">419.3</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Webkit</td>--%>
                        <%--<td>Safari 3.0</td>--%>
                        <%--<td>OSX.4+</td>--%>
                        <%--<td class="center">522.1</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Webkit</td>--%>
                        <%--<td>OmniWeb 5.5</td>--%>
                        <%--<td>OSX.4+</td>--%>
                        <%--<td class="center">420</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Webkit</td>--%>
                        <%--<td>iPod Touch / iPhone</td>--%>
                        <%--<td>iPod</td>--%>
                        <%--<td class="center">420.1</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Webkit</td>--%>
                        <%--<td>S60</td>--%>
                        <%--<td>S60</td>--%>
                        <%--<td class="center">413</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Presto</td>--%>
                        <%--<td>Opera 7.0</td>--%>
                        <%--<td>Win 95+ / OSX.1+</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Presto</td>--%>
                        <%--<td>Opera 7.5</td>--%>
                        <%--<td>Win 95+ / OSX.2+</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Presto</td>--%>
                        <%--<td>Opera 8.0</td>--%>
                        <%--<td>Win 95+ / OSX.2+</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Presto</td>--%>
                        <%--<td>Opera 8.5</td>--%>
                        <%--<td>Win 95+ / OSX.2+</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Presto</td>--%>
                        <%--<td>Opera 9.0</td>--%>
                        <%--<td>Win 95+ / OSX.3+</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Presto</td>--%>
                        <%--<td>Opera 9.2</td>--%>
                        <%--<td>Win 88+ / OSX.3+</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Presto</td>--%>
                        <%--<td>Opera 9.5</td>--%>
                        <%--<td>Win 88+ / OSX.3+</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Presto</td>--%>
                        <%--<td>Opera for Wii</td>--%>
                        <%--<td>Wii</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Presto</td>--%>
                        <%--<td>Nokia N800</td>--%>
                        <%--<td>N800</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Presto</td>--%>
                        <%--<td>Nintendo DS browser</td>--%>
                        <%--<td>Nintendo DS</td>--%>
                        <%--<td class="center">8.5</td>--%>
                        <%--<td class="center">C/A<sup>1</sup></td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeC">--%>
                        <%--<td>KHTML</td>--%>
                        <%--<td>Konqureror 3.1</td>--%>
                        <%--<td>KDE 3.1</td>--%>
                        <%--<td class="center">3.1</td>--%>
                        <%--<td class="center">C</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>KHTML</td>--%>
                        <%--<td>Konqureror 3.3</td>--%>
                        <%--<td>KDE 3.3</td>--%>
                        <%--<td class="center">3.3</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>KHTML</td>--%>
                        <%--<td>Konqureror 3.5</td>--%>
                        <%--<td>KDE 3.5</td>--%>
                        <%--<td class="center">3.5</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeX">--%>
                        <%--<td>Tasman</td>--%>
                        <%--<td>Internet Explorer 4.5</td>--%>
                        <%--<td>Mac OS 8-9</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">X</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeC">--%>
                        <%--<td>Tasman</td>--%>
                        <%--<td>Internet Explorer 5.1</td>--%>
                        <%--<td>Mac OS 7.6-9</td>--%>
                        <%--<td class="center">1</td>--%>
                        <%--<td class="center">C</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeC">--%>
                        <%--<td>Tasman</td>--%>
                        <%--<td>Internet Explorer 5.2</td>--%>
                        <%--<td>Mac OS 8-X</td>--%>
                        <%--<td class="center">1</td>--%>
                        <%--<td class="center">C</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Misc</td>--%>
                        <%--<td>NetFront 3.1</td>--%>
                        <%--<td>Embedded devices</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">C</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeA">--%>
                        <%--<td>Misc</td>--%>
                        <%--<td>NetFront 3.4</td>--%>
                        <%--<td>Embedded devices</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">A</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeX">--%>
                        <%--<td>Misc</td>--%>
                        <%--<td>Dillo 0.8</td>--%>
                        <%--<td>Embedded devices</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">X</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeX">--%>
                        <%--<td>Misc</td>--%>
                        <%--<td>Links</td>--%>
                        <%--<td>Text only</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">X</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeX">--%>
                        <%--<td>Misc</td>--%>
                        <%--<td>Lynx</td>--%>
                        <%--<td>Text only</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">X</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeC">--%>
                        <%--<td>Misc</td>--%>
                        <%--<td>IE Mobile</td>--%>
                        <%--<td>Windows Mobile 6</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">C</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeC">--%>
                        <%--<td>Misc</td>--%>
                        <%--<td>PSP browser</td>--%>
                        <%--<td>PSP</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">C</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="gradeU">--%>
                        <%--<td>Other browsers</td>--%>
                        <%--<td>All others</td>--%>
                        <%--<td>-</td>--%>
                        <%--<td class="center">-</td>--%>
                        <%--<td class="center">U</td>--%>
                    <%--</tr>--%>
                    </tbody>
                </table>

            </div>
        </div>
        <!-- Warper Ends Here (working area) -->


    <%--<div class="page-header">--%>
        <%--<h3>&nbsp;&nbsp;&nbspOrder Request&nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small>--%>
        <%--</h3>--%>
    <%--</div>--%>

    <%--<c:set var="orderName" value="orderSessionNo${orderId}"/>--%>
    <%--<c:set var="relatedOrder" value="${sessionScope[orderName]}"/>--%>

    <%--<div class="row">--%>
        <%--<p>Order Number: ${relatedOrder.orderId}</p>--%>
        <%--<p>Order--%>
            <%--By: ${relatedOrder.user.firstName}&nbsp;${relatedOrder.user.middleName}&nbsp;${relatedOrder.user.lastName}</p>--%>
        <%--<p>Schedule For: ${relatedOrder.orderSchedule}</p>--%>
        <%--<p>Budget: ${relatedOrder.budgetAmount}</p>--%>
    <%--</div>--%>

    <%--<c:set var="orderIdValue" value="${relatedOrder.orderId}"/>--%>
    <%--<c:set var="basketName" value="orderDetailsMap-OrderNo${orderIdValue}"/>--%>

    <%--<div>--%>
        <%--<c:if test="${not empty sessionScope[basketName]}">--%>
            <%--<a href="/order-details/basket/${orderIdValue}"><img width="16"--%>
                                                                 <%--src="${resourceURL}/images/order/add-to-cart.png"--%>
                                                                 <%--alt="add to cart"/><span>${fn:length(sessionScope[basketName])}</span></a>--%>
        <%--</c:if>--%>
    <%--</div>--%>


    <%--<hr class="style13">--%>


    <%--<div class="row">--%>
        <%--<table class="table table-hover">--%>
            <%--<thead>--%>
            <%--&lt;%&ndash;<th>Name</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>Item Type</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>Available Quantity</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>Item Picture</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>Packages</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>Package Capacity</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>No of Packs</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>Quantity Request</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>Category</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>Unit Price</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>Suppliers</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>Add To Cart</th>&ndash;%&gt;--%>
            <%--</thead>--%>
            <%--<c:forEach items="${itemList}" var="item" varStatus="loop">--%>
                <%--<form:form commandName="orderDetail" method="post">--%>
                    <%--<tr>--%>
                        <%--<td>${item.name}</td>--%>
                        <%--<td>${item.itemType}</td>--%>
                        <%--<td>${item.quantity}</td>--%>

                        <%--<c:choose>--%>
                            <%--<c:when test="${not empty item.picName}">--%>
                                <%--<td><img width="64" src="${baseUrl}${item.picName}" alt="item image"/></td>--%>
                            <%--</c:when>--%>
                            <%--<c:otherwise>--%>
                                <%--<td><img width="64" src="${resourceURL}/images/shared-images/no-item.png"--%>
                                         <%--alt="item image"/>--%>
                                <%--</td>--%>
                            <%--</c:otherwise>--%>
                        <%--</c:choose>--%>
                        <%--<td>--%>
                            <%--<form:select path="pack">--%>
                                <%--<form:options items="${packs}" itemLabel="name" itemValue="packId"/>--%>
                            <%--</form:select>--%>
                        <%--</td>--%>

                        <%--<td>--%>
                            <%--<form:input path="packCapacity"/>--%>
                        <%--</td>--%>

                        <%--<td>--%>
                            <%--<form:input path="noOfPacks"/>--%>
                        <%--</td>--%>

                        <%--<td>--%>
                            <%--<form:input path="totalQuantityRequestNo"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--<form:select path="category">--%>
                                <%--<form:options items="${categories}" itemLabel="name" itemValue="categoryId"/>--%>
                            <%--</form:select>--%>
                        <%--</td>--%>

                        <%--<td>--%>
                            <%--<form:input path="unitPrice"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--<form:select path="supplier">--%>
                                <%--<form:options items="${suppliers}" itemLabel="name" itemValue="supplierId"/>--%>
                            <%--</form:select>--%>

                        <%--</td>--%>

                        <%--<form:hidden path="order" value="${relatedOrder.orderId}"/>--%>
                        <%--<form:hidden path="item" value="${item.itemId}"/>--%>

                        <%--<td>--%>
                            <%--<button class="btn btn-success btn-block">Add To Cart</button>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                <%--</form:form>--%>
            <%--</c:forEach>--%>

        <%--</table>--%>
    <%--</div>--%>

    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>

