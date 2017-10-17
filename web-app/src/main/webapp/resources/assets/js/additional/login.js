function loginUser() {
    var username = $("#login-username").val();
    var password = $("#login-password").val();

    checkUser(username, password);

}

function checkUser(username, password) {
    var formData = {username: username, password: password};
    $.ajax({
        url: "/login/", type: "POST",
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