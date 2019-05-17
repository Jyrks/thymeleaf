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
    console.dir(map);
});

$('input[type="text"]').bind("input propertychange", function () {
    $(this).css('border', '');
});

$('#telliVaagen').on('click', function() {
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

    fetch('/order', {
        headers: { "Content-Type": "application/json; charset=utf-8" },
        method: 'POST',
        body: JSON.stringify({
            personName: $('#nameInput').val(),
            platterName: $('#selectVaagen').val(),
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
    $('input[type="text"]').css('border', '');
});
