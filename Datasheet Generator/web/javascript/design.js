$(document).ready(function() {
    var tableCount = 0;
    $('button.tableButton').click(function() {
        var tableArea = $(this).parent();
        tableArea.append('<div><input placeholder="Table Name"/><table class="table table-bordered table-condensed table-striped" id="table' + tableCount +
                '"><thead><tr><th>Field</th><th>Value</th></tr></thead><tbody></tbody></table><button class="addRowButton btn">Add Row</button><button class="removeRowButton btn">Remove Row</button><button class="btn removeButton">Remove Table</button><hr/></div>');
        $('.removeButton').click(function() {
            $(this).parent().remove();
        });
        $('.addRowButton').click(function() {
            $(this).parent().find('table tbody').append('<tr><td><input/></td><td><input/></td></tr>');

        });
        $('.removeRowButton').click(function() {
            $(this).parent().find('tr:last').remove();
        });
        tableCount++;
    });
});