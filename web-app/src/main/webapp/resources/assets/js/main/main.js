$('.btn-next').click(function(){
    $('.pagination-circle > .pagination-active').next('li').find('a').trigger('click');
});

$('.btn-previous').click(function(){
    $('.pagination-circle > .pagination-active').prev('li').find('a').trigger('click');
});

$('.add-quantity').click(function(){
    var sel = document.getElementById("item-quantity-list");
    var i = sel.option[sel.selectedIndex]
});

$('.minus-quantity').click(function(){
    $('.pagination-circle > .pagination-active').prev('li').find('a').trigger('click');
});
