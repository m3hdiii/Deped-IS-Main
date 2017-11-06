function toggle(source) {
    var checkboxes = document.getElementsByClassName('orderStateClass');
    for (var i = 0, n = checkboxes.length; i < n; i++) {
        checkboxes[i].checked = source.checked;
    }
}

function uncheckedAction(source) {
    var selectAllCheckbox = document.getElementById('selectAllId');
    if(source.checked == true){
        source.value = "APPROVED";
        var checkboxes = document.getElementsByClassName('orderStateClass');
        for (var i = 0, n = checkboxes.length; i < n; i++) {
            if(checkboxes[i].checked == false){
                return;
            }
        }
        selectAllCheckbox.checked = true;
    }else{
        source.value = "DISAPPROVED";
        selectAllCheckbox.checked = false;
    }
}