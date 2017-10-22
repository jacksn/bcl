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

function filterEntries(parentName, entryName, id) {
    $('[id=' + parentName + ']').find('[id^=' + entryName + '_]').each(function () {
        if (id === 'ALL' || this.id === entryName + '_' + id) {
            this.style.display = 'block';
        } else {
            this.style.display = 'none';
        }
    });
}