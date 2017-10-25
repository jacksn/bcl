var ajaxUrl = "api/v1";
var legalEntitiesUrl = "/legalEntity";
var contractsUrl = "/contract";

function contractChange() {
    var contractId = $('#contractId').val();
    if (contractId) {
        $.ajax({
            type: "GET",
            url: ajaxUrl + contractsUrl + '/' + contractId,
            success: updateRecipientByContract,
            error: showServerError
        });
    } else {
        $.ajax({
            type: "GET",
            url: ajaxUrl + legalEntitiesUrl,
            success: fillRecipients,
            error: showServerError
        });
    }
}

function updateRecipientByContract(contract) {
    fillRecipients([contract.signer]);
}

function fillRecipients(recipients) {
    var recipientId = $('#recipientId');
    var senderAccountId = $('#senderAccountId').val();
    recipientId.empty();

    if (recipients) {

        $.each(recipients, function (i, recipient) {
            if (recipient.accounts.length === 1 && recipient.accounts[0].id == senderAccountId) {
                return;
            }
            if (i === 0) {
                updateRecipientDetails(recipient);
            }
            recipientId.append($('<option>', {
                value: recipient.id,
                text: recipient.fullName
            }))
        });
    }
}

function updateRecipientData() {
    var loader = $('#loader');
    loader.removeClass("hide");

    $.ajax({
        type: "GET",
        url: ajaxUrl + legalEntitiesUrl + '/' + $('#recipientId').val(),
        success: updateRecipientDetails,
        error: showServerError
    });

    loader.addClass("hide");
}

function updateRecipientDetails(recipient) {
    var recipientDetails = $('#recipientDetails');

    recipientDetails.find('#recipientAddress').text(recipient.address);
    recipientDetails.find('#recipientInn').text(recipient.inn);
    recipientDetails.find('#recipientKpp').text(recipient.kpp);
    recipientDetails.find('#recipientOgrn').text(recipient.ogrn);

    fillAccounts(recipient.accounts);
}

function fillAccounts(accounts) {
    var senderAccountId = $('#senderAccountId').val();
    var recipientAccount = $('#recipientAccount');
    recipientAccount.empty();

    var currencyCode = $('#currencyCode').val();

    $.each(accounts, function (i, account) {
        if (account.currencyCode === currencyCode) {
            if (account.id != senderAccountId && account.status == 'ACTIVE') {
                recipientAccount.append($('<option>', {
                    value: account.id,
                    text: account.name
                    + " (" + account.number + ")"
                    + " в " + account.bank.name
                    + ", код валюты " + account.currencyCode
                }))
            }
        }
    });
}

function showServerError() {
    showNotification('danger', 'Ошибка при отправке запроса на сервер.')
}
