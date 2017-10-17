<%--
  Created by IntelliJ IDEA.
  User: kevin
  Date: 27/09/2017
  Time: 11:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:url value="/public" var="resourceURL" scope="request"/>
<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Goods"/>
    <c:param name="description" value="List of Goods"/>
</c:import>
<html>
<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">
        <div class="page-header">
            <h1>Goods
                <small>DepEd-Baguio City Division Office</small>
            </h1>

        </div>


        <div class="row item-body">

            <!-- Items-thumbnail-and-content-of-the-item-thumbnail -->

            <div class='col-xs-3 thumbnail item-content-thumbnail'>
                <img src="${resourceURL}/images/carousel/1.jpg" alt="1-jpg" width="304px" height="236px">
                <div class="item-infomation text-center">
                    <h4>Thumbnail</h4>
                    <label>Description:</label>
                    <p>The .img-thumbnail class creates a thumbnail of the image:</p>
                    <label>Quantity:</label>
                    <p>12</p>
                    <button class="btn btn-md btn-purple">Add to cart</button>
                    <button class="btn btn-md btn-danger">Check out</button>
                </div>
            </div>
            <div class='col-xs-3 thumbnail item-content-thumbnail'>
                <img src="${resourceURL}/images/carousel/1.jpg" alt="1-jpg" width="304px" height="236px">
                <div class="item-infomation text-center">
                    <h4>Thumbnail</h4>
                    <label>Description:</label>
                    <p>The .img-thumbnail class creates a thumbnail of the image:</p>
                    <label>Quantity:</label>
                    <p>12</p>
                    <button class="btn btn-md btn-purple">Add to cart</button>
                    <button class="btn btn-md btn-danger">Check out</button>
                </div>
            </div>
            <div class='col-xs-3 thumbnail item-content-thumbnail'>
                <img src="${resourceURL}/images/carousel/1.jpg" alt="1-jpg" width="304px" height="236px">
                <div class="item-infomation text-center">
                    <h4>Thumbnail</h4>
                    <label>Description:</label>
                    <p>The .img-thumbnail class creates a thumbnail of the image:</p>
                    <label>Quantity:</label>
                    <p>12</p>
                    <button class="btn btn-md btn-purple">Add to cart</button>
                    <button class="btn btn-md btn-danger">Check out</button>
                </div>
            </div>
            <div class='col-xs-3 thumbnail item-content-thumbnail'>
                <img src="${resourceURL}/images/carousel/1.jpg" alt="1-jpg" width="304px" height="236px">
                <div class="item-infomation text-center">
                    <h4>Thumbnail</h4>
                    <label>Description:</label>
                    <p>The .img-thumbnail class creates a thumbnail of the image:</p>
                    <label>Quantity:</label>
                    <p>12</p>
                    <button class="btn btn-md btn-purple">Add to cart</button>
                    <button class="btn btn-md btn-danger">Check out</button>
                </div>
            </div>


            <!-- PAGINATION FOR THE ITEMS -->

            <div class="items-pagination col-md-12 text-center">
                <nav aria-label="Pagination-for-items">
                    <ul class="pagination">
                        <li class="page-item disabled">
                            <a class="page-link" href="#" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                                <span class="sr-only">Previous</span>
                            </a>
                        </li>
                        <li class="page-item active"><a class="page-link" href="#">1</a></li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item">
                            <a class="page-link" href="#" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                                <span class="sr-only">Next</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

    </div>
    <!-- Warper Ends Here (working area) -->

    <!-- CART MODAL -->
    <div class="modal" id="item-cart-modal" tabindex="-1" role="dialog" aria-labelledby="Modal-Label"
         aria-hidden="true">
        <div class="modal-dialog">

            <!-- modal content -->
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title" id="Modal-Label">Deped Cart</h3>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form>
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Image</th>
                                <th>Item Name</th>
                                <th>Quantity</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td><img src="${resourceURL}/images/carousel/1.jpg" alt="1-jpg" width="50" height="50"/>
                                </td>
                                <td>Thumbnail</td>
                                <td>
                                    <div class="input-group up-down-select">
                                        <span class="btn-default btn-up-down fa-border down"><i
                                                class="glyphicon glyphicon-minus up-down-icon"></i></span>
                                        <select class="item-quantity">
                                            <option selected>1</option>
                                            <option>2</option>
                                            <option>3</option>
                                            <option>4</option>
                                            <option>5</option>
                                        </select>
                                        <span class="btn-default btn-up-down fa-border up"><i
                                                class="glyphicon glyphicon-plus up-down-icon"></i></span>
                                    </div>
                                </td>
                                <td>
                                    <!-- <button class="btn btn-danger"><span class="glyphicon glyphicon-trash"> <strong>Remove</strong></span></button> -->
                                    <button type="button" class="close" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <img src="${resourceURL}/images/carousel/2.jpg" alt="1-jpg" width="50" height="50"/>
                                </td>
                                <td>Thumbnail</td>
                                <td>
                                    <div class="input-group up-down-select">
                                        <span class="btn-default btn-up-down fa-border down"><i
                                                class="glyphicon glyphicon-minus up-down-icon"></i></span>
                                        <select class="item-quantity">
                                            <option selected>1</option>
                                            <option>2</option>
                                            <option>3</option>
                                            <option>4</option>
                                            <option>5</option>
                                        </select>
                                        <span class="btn-default btn-up-down fa-border up"><i
                                                class="glyphicon glyphicon-plus up-down-icon"></i></span>
                                    </div>
                                </td>
                                <td>
                                    <!-- <button class="btn btn-danger"><span class="glyphicon glyphicon-trash"> <strong>Remove</strong></span></button> -->
                                    <button type="button" class="close" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger">Checkout</button>
                </div>
            </div>
        </div>
    </div>

    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>
