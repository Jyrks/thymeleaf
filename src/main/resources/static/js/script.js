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
        history.pushState({}, null, "/telli");
    }
    if (decodeURIComponent(window.location.href).includes('suur')) {
        map.set("Suur", 1);
        $('#suurNr').text(map.get("Suur") + "x Suur");
        $('#suurDiv').show();
        history.pushState({}, null, "/telli");
    }
};

$('input[type="text"]').on("input propertychange", function () {
    $(this).css('border', '');
});

function isGoodDate(dt){
    var reGoodDate = /^((0?[1-9]|1[012])[/](0?[1-9]|[12][0-9]|3[01])[/](19|20)?[0-9]{2})*$/;
    return reGoodDate.test(dt);
}

$('#telliVaagen').on('click', function() {
    var empty = false;
    if (map.size === 0) {
        $('#lisaKandikText').show();
        empty = true;
    }

    if ($('#datepicker').val() === '' || !isGoodDate($('#datepicker').val())) {
        $('#datepicker').css('border', '1px solid red');
        empty = true;
    }

    $('input[type="text"]').each(function(){
        if ($(this).val().trim() === '') {
            $(this).css('border', '1px solid red');
            empty = true;
        }
    });

    if (empty) {
        return;
    }

    var array = [];

    map.forEach(function (value, key, map) {
        array.push(key + " " + value);
    });

    fetch('/order/createOrder', {
        headers: { "Content-Type": "application/json; charset=utf-8" },
        method: 'POST',
        body: JSON.stringify({
            personName: $('#nameInput').val(),
            platters: array,
            email: $('#emailInput').val(),
            phoneNumber: $('#phoneNumberInput').val(),
            time: $('#selectTime option:selected').text(),
            date: $('#datepicker').val()
        })
    });

    window.location.href = '/tellitud';
});

var orderMap = new Map();
$(function () {
    fetch('/order/getOrders')
        .then(function(response) {
            return response.json();
        })
        .then(function(myJson) {
            myJson.forEach(function (value) {
                var res = value.split(" ");
                if (orderMap.get(res[0]) == null) {
                    orderMap.set(res[0], [])
                }
                orderMap.get(res[0]).push(res[1]);
            });

            var unavailableDates = [];
            orderMap.forEach(function(value, key) {
                if (value.length == 4) {
                    unavailableDates.push(key);
                }
            });

            $('#datepicker').datepicker({
                uiLibrary: 'bootstrap4',
                weekStartDay: 1,
                minDate: new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate() + 1),
                disableDates: unavailableDates
            });
        });
});

var availableDates = ['19:00','20:00','21:00','22:00'];
$('#datepicker').change(function () {
    $(this).css('border', '');

    $("#selectTime").empty();
    for (let i = 0; i < availableDates.length; i++) {
        if (orderMap.get($('#datepicker').val()) != null && orderMap.get($('#datepicker').val()).includes(availableDates[i])) {
            continue;
        }
        $("#selectTime").append($("<option></option>").attr("value", availableDates[i]).text(availableDates[i]));
    }
});
