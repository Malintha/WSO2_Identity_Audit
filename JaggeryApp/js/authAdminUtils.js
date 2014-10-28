var resarr;

function clickgetdata() {
    console.log('btn clicked');
    $.get("service.jag", null, function(data){
        // populateTable(data);
        resarr = JSON.parse(data);
        test();
    });
}

function test() {
    populateTable();
}

function populateTable() {
    $('#example').dataTable( {
    	"bProcessing": true,
        "aaData": resarr,
        "paging":   true,
        "ordering": true,
        "info":     true,
        "aoColumns": [
            { "mData": "Timestampstr", "class":"center"},
            { "mData": "UserName","class":"center" },
            { "mData": "RemoteHost","class":"center" },
            { "mData": "Response","class":"center" }
        ]
    } );
}

$(document).ready(function() {
    $('.input-daterange').datepicker({
        todayBtn: "linked"
    });
});