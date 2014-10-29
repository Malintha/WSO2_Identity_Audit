var tableData =null;
var isFirstSearch = true;

$(document).ready(function () {
    loadDefaultDatafromDB(function(resultArray) {
        populateTable(resultArray);
        console.log('done');
    });
    $('.input-daterange').datepicker({
        todayBtn: "linked"
    });
});

function loadDefaultDatafromDB(cb) {
    $.get("service.jag", null, function (data) {
        resultArray = JSON.parse(data);
        cb(resultArray);
    });
}

function populateTable(resultArray) {
    tableData = $('#example').on('search.dt', function () {
        if(isFirstSearch){
            console.log('first search');
            isFirstSearch = false;
        }
        else {
            searchEventFired();
        }
    }).dataTable({
        "bProcessing": true,
        "aaData": resultArray,
        "paging": true,
        "ordering": true,
        "info": true,
        "aoColumns": [
            { "mData": "Timestampstr", "class": "center"},
            { "mData": "UserName", "class": "center" },
            { "mData": "RemoteHost", "class": "center" },
            { "mData": "Response", "class": "center" }
        ]
    });
}

    var searchEventFired = function () {
        console.log(tableData.fnSettings().fnRecordsDisplay());
    }


