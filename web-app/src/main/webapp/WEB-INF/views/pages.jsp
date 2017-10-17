<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="includes/head.jsp">
    <c:param name="title" value="Edit Profile"/>
    <c:param name="description" value="Edit Profile Page"/>
</c:import>
<body>
<section>

    <div>
        <div class="jumbotron">
            <h1 class="text-center">Project Pages</h1>
        </div>
    </div>
    <div class="container">

        <div class="row">
            <p class="col col-lg-3"><a href="/brand/create">Create Brand</a>&nbsp;<img width="20" height="20"
                                                                                       src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/category/create">Create Category</a>&nbsp;<img width="20" height="20"
                                                                                             src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/equipment/create">Create Equipment</a></p>
            <p class="col col-lg-3"><a href="/goods/create">Create Goods</a>&nbsp;<img width="20" height="20"
                                                                                       src="${resourceURL}/images/check.png"/>
            </p>
        </div>
        <div class="row">
            <p class="col col-lg-3"><a href="/brand/update/1">Update Brand 1</a>&nbsp;<img width="20" height="20"
                                                                                           src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/category/update/1">Update Category 1</a>&nbsp;<img width="20" height="20"
                                                                                                 src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/equipment/update/1">Update Equipment 1</a></p>
            <p class="col col-lg-3"><a href="/goods/update/1">Update Goods 1</a>&nbsp;<img width="20" height="20"
                                                                                           src="${resourceURL}/images/check.png"/>
            </p>
        </div>
        <div class="row">
            <p class="col col-lg-3"><a href="/brand/1">Brand 1 Info</a>&nbsp;<img width="20" height="20"
                                                                                  src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/category/1">Category 1 Info</a>&nbsp;<img width="20" height="20"
                                                                                        src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/equipment/1">Equipment 1 Info</a></p>
            <p class="col col-lg-3"><a href="/goods/1">Goods 1 Info</a>&nbsp;<img width="20" height="20"
                                                                                  src="${resourceURL}/images/check.png"/>
            </p>

        </div>
        <div class="row">
            <p class="col col-lg-3"><a href="/brand/list">Brand List</a>&nbsp;<img width="20" height="20"
                                                                                   src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/category/list">Category List</a>&nbsp;<img width="20" height="20"
                                                                                         src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/equipment/list">Equipment List</a></p>
            <p class="col col-lg-3"><a href="/goods/list">Goods List</a>&nbsp;<img width="20" height="20"
                                                                                   src="${resourceURL}/images/check.png"/>
            </p>
        </div>
        <hr>

        <!-- NEXT ROW -->

        <div class="row">
            <p class="col col-lg-3"><a href="/semi-expendable/create">Create Semi Expendable</a>&nbsp;<img width="20"
                                                                                                           height="20"
                                                                                                           src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/pack/create">Create Pack</a>&nbsp;<img width="20" height="20"
                                                                                     src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/department/create">Create Department</a>&nbsp;<img width="20" height="20"
                                                                                                 src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/section/create">Create Section</a>&nbsp;<img width="20" height="20"
                                                                                           src="${resourceURL}/images/check.png"/>
            </p>
        </div>
        <div class="row">
            <p class="col col-lg-3"><a href="/semi-expendable/update/3">Update Semi Expendable 3</a>&nbsp;<img
                    width="20" height="20" src="${resourceURL}/images/check.png"/></p>
            <p class="col col-lg-3"><a href="/pack/update/1">Update Pack 1</a>&nbsp;<img width="20" height="20"
                                                                                         src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/department/update/1">Update Department 1</a>&nbsp;<img width="20"
                                                                                                     height="20"
                                                                                                     src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/section/update/1">Update Section 1</a>&nbsp;<img width="20" height="20"
                                                                                               src="${resourceURL}/images/check.png"/>
            </p>
        </div>
        <div class="row">
            <p class="col col-lg-3"><a href="/semi-expendable/3">Semi Expendable 3 Info</a>&nbsp;<img width="20"
                                                                                                      height="20"
                                                                                                      src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/pack/1">Pack 1 Info</a>&nbsp;<img width="20" height="20"
                                                                                src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/department/1">Department 1 Info</a>&nbsp;<img width="20" height="20"
                                                                                            src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/section/1">Section 1 Info</a>&nbsp;<img width="20" height="20"
                                                                                      src="${resourceURL}/images/check.png"/>
            </p>
        </div>
        <div class="row">
            <p class="col col-lg-3"><a href="/semi-expendable/list">Semi Expendable List</a>&nbsp;<img width="20"
                                                                                                       height="20"
                                                                                                       src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/pack/list">Pack List</a>&nbsp;<img width="20" height="20"
                                                                                 src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/department/list">Department List</a>&nbsp;<img width="20" height="20"
                                                                                             src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"><a href="/section/list">Section List</a>&nbsp;<img width="20" height="20"
                                                                                       src="${resourceURL}/images/check.png"/>
            </p>
        </div>

        <hr>
        <!-- NEXT ROW -->

        <div class="row">
            <p class="col col-lg-3"><a href="/borrow-request/create">Create Borrow Request</a></p>
            <p class="col col-lg-3"><a href="/request-item/create">Create Request Item</a></p>
            <p class="col col-lg-3"><a href="/supply/create">Create Supply</a></p>
            <p class="col col-lg-3"><a href="/user/create">Create User</a>&nbsp;<img width="20" height="20"
                                                                                     src="${resourceURL}/images/check.png"/>
            </p>
        </div>
        <div class="row">
            <p class="col col-lg-3"><a href="/borrow-request/update/1">Update Borrow Request 1</a></p>
            <p class="col col-lg-3"><a href="/request-item/update/1">Update Request Item 1</a></p>
            <p class="col col-lg-3"><a href="/supply/update/1">Update Supply 1</a></p>
            <p class="col col-lg-3"><a href="/user/update/1">Update User 1</a>&nbsp;<img width="20" height="20"
                                                                                         src="${resourceURL}/images/check.png"/>
            </p>
        </div>
        <div class="row">
            <p class="col col-lg-3"><a href="/borrow-request/1">Borrow Request 1 Info</a></p>
            <p class="col col-lg-3"><a href="/request-item/1">Request Item 1 Info</a></p>
            <p class="col col-lg-3"><a href="/supply/1">Supply 1 Info</a></p>
            <p class="col col-lg-3"><a href="/user/1">User 1 Info</a>&nbsp;<img width="20" height="20"
                                                                                src="${resourceURL}/images/check.png"/>
            </p>
        </div>
        <div class="row">
            <p class="col col-lg-3"><a href="/borrow-request/list">Borrow Request List</a></p>
            <p class="col col-lg-3"><a href="/request-item/list">Request Item List</a></p>
            <p class="col col-lg-3"><a href="/supply/list">Supply List</a></p>
            <p class="col col-lg-3"><a href="/user/list">User List</a></p>
        </div>


        <hr>
        <div class="row">
            <p class="col col-lg-3"></p>
            <p class="col col-lg-3"><a href="/login">Login</a>&nbsp;<img width="20" height="20"
                                                                         src="${resourceURL}/images/check.png"/></p>
            <p class="col col-lg-3"><a href="/dashboard">Dashboard Page</a>&nbsp;<img width="20" height="20"
                                                                                      src="${resourceURL}/images/check.png"/>
            </p>
            <p class="col col-lg-3"></p>
        </div>


        <%-- <!-- NEXT ROW -->
        <div class="row">
            <div class="col">
                <p class="col col-lg-3"><a href="/%%%/create" >Create ***</a></p>
                <p class="col col-lg-3"><a href="/%%%/update/1" >Update *** 1</a></p>
                <p class="col col-lg-3"><a href="/%%%/1" >*** 1 Info</a></p>
                <p class="col col-lg-3"><a href="/%%%/list" >*** List</a></p>
            </div>
            <div class="col">
                <p class="col col-lg-3"><a href="/%%%/create" >Create ***</a></p>
                <p class="col col-lg-3"><a href="/%%%/update/1" >Update *** 1</a></p>
                <p class="col col-lg-3"><a href="/%%%/1" >*** 1 Info</a></p>
                <p class="col col-lg-3"><a href="/%%%/list" >*** List</a></p>
            </div>
            <div class="col">
                <p class="col col-lg-3"><a href="/%%%/create" >Create ***</a></p>
                <p class="col col-lg-3"><a href="/%%%/update/1" >Update *** 1</a></p>
                <p class="col col-lg-3"><a href="/%%%/1" >*** 1 Info</a></p>
                <p class="col col-lg-3"><a href="/%%%/list" >*** List</a></p>
            </div>
            <div class="col">
                <p class="col col-lg-3"><a href="/%%%/create" >Create ***</a></p>
                <p class="col col-lg-3"><a href="/%%%/update/1" >Update *** 1</a></p>
                <p class="col col-lg-3"><a href="/%%%/1" >*** 1 Info</a></p>
                <p class="col col-lg-3"><a href="/%%%/list" >*** List</a></p>
            </div>
        </div> --%>

    </div>
</section>
</body>
</html>
