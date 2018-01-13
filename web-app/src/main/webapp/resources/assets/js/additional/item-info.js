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
            let colorElemValue = colourIdElem.value;
            if (colorElemValue) {
                colourClassElem.value = colorElemValue;
            }

            let purchasePriceValue = document.getElementById(purchasePriceId).value;
            if (purchasePriceValue) {
                document.getElementsByClassName(purchasePriceClass)[row].value = document.getElementById(purchasePriceId).value;
            }

            let equipmentSerialNumberValue = document.getElementById(equipmentSerialNoId).value;
            if (equipmentSerialNumberValue) {
                document.getElementsByClassName(equipmentSerialNoClass)[row].value = equipmentSerialNumberValue;
            }


            let materialClassElem = document.getElementsByClassName(materialClass)[row];
            let materialIdElem = document.getElementById(materialId);
            let materialElemValue = materialIdElem.value;
            if (materialElemValue) {
                materialClassElem.value = materialElemValue;
            }

            let availabilityClassElem = document.getElementsByClassName(availabilityClass)[row];
            let availabilityIdElem = document.getElementById(availabilityId);
            let availabilityElemValue = availabilityIdElem.value;
            if (availabilityElemValue) {
                availabilityClassElem.value = availabilityElemValue;
            }

            let conditionClassElem = document.getElementsByClassName(conditionClass)[row];
            let conditionIdElem = document.getElementById(conditionId);
            let conditionElemValue = conditionIdElem.value;
            if (conditionElemValue) {
                conditionClassElem.value = conditionElemValue;
            }

            let weightValue = document.getElementById(weightInGramId).value;
            if (weightValue) {
                document.getElementsByClassName(weightInGramClass)[row].value = weightValue;
            }

            let lifeSpanValue = document.getElementById(lifeSpanId).value;
            if (lifeSpanValue) {
                document.getElementsByClassName(lifeSpanClass)[row].value = lifeSpanValue;
            }
        }
    }
}

function isNumber(n) {
    return /^-?[\d.]+(?:e-?\d+)?$/.test(n);
}