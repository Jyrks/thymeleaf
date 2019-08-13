var map = new Map();

var lisaVaagen = document.getElementById("lisaVaagen");
var selectVaagen = document.getElementById("selectVaagen");

lisaVaagen.addEventListener("click", function (ev) {
    var value = selectVaagen.options[selectVaagen.selectedIndex].text;
    if (map.get(value) == null) {
        map.set(value, 1);
    } else {
        map.set(value, map.get(value) + 1);
    }

    if (value === "Väike") {
        $('#väikeNr').text(map.get("Väike") + "x Väike");
        $('#väikeDiv').show();
        $('#lisaKandikText').hide();
    }
    if (value === "Suur") {
        $('#suurNr').text(map.get("Suur") + "x Suur");
        $('#suurDiv').show();
        $('#lisaKandikText').hide();
    }
});

var loadFunction = function() {
    $('#väikeDiv').hide();
    $('#suurDiv').hide();
    $('#lisaKandikText').hide();
    if (decodeURIComponent(window.location.href).includes('väike')) {
        map.set("Väike", 1);
        $('#väikeNr').text(map.get("Väike") + "x Väike");
        $('#väikeDiv').show();
    }
    if (decodeURIComponent(window.location.href).includes('suur')) {
        map.set("Suur", 1);
        $('#suurNr').text(map.get("Suur") + "x Suur");
        $('#suurDiv').show();
    }
};

$('input[type="text"]').bind("input propertychange", function () {
    $(this).css('border', '');
});

$('#telliVaagen').on('click', function() {
    if (map.size === 0) {
        $('#lisaKandikText').show();
        return;
    }

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

    var array = [];

    map.forEach(function (value, key, map) {
        array.push(key + " " + value);
    });

    fetch('/order', {
        headers: { "Content-Type": "application/json; charset=utf-8" },
        method: 'POST',
        body: JSON.stringify({
            personName: $('#nameInput').val(),
            platters: array,
            email: $('#emailInput').val(),
            phoneNumber: $('#phoneNumberInput').val(),
            time: $('#selectTime option:selected').text(),
            date: $('#dateInput').val()
        })
    });
    window.location.href = '/tellitud';
});
