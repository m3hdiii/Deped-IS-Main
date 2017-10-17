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