function addErrorHighlight(field) {
    $(field).parent().addClass("has-error");
}

function removeErrorHighlight(field) {
    $(field).parent().removeClass('has-error');
}

function showNotification(type, message) {
    $.notify({
            message: message
        }, {
            type: type,
            placement: {
                from: "bottom",
                align: "center"
            },
            mouse_over: "pause"
        }
    );
}
