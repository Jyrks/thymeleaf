var map = new Map();

function isGoodDate(dt){
    var reGoodDate = /^(0?[1-9]|[12][0-9]|3[01])[/]((0?[1-9]|1[012])[/](19|20)?[0-9]{2})*$/;
    return reGoodDate.test(dt);
}

$('#blokeeri').on('click', function() {
    if ($('#datepicker').val() === '' || !isGoodDate($('#datepicker').val())) {
        $('#datepicker').css('border', '1px solid red');
        return;
    }

    fetch('/order/blockDate', {
        headers: { "Content-Type": "application/json; charset=utf-8" },
        method: 'POST',
        body: $('#datepicker').val()
    });

    location.reload();
});

var orderMap = new Map();
var blockedList = [];
$(function () {
    fetch('/order/getOrders')
        .then(function(response) {
            return response.json();
        })
        .then(function(myJson) {
            myJson.orders.forEach(function (value) {
                var res = value.split(" ");
                if (orderMap.get(res[0]) == null) {
                    orderMap.set(res[0], [])
                }
                orderMap.get(res[0]).push(res[1]);
            });

            myJson.blockedDates.forEach(function (value) {
                blockedList.push(value)
            });

            var unavailableDates = [];
            orderMap.forEach(function(value, key) {
                if (getAvailableTimesByDate(key).length === 0) {
                    unavailableDates.push(key);
                }
            });

            blockedList.forEach(function(value) {
                unavailableDates.push(value.split(" ")[0]);
            });


            $('#datepicker').datepicker({
                uiLibrary: 'bootstrap4',
                weekStartDay: 1,
                minDate: new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate() + 3),
                disableDates: unavailableDates,
                format: 'dd/mm/yyyy',
            });
        });
});

var availableTimes = ['19:00','20:00','21:00','22:00'];
$('#datepicker').change(function () {
    $(this).css('border', '');

    availableTimes = getAvailableTimesByDayName($('#datepicker').val());
    getAvailableTimesByDayName($('#datepicker').val());

    $("#selectTime").empty();
    for (let i = 0; i < availableTimes.length; i++) {
        if (getAvailableTimesByDate($('#datepicker').val()).includes(availableTimes[i])) {
            $("#selectTime").append($("<option></option>").attr("value", availableTimes[i]).text(availableTimes[i]));
        }
    }
});

function getAvailableTimesByDate(date) {
    availableTimes = getAvailableTimesByDayName(date);
    skipNext = false;
    freeTimes = [];
    for (let i = 0; i < availableTimes.length; i++) {

        if (skipNext === true || orderMap.get(date) != null && orderMap.get(date).includes(availableTimes[i])) {
            if (skipNext === true) {
                skipNext = false
            } else {
                skipNext = true;
            }
            continue;
        }

        // Skips time before the next as well
        if (i < availableTimes.length && orderMap.get(date) != null && orderMap.get(date).includes(availableTimes[i + 1])) {
            continue;
        }

        freeTimes.push(availableTimes[i]);
    }

    return freeTimes;
}

function getAvailableTimesByDayName(date) {
    var day = new Date(Date.UTC(date.slice(6,10), parseInt(date.slice(3,5)) - 1, date.slice(0,2), 0, 0, 0)).toLocaleDateString('en-us', { weekday: 'long' });

    if (day === "Saturday" || day === "Sunday") {
        return ['10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00','20:00','21:00','22:00']
    }

    return ['19:00','20:00','21:00','22:00']
}
