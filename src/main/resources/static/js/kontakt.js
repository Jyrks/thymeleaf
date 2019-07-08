$('#sendEmail').on('click', function() {
    console.log("Lood");
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
