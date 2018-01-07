// var qtyField = $('#item-qty-field');
// var minusBtn = $('#minus-btn');
// var addBtn = $('#add-btn');

//
// addBtn.click(function () {
//     qtyField.val(parseInt(qtyField.val()) + 1);
// });
//
// minusBtn.click(function () {
//     if (qtyField.val() !== 0 && qtyField.val() > 0) {
//         qtyField.val(parseInt(qtyField.val()) - 1);
//     }
//
// });

// $(document).ready(function(){
//     var date_input=$('input[name="date"]'); //our date input has the name "date"
//     var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
//     date_input.datepicker({
//         format: 'mm/dd/yyyy',
//         container: container,
//         todayHighlight: true,
//         autoclose: true,
//     })
// })

// function clickOnDateInput(id) {
//     bootTheDatePicker(id);
// }

function clickOnDateInput(id){
    var date_input=$('#' + id); //our date input has the name "date"
    var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
    var options={
        format: 'mm/dd/yyyy',
        container: container,
        todayHighlight: true,
        autoclose: true,
    };
    date_input.datepicker(options);
}
