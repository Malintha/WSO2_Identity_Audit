var tableData = null;
var isFirstSearch = true;

$(document).ready(function () {
    loadDefaultDatafromDB(function (resultArray) {
        populateTable(resultArray);
        generatePieData();
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
        if (isFirstSearch) {
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

function displayGraph() {
    nv.addGraph(function () {
        var width = 300,
            height = 300;

        var chart = nv.models.pieChart()
            .x(function (d) {
                return d.key
            })
            .y(function (d) {
                return d.y
            })
            .color(d3.scale.category10().range())
            .width(width)
            .height(height);

        d3.select("#test1")
            .datum(dataarr)
            .transition().duration(1200)
            .attr('width', width)
            .attr('height', height)
            .call(chart);

        chart.dispatch.on('stateChange', function (e) {
            nv.log('New State:', JSON.stringify(e));
        });

        return chart;
    });
}

function pieData(key, y) {
    this.key = key;
    this.y = y;
}

var dataarr = [];

function generatePieData() {
    var pd1 = new pieData("True", 14);
    var pd2 = new pieData("False", 19);
    dataarr.push(pd1);
    dataarr.push(pd2);

    console.log(dataarr[0]);
    displayGraph();
}

