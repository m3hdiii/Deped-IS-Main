function signUp() {
    var username = $("#username").val();
    var firstName = $("#firstName").val();
    var lastName = $("#lastName").val();
    var middleName = $("#middleName").val();
    var emailAddress = $("#emailAddress").val();
    var phoneNo1 = $("#phoneNo1").val();
    var phoneNo2 = $("#phoneNo2").val();
    var password = $("#password").val();
    var birthDate = $("#birthDate").val();
    var gender = $("#gender").val();
    var address = $("#address").val();
    var website = $("#website").val();
    var profilePicture = $("#profilePicture").val();
    var position = $("#position").val();
    var referrerName = $("#referrerName").val();
    var referrerAddress = $("#referrerAddress").val();
    var referrerNumber1 = $("#referrerPhoneNo1").val();
    var referrerNumber2 = $("#referrerPhoneNo2").val();
    var section = $("#section").val();
    var role = $("#roleName").val();
    var manager = $("#manager.lastName").val();

    checkUser(username, firstName, lastName, middleName, emailAddress, phoneNo1, phoneNo2, password, birthDate,
        gender, address, website, profilePicture, position, referrerName, referrerAddress, referrerNumber1,
        referrerNumber2, section, role, manager);
}

function checkUser(username, firstName, lastName, middleName, emailAddress, phoneNo1, phoneNo2, password, birthDate,
                   gender, address, website, profilePicture, position, referrerName, referrerAddress, referrerNumber1,
                   referrerNumber2, section, role, manager) {

    var formData = {
        username: username, firstName: firstName, lastName: lastName, middleName: middleName,
        emailAddress: emailAddress, phoneNo1: phoneNo1, phoneNo2: phoneNo2, password: password, birthDate: birthDate,
        gender: gender, address: address, website: website, picture: profilePicture, position: position,
        referrerName: referrerName, referrerAddress: referrerAddress, referrerPhoneNo1: referrerNumber1,
        referrerPhoneNo2: referrerNumber2, section: section, role: role, manager: manager
    };

    $.ajax({
        url: "/signup/", type: "POST",
        data: JSON.stringify(formData),
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (data, textStatus, jqXHR) {
            alert("Success");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("Failure");
        }
    });
    return false;
};