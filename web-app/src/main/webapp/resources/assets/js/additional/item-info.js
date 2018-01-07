function insertDummy(id, length) {

    let officeSerialNoTemplate = "officeSerialNo-";
    let officeSerialNoChangingTemplate = "officeSerialNoChanging-";
    let colourTemplate = "colour-";
    let purchasePriceTemplate = "purchasePrice-";
    let equipmentSerialNoTemplate = "equipmentSerialNo-";
    let materialTemplate = "material-";
    let availabilityTemplate = "availability-";
    let conditionTemplate = "condition-";
    let weightInGramTemplate = "weightInGram-";
    let lifeSpanTemplate = "lifeSpan-";

    let officeSerialNoId = officeSerialNoTemplate + id;
    let officeSerialNoChangingId = officeSerialNoChangingTemplate + id;
    let colourId = colourTemplate + id;
    let purchasePriceId = purchasePriceTemplate + id;
    let equipmentSerialNoId = equipmentSerialNoTemplate + id;
    let materialId = materialTemplate + id;
    let availabilityId = availabilityTemplate + id;
    let conditionId = conditionTemplate + id;
    let weightInGramId = weightInGramTemplate + id;
    let lifeSpanId = lifeSpanTemplate + id;

    let officeSerialNoClass = officeSerialNoTemplate + id;
    let colourClass = colourTemplate + id;
    let purchasePriceClass = purchasePriceTemplate + id;
    let equipmentSerialNoClass = equipmentSerialNoTemplate + id;
    let materialClass = materialTemplate + id;
    let availabilityClass = availabilityTemplate + id;
    let conditionClass = conditionTemplate + id;
    let weightInGramClass = weightInGramTemplate + id;
    let lifeSpanClass = lifeSpanTemplate + id;


    for (let column = 0; column < 9; column++) {
        var temp = 1;
        for (let row = 0; row < length; row++) {
            let constantVal = document.getElementById(officeSerialNoId).value;
            let changingVal = document.getElementById(officeSerialNoChangingId).value;
            let eachRowOfficeSerialNumber = document.getElementsByClassName(officeSerialNoClass)[row];
            if (isNumber(changingVal)) {
                eachRowOfficeSerialNumber.value = constantVal + (parseInt(changingVal) + (temp++));
            }

            let colourClassElem = document.getElementsByClassName(colourClass)[row];
            let colourIdElem = document.getElementById(colourId);
            colourClassElem.value = colourIdElem.value;

            document.getElementsByClassName(purchasePriceClass)[row].value = document.getElementById(purchasePriceId).value;
            document.getElementsByClassName(equipmentSerialNoClass)[row].value = document.getElementById(equipmentSerialNoId).value;

            let materialClassElem = document.getElementsByClassName(materialClass)[row];
            let materialIdElem = document.getElementById(materialId);
            materialClassElem.value = materialIdElem.value;

            let availabilityClassElem = document.getElementsByClassName(availabilityClass)[row];
            let availabilityIdElem = document.getElementById(availabilityId);
            availabilityClassElem.value = availabilityIdElem.value;

            let conditionClassElem = document.getElementsByClassName(conditionClass)[row];
            let conditionIdElem = document.getElementById(conditionId);
            conditionClassElem.value = conditionIdElem.value;

            document.getElementsByClassName(weightInGramClass)[row].value = document.getElementById(weightInGramId).value;
            document.getElementsByClassName(lifeSpanClass)[row].value = document.getElementById(lifeSpanId).value;
        }
    }
}

function isNumber(n) {
    return /^-?[\d.]+(?:e-?\d+)?$/.test(n);
}