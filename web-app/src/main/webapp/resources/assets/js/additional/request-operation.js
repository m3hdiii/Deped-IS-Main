function processReason(requestDetailsStatusId, key) {

    let selectElement = document.getElementById(requestDetailsStatusId);
    let selectValue = selectElement.options[selectElement.selectedIndex].value;

    let textAreaElement = document.getElementById(key);
    let disApprovalField = ".disapprovalMessage";
    let cancellationField = ".cancellationReason";
    let textAreaName = "map['" + key + "']";
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