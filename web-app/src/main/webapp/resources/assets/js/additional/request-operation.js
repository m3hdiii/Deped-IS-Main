function processReason(requestDetailsStatusId, key) {

    var selectElement = document.getElementById(requestDetailsStatusId);
    var selectValue = selectElement.options[selectElement.selectedIndex].value;
    alert(selectValue);


    var textAreaElement = document.getElementById(key);
    var disApprovalField = ".disapprovalMessage";
    var cancellationField = ".cancellationReason";
    var textAreaName = "map['" + key + "']";
    if (selectValue == "DISAPPROVED") {
        textAreaName += disApprovalField;
        textAreaElement.name = textAreaName;
        textAreaElement.disabled = false;
        return;
    }

    if (selectValue == "CANCELED") {
        textAreaName += cancellationField;
        textAreaElement.name = textAreaName;
        textAreaElement.disabled = false;
        return;
    }

    textAreaElement.disabled = true;
    textAreaElement.value = "";
}