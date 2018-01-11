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
        totalElement.disabled = true;
        totalElement.value = "";
    } else {
        totalElement.disabled = false;

    }

}

function lockDownTheFirstTwo(loopNo) {//third input
    //once the user start putting character check the value if the value is not empty then lock the first and second but
    //if its empty then lock-out the first two

    var no1Id = 'num1' + loopNo;
    var no2Id = 'num2' + loopNo;
    var totalId = 'total' + loopNo;
    var no1Element = document.getElementById(no1Id);
    var no2Element = document.getElementById(no2Id);
    var totalValue = document.getElementById(totalId).value;


    if (isAcceptable(totalValue) && totalValue != "") {
        no1Element.disabled = true;
        no1Element.value = "";
        no2Element.disabled = true;
        no2Element.value = "";
    } else {
        no1Element.disabled = false;
        no2Element.disabled = false;
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