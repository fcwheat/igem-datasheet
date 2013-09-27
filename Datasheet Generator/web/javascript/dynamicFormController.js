// Function to hide sections 
$(document).ready(function() {
    data = {};

    var $tabs = $('.tabbable li');

    $('.back').on('click', function() {
        $tabs.filter('.active').prev('li').find('a[data-toggle="tab"]').tab('show');
    });

    $('.next').on('click', function() {
        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
    });


    // Functions for dropdown menus     
    $('#rd1').click(function() {
        $("#restrictionDigestandGel").show();
    });
    $('#rd1').click(function() {
        $("#assayType1").hide();
    });
    $('#flow1').click(function() {
        $("#flowCytometry").show();
    });
    $('#flow1').click(function() {
        $("#assayType1").hide();
    });

    // If loop for adding addition assays 
    $('#btn5').click(function() {
        $("#assayType1").show();
        $("#restrictionDigestandGel").hide();
        $("#flowCytometry").hide();
    });
    $('#btn7').click(function() {
        $("#assayType1").show();
        $("#restrictionDigestandGel").hide();
        $("#flowCytometry").hide();
    });
    $('#btn6').click(function() {
        $("#assayType1").hide();
        $("#restrictionDigestandGel").hide();
        $("#flowCytometry").hide();
        $("#other").show();
    });
    $('#btn8').click(function() {
        $("#assayType1").hide();
        $("#restrictionDigestandGel").hide();
        $("#flowCytometry").hide();
        $("#other").show();
    });
});
