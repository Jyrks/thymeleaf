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

$('#telliVaagen').on('click', function() {
    console.dir($('#dateInput').val());

    fetch('/tellimus', {
        headers: { "Content-Type": "application/json; charset=utf-8" },
        method: 'POST',
        body: JSON.stringify({
            username: 'Elon Musk',
            email: 'elonmusk@gmail.com'
        })
    })
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

