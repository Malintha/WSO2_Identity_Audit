var tableData = null;
var isFirstSearch = true;


//var pieData = null;

function onDateChanges(startDate,endDate) {
    console.log('date changes'+startDate+" , "+endDate+" , "+moment().subtract(1,'days').toISOString());
    tableData.fnClearTable();
    if(moment(endDate).isAfter(startDate)){
        loadDefaultDatafromDB(startDate,endDate,function (resultArray) {
            tableData.fnAddData(resultArray);
        });
        loadPromptDataFromDB(startDate,endDate, function(resultArray){
            console.log("hellooo"+resultArray[0].failCount);
            $('#promptBox').html("<a href='#' class='close' data-dismiss='alert'>&times;</a><strong>Warning! </strong>"+ resultArray[0].failCount+" login attempts failed for user "+resultArray[0].UserName+" for selected period");
        });
    }
}


$(document).ready(function () {

    loadDefaultDatafromDB(moment().subtract(1,'days').toISOString(),moment().toISOString(),function (resultArray) {
        populateTable(resultArray);
        var a = getStatsFromTable();
        console.log(a[0]+","+a[1]);
        generatePie(a[0],a[1]);
        console.log('done');
    });

    loadPromptDataFromDB(moment().subtract(1,'days').toISOString(),moment().toISOString(), function(resultArray){
        console.log("hellooo"+resultArray[0].failCount);
        $('#promptBox').html("<a href='#' class='close' data-dismiss='alert'>&times;</a><strong>Warning! </strong>"+ resultArray[0].failCount+" login attempts failed for user "+resultArray[0].UserName+" for selected period");
    });

});

function loadPromptDataFromDB(aStart,aEnd, cb){
    $.get("service.jag", {action:"promptSearch",start:aStart, end:aEnd}, function (data) {
        resultArray = JSON.parse(data);
        cb(resultArray);
    });
}

function loadDefaultDatafromDB(aStart, aEnd, cb) {
    $.get("service.jag", {action:"baseSearch", start:aStart, end:aEnd}, function (data) {
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
   var r = getStatsFromTable();
   var temp = [];
    console.log(r[0]+" , "+r[1]);

    var pd1 = new pieData("Successful", r[0]);
    var pd2 = new pieData("Failed", r[1]);

    temp.push(pd1);
    temp.push(pd2);

    d3.selectAll("svg > *").remove();

    var pieChart = nv.addGraph(function () {

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
            .datum(temp)
            .transition().duration(100)
            .attr('width', width)
            .attr('height', height)
            .call(chart);

        chart.dispatch.on('stateChange', function (e) {
            nv.log('New State:', JSON.stringify(e));
        });

        return chart;
    });
   // generatePie(r[0],r[1]);
}

function getStatsFromTable() {
//    var length = tableData.fnSettings().fnRecordsDisplay();
    var filteredrows = tableData._('tr', {"filter": "applied"});

    var trueCount = 0;
    var falseCount = 0;

    for(i=0;i<filteredrows.length;i++) {
        var rowData  = filteredrows[i];
//        console.log(rowData.Response)
        if(rowData.Response == 'true') {
            trueCount++;
        }
        else {
            falseCount++;
        }
    }
    var r=[trueCount,falseCount];
    return r;
}

function displayGraph() {

    var pieChart = nv.addGraph(function () {
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
            .transition().duration(100)
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

function generatePie(trueCount,falseCount) {

    var pd1 = new pieData("Successful", trueCount);
    var pd2 = new pieData("Failed", falseCount);
    dataarr.push(pd1);
    dataarr.push(pd2);

    displayGraph();
}

