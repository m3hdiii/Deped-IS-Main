<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
<sec:authentication var="principal" property="principal" />
<c:set var="currentUser" value="${principal.user}" />
--%>
<aside class="left-panel">

    <div class="user text-center">
        <img src="${resourceURL}/images/avtar/user.png" class="img-circle" alt="...">
        <h4 class="user-name">Name Here</h4>

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
                    Sign out</a></li>
            </ul>
        </div>
    </div>


    <nav class="navigation">
        <ul class="list-unstyled">
            <li class="active" data-toggle="tooltip" title="Dashboard"><a href="/dashboard"><i
                    class="fa fa-dashboard"></i><span class="nav-label">Dashboard</span></a></li>


            <li class="has-submenu" data-toggle="tooltip" title="Request Items"><a href="#"><i
                    class="fa fa-list-alt"></i> <span class="nav-label">Request Items</span></a>
                <ul class="list-unstyled">
                    <li><a href="/request/create">Create Request</a></li>
                </ul>
            </li>
            <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">
                <li class="has-submenu" data-toggle="tooltip" title="Order Items"><a href="#"><i
                        class="fa fa-file-text-o"></i> <span class="nav-label">Order Items</span></a>
                    <ul class="list-unstyled">
                        <li><a href="/order/create">Create Order</a></li>
                    </ul>
                </li>
            </sec:authorize>

            <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER') OR hasRole('ROLE_ADMIN')">
                <li class="has-submenu" data-toggle="tooltip" title="Item Manager"><a href="#"><i
                        class="fa fa-cubes"></i>
                    <span class="nav-label">Item Manager</span></a>
                    <ul class="list-unstyled">
                        <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER') OR hasRole('ROLE_ADMIN')">
                            <li><a href="/item/list">List of Item</a></li>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">
                            <li><a href="/item/create">Create Item</a></li>
                        </sec:authorize>
                    </ul>
                </li>
            </sec:authorize>

            <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER') OR hasRole('ROLE_CHIEF')">
                <li class="has-submenu" data-toggle="tooltip" title="Order Manager"><a href="#"><i
                        class="fa fa-truck"></i>
                    <span class="nav-label">Order Manager</span></a>
                    <ul class="list-unstyled">
                        <sec:authorize access="hasRole('ROLE_CHIEF')">
                            <li><a href="/order/approval-list">Approve Order</a></li>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">
                            <li><a href="/order/requisition-list">Order Requisition</a></li>
                            <li><a href="/order/arrival-list">Arrived Order Checklist</a></li>
                        </sec:authorize>
                    </ul>
                </li>
            </sec:authorize>

            <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER') OR hasRole('ROLE_CHIEF')">
                <li class="has-submenu" data-toggle="tooltip" title="Request Manager"><a href="#"><i
                        class="fa fa-archive"></i> <span class="nav-label">Request Manager</span></a>
                    <ul class="list-unstyled">
                        <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER') OR hasRole('ROLE_CHIEF')">
                            <li><a href="/request/approval-list">Approve Request</a></li>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">
                            <li><a href="/request/release-list">Release Request</a></li>
                        </sec:authorize>
                    </ul>
                </li>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN') OR hasRole('ROLE_CHIEF')">
                <li class="has-submenu" data-toggle="tooltip" title="User Manager"><a href="#"><i
                        class="fa fa-users"></i>
                    <span class="nav-label">User Manager</span></a>
                    <ul class="list-unstyled">
                        <sec:authorize access="hasRole('ROLE_ADMIN') OR hasRole('ROLE_CHIEF')">
                            <li><a href="/user/list">List of User</a></li>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <li><a href="/user/create">Create User</a></li>
                        </sec:authorize>
                    </ul>
                </li>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPPLY_OFFICER')">
                <li class="has-submenu" data-toggle="tooltip" title="Category"><a href="#"><i class="fa fa-gears"></i>
                    <span
                            class="nav-label">Category</span></a>
                    <ul class="list-unstyled">
                        <sec:authorize access="hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPPLY_OFFICER')">
                            <li><a href="/category/list">Category List</a></li>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">
                            <li><a href="/category/create">Create Category</a></li>
                        </sec:authorize>
                    </ul>
                </li>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <li class="has-submenu" data-toggle="tooltip" title="Department"><a href="#"><i class="fa fa-gears"></i>
                    <span class="nav-label">Department</span></a>
                    <ul class="list-unstyled">
                        <li><a href="/department/list">Department List</a></li>
                        <li><a href="/department/create">Create Department</a></li>
                    </ul>
                </li>

                <li class="has-submenu" data-toggle="tooltip" title="Section"><a href="#"><i class="fa fa-gears"></i>
                    <span
                            class="nav-label">Section</span></a>
                    <ul class="list-unstyled">
                        <li><a href="/section/list">Section List</a></li>
                        <li><a href="/section/create">Create Section</a></li>
                    </ul>
                </li>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPPLY_OFFICER')">
                <li class="has-submenu" data-toggle="tooltip" title="Brand"><a href="#"><i class="fa fa-gears"></i>
                    <span
                            class="nav-label">Brand</span></a>
                    <ul class="list-unstyled">
                        <sec:authorize access="hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPPLY_OFFICER')">
                            <li><a href="/brand/list">Brand List</a></li>
                        </sec:authorize>

                        <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">
                            <li><a href="/brand/create">Create Brand</a></li>
                        </sec:authorize>
                    </ul>
                </li>
            </sec:authorize>

            <sec:authorize access="hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPPLY_OFFICER')">
                <li class="has-submenu" data-toggle="tooltip" title="Package"><a href="#"><i class="fa fa-gears"></i>
                    <span
                            class="nav-label">Package</span></a>
                    <ul class="list-unstyled">
                        <sec:authorize access="hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPPLY_OFFICER')">
                            <li><a href="/pack/list">Pack List</a></li>
                        </sec:authorize>

                        <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">
                            <li><a href="/pack/create">Create Pack</a></li>
                        </sec:authorize>
                    </ul>
                </li>
            </sec:authorize>

            <sec:authorize access="hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPPLY_OFFICER')">
                <li class="has-submenu" data-toggle="tooltip" title="Package"><a href="#"><i class="fa fa-gears"></i>
                    <span
                            class="nav-label">Supplier</span></a>
                    <ul class="list-unstyled">
                        <sec:authorize access="hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPPLY_OFFICER')">
                            <li><a href="/supplier/list">Supplier List</a></li>
                        </sec:authorize>

                        <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">
                            <li><a href="/supplier/create">Create Supplier</a></li>
                        </sec:authorize>
                    </ul>
                </li>
            </sec:authorize>
            <li><a href="signin.html"><i class="fa fa-sign-out" data-toggle="tooltip" title="Sign Out"></i><span
                    class="nav-label">Sign Out</span></a></li>
        </ul>
    </nav>
</aside>