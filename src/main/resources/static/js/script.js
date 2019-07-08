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
        console.log('Empty');
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
    window.location.href = '/tellimus';
});

$j('#datepicker').datepicker.dates['ee'] = {
    days: ["Pühapäev", "Esmaspäev", "Teisipäev", "Kolmapäev", "Neljapäev", "Reede", "Laupäev"],
    daysShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
    daysMin: ["P", "E", "T", "K", "N", "R", "L"],
    months: ["Jaanuar", "Veebruar", "Märts", "Aprill", "Mai", "Juuni", "Juuli", "August", "September", "Oktoober", "November", "Detsember"],
    monthsShort: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
    today: "Täna"
};

$j(function () {
    $j('#datepicker').datepicker({
        format: "dd/mm/yyyy",
        weekStart: 1,
        autoclose: true,
        todayHighlight: true,
        showOtherMonths: true,
        selectOtherMonths: true,
        changeMonth: true,
        changeYear: true,
        language: 'ee',
        orientation: "button"
    });
});

$j('#datepicker').change(function(){
    $('#dateInput').css('border', '');
});
