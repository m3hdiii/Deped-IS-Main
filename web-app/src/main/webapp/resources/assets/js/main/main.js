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

function multiplyValue(get1, get2, totalQtyID) {
    var unitContent = document.getElementById(get1.toString()).value;
    var numOfUnit = document.getElementById(get2.toString()).value;
    var totalQty = unitContent * numOfUnit;

    document.getElementById(totalQtyID.toString()).innerHTML = totalQty;
}

function clickOnDateInput(id) {
    var date_input = $('#' + id); //our date input has the name "date"
    var container = $('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
    var options = {
        format: 'mm/dd/yyyy',
        container: container,
        todayHighlight: true,
        autoclose: true,
    };
    date_input.datepicker(options);
}

function lockDownTotal(loopNo) {//first input
    //if the capacity value is not empty then lock the total. if its empty then look at the (second) noOfUnits
    // if noOfUnits was also empty then lock-out the total otherwise don't lock it out
    //if the second one and the first one, both not empty then multiply the two and put it inside the total
    var no1Id = 'num1' + loopNo;
    var no2Id = 'num2' + loopNo;
    var totalId = 'total' + loopNo;
    var no1Value = document.getElementById(no1Id).value;
    var no2Value = document.getElementById(no2Id).value;
    var totalElement = document.getElementById(totalId);

    if ((isAcceptable(no1Value) && no1Value != "") && (isAcceptable(no2Value) && no2Value != "")) {
        var total = no1Value * no2Value;
        totalElement.value = total;
        return;
    }

    if ((isAcceptable(no1Value) && no1Value != "") || (isAcceptable(no2Value) && no2Value != "")) {
        totalElement.readOnly = true;

        totalElement.value = "";
    } else {
        totalElement.readOnly = false;
    }

}

function lockDownTheFirstTwo(loopNo) {//third input
    //once the user start putting character check the value if the value is not empty then lock the first and second but
    //if its empty then lock-out the first two

    var no1Id = 'num1' + loopNo;
    var no2Id = 'num2' + loopNo;
    var unitId = 'unit' + loopNo;
    /*To Get the Unit ID*/
    var totalId = 'total' + loopNo;
    var no1Element = document.getElementById(no1Id);
    var no2Element = document.getElementById(no2Id);
    var unitElement = document.getElementById(unitId);
    /*Unit Element*/
    var totalValue = document.getElementById(totalId).value;


    if (isAcceptable(totalValue) && totalValue != "") {
        no1Element.readOnly = true;
        no1Element.value = "";
        no2Element.readOnly = true;
        no2Element.value = "";
        unitElement.disabled = true;
        /*I put this to disable the unit dropdown list*/

    } else {
        no1Element.readOnly = false;
        no2Element.readOnly = false;
        unitElement.disabled = false;
        /*I put this to enable the unit dropdown list*/
    }

}

function isEmpty(str) {
    return (!str || 0 === str.length);
}

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

function isAcceptable(val) {
    if(isEmpty(val) || isBlank(val)){
        return false;
    }
    return true;
}

/* This One is for Images to Upload. */
$(document).ready( function() {
    $(document).on('change', '.btn-file :file', function() {
        var input = $(this),
            label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.trigger('fileselect', [label]);
    });

    $('.btn-file :file').on('fileselect', function(event, label) {

        var input = $(this).parents('.input-group').find(':text'),
            log = label;

        if( input.length ) {
            input.val(log);
        } else {
            if( log ) alert(log);
        }

    });
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#img-upload').attr('src', e.target.result);
            }

            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#profilePicture").change(function(){
        readURL(this);
    });
});