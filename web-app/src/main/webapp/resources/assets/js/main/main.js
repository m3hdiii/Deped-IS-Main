$('.btn-next').click(function () {
    $('.pagination-circle > .pagination-active').next('li').find('a').trigger('click');
});

$('.btn-previous').click(function () {
    $('.pagination-circle > .pagination-active').prev('li').find('a').trigger('click');
});
