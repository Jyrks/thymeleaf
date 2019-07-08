$('input[type="text"]').bind("input propertychange", function () {
    $(this).css('border', '');
});

$('#emailContent').bind("input propertychange", function () {
    $(this).css('border', '');
});

$('#sendEmail').on('click', function() {
    var empty = false;
    $('input[type="text"]').each(function(){
        if ($(this).val().trim() === '') {
            $(this).css('border', '1px solid red');
            empty = true;
            return false;
        }
    });

    if (empty) {
        return;
    }

    if (!$('#emailContent').val()) {
        $('#emailContent').css('border', '1px solid red');
        return false;
    }

    fetch('/sendEmail', {
        headers: { "Content-Type": "application/json; charset=utf-8" },
        method: 'POST',
        body: JSON.stringify({
            emailAddress: $('#emailInput').val(),
            email: $('#emailContent').val(),
            name: $('#nameInput').val()
        })
    });

    window.location.href = '/email-saadetud';
});
