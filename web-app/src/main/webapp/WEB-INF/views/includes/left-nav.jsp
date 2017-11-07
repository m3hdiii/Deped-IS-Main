<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authentication var="principal" property="principal" />
<c:set var="currentUser" value="${principal.user}" />
--%>
<aside class="left-panel">

    <div class="user text-center">
        <img src="${resourceURL}/images/avtar/user.png" class="img-circle" alt="...">
        <h4 class="user-name">Employee Name Here</h4>

        <div class="dropdown user-login">
            <button class="btn btn-xs dropdown-toggle btn-rounded" type="button" data-toggle="dropdown"
                    aria-expanded="true">
                <i class="fa fa-circle status-icon available"></i> Available <i class="fa fa-angle-down"></i>
            </button>
            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                <li role="presentation"><a role="menuitem" href="#"><i class="fa fa-circle status-icon busy"></i>
                    Busy</a></li>
                <li role="presentation"><a role="menuitem" href="#"><i class="fa fa-circle status-icon invisibled"></i>
                    Invisible</a></li>
                <li role="presentation"><a role="menuitem" href="#"><i class="fa fa-circle status-icon signout"></i>
                    Away</a></li>
            </ul>
        </div>
    </div>


    <nav class="navigation">
        <ul class="list-unstyled">
            <li class="active">
                <a href="/dashboard"><i class="fa fa-dashboard"></i><span class="nav-label leftNavHeader">Dashboard</span></a>
            </li>

            <li class="has-submenu">
                <a href="#"><i class="fa fa-archive"></i><span class="nav-label leftNavHeader">Manage Items</span></a>
                <ul class="list-unstyled">
                    <li><a href="/item/list"></i><span>List of Item</span></a></li>
                    <li><a href="/item/create"></i><span>Create Item</span></a></li>
                    <div class="brand-list-pages">
                        <li><a href="/brand/list"><span>Brand List</span></a></li>
                        <li><a href="/brand/create"><span>Create Brand</span></a></li>
                        <li><a href="/category/list"><span>Category List</span></a></li>
                        <li><a href="/category/create"><span>Create Category</span></a></li>
                    </div>
                </ul>
            </li>


            <li class="has-submenu"><a href="#"><i class="fa fa-truck"></i> <span
                    class="nav-label leftNavHeader">Manage Orders</span></a>
                <ul class="list-unstyled">
                    <li><a href="/order/approval-list"><span>Approve Order</span></a></li>
                    <li><a href="/order/arrival-list"><span>Arrived Order</span></a></li>
                    <li><a href="/pack/list"><span>Pack List</span></a></li>
                    <li><a href="/pack/create"><span>Create Pack</span></a></li>
                </ul>
            </li>

            <li class="has-submenu"><a href="#"><i class="fa fa-cube"></i> <span
                    class="nav-label leftNavHeader">Manage Requests</span></a>
                <ul class="list-unstyled">
                    <li><a href="/request/approval-list">Approve Request</a></li>
                    <li><a href="/request/release-list"><span>Release Request</span></a> </li>
                </ul>
            </li>

            <li class="has-submenu"><a href="/order/create"><i class="fa fa-file-text-o"></i> <span
                    class="nav-label leftNavHeader">Order Items</span></a>
            </li>


            <li class="has-submenu"><a href="/request/create"><i class="fa fa-file-text-o"></i> <span
                    class="nav-label leftNavHeader">Request Items</span></a>
            </li>

            <li class="has-submenu"><a href="/user/list"><i class="fa fa-users"></i><span class="nav-label leftNavHeader">Manage User</span></a>
                <ul class="list-unstyled">
                    <li><a href="/user/create"><span> Create User</span></a></li>
                    <li><a href="/department/list"><span> Department List</span></a></li>
                    <li><a href="/department/create"><span> Create Department</span></a></li>
                    <li><a href="/section/list"><span> Section List</span></a></li>
                    <li><a href="/section/create"><span> Create Section</span></a></li>
                </ul>
            </li>

            <li class="has-submenu"><a href="#"><i class="fa fa-sign-out"></i> <span class="nav-label leftNavHeader">Logout</span></a>
            </li>
        </ul>
    </nav>

    <%--<nav class="navigation">--%>
        <%--<ul class="list-unstyled">--%>
            <%--<li class="active"><a href="/dashboard"><span--%>
                    <%--class="nav-label leftNavHeader">Dashboard</span></a>--%>
            <%--</li>--%>
            <%--<li class="has-submenu"><a href="#"><span class="nav-label leftNavHeader">Profile</span></a>--%>
                <%--<ul class="list-unstyled">--%>
                    <%--<li><a href="/user/${currentUser.userId}">User Profile</a></li>--%>
                    <%--<li><a href="/user/update/${currentUser.userId}">Update User Profile</a></li>--%>
                    <%--<li><a href="/user/list">Search a User</a></li>--%>
                <%--</ul>--%>
            <%--</li>--%>

            <%--<li class="has-submenu"><a href="#"><i class="fa fa-file-text-o"></i> <span--%>
                    <%--class="nav-label leftNavHeader">Items</span></a>--%>
                <%--<ul class="list-unstyled">--%>
                    <%--<li><a href="/item/create">Create Item</a></li>--%>
                    <%--<li><a href="/item/list">All Items</a></li>--%>
                <%--</ul>--%>
            <%--</li>--%>

            <%--<li class="has-submenu"><a href="#"><i class="fa fa-file-text-o"></i> <span--%>
                    <%--class="nav-label leftNavHeader">Order Items</span></a>--%>
                <%--<ul class="list-unstyled">--%>
                    <%--<li><a href="/order/create">Create Orders</a></li>--%>
                    <%--<li><a href="/order/add-items">Add Items</a></li>--%>
                    <%--<li><a href="/order/list">All Orders</a></li>--%>
                    <%--<li><a href="/order/manage">Manage Orders</a></li>--%>
                <%--</ul>--%>
            <%--</li>--%>


            <%--<li class="has-submenu"><a href="#"><i class="fa fa-file-text-o"></i> <span--%>
                    <%--class="nav-label leftNavHeader">Request Items</span></a>--%>
                <%--<ul class="list-unstyled">--%>
                    <%--<li><a href="/request/create">Create Request</a></li>--%>
                    <%--<li><a href="/request/add-items">Add Items</a></li>--%>
                    <%--<li><a href="/request/list">All Requests</a></li>--%>
                    <%--<li><a href="/request/manage">Manage Requests</a></li>--%>
                <%--</ul>--%>
            <%--</li>--%>

            <%--<li class="has-submenu"><a href="#"><span--%>
                    <%--class="nav-label leftNavHeader">User Management</span></a>--%>
                <%--<ul class="list-unstyled">--%>
                    <%--<li><a href="/user/create">Create User</a></li>--%>
                    <%--<li><a href="/user/list">List of Users</a></li>--%>
                <%--</ul>--%>
            <%--</li>--%>
            <%--<li class="has-submenu"><a href="#"><span class="nav-label leftNavHeader">Departments</span></a>--%>
                <%--<ul class="list-unstyled">--%>
                    <%--<li><a href="/department/create">Create Department</a></li>--%>
                    <%--<li><a href="/department/list">All Departments</a></li>--%>
                <%--</ul>--%>
            <%--</li>--%>

            <%--<li class="has-submenu">--%>
                <%--<a href="#"><span class="nav-label leftNavHeader">Sections</span></a>--%>
                <%--<ul class="list-unstyled">--%>
                    <%--<li><a href="/section/create">Create Section</a></li>--%>
                    <%--<li><a href="/section/list">All Sections</a></li>--%>
                <%--</ul>--%>
            <%--</li>--%>


            <%--<li class="has-submenu"><a href="#"> <span class="nav-label leftNavHeader">Request Management</span></a>--%>
                <%--<ul class="list-unstyled">--%>
                    <%--<li><a href="/borrow-request/create">Request for an Equipment</a></li>--%>
                    <%--<li><a href="/request-item/create">Request for Goods</a></li>--%>
                    <%--<li><a href="/request-item/create">Request for Semi-Expendables</a></li>--%>
                    <%--<li><a href="/request-item/update">Manage an Item Request</a></li>--%>
                    <%--<li><a href="/request-item/1">View Item Request Info</a></li>--%>
                    <%--<li><a href="/request-item/list">List of all Item Requests</a></li>--%>
                <%--</ul>--%>
            <%--</li>--%>

            <%--<li class="has-submenu"><a href="#"><i class="fa fa-sign-out"></i> <span class="nav-label leftNavHeader">Logout</span></a>--%>
            <%--</li>--%>
        <%--</ul>--%>
    <%--</nav>--%>
</aside>