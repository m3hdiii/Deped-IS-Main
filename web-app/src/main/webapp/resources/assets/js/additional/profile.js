function profile() {

    checkUser();

}

function checkUser() {
    $.ajax({
        url: "/section-list/", type: "POST",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (data, textStatus, jqXHR) {
            alert(JSON.stringify(data));
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("Failure");
        }
    });
    return false;
};