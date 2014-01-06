// Function to hide sections 
$(document).ready(function() {
    var data = {};

    var $tabs = $('.tabbable li');

    $('.back').on('click', function() {
        $tabs.filter('.active').prev('li').find('a[data-toggle="tab"]').tab('show');
    });

    $('.next').on('click', function() {
        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
    });

    //handlers for assays
    $('button#addAssayButton').click(function() {
        var selected = $('#selectAssay :selected').text();
        if (selected === "Other") {
            //append new assay code
            $('#otherAssay').removeClass("hidden");
        } else if (selected === "Restriction Digest and Gel Electrophoresis") {
            $('#restrictionDigestandGel').removeClass("hidden");
        } else if (selected === "Flow Cytometry") {
            $('#flowCytometry').removeClass("hidden");
        }

    });
    $('button#removeRDButton').click(function() {
        $('#restrictionDigestandGel').addClass("hidden");
        $('#restrictionDigestandGel input').each(function() {
            //clear the values
            $(this).val("");
        });
    });
    $('button#removeFlowCytometryButton').click(function() {
        $('#flowCytometry').addClass("hidden");
        $('#flowCytometry input').each(function() {
            //clear the values
            $(this).val("");
        });
    });
    $('button#removeOtherButton').click(function() {
        $('#otherAssay').addClass("hidden");
        $('#otherAssay input').each(function() {
            //clear the values
            $(this).val("");
        });
    });




    //JSON object 
    function createJSON() {
        var jsonObj = [];
        $("input").each(function() {

            var id = $(this).attr('id');
            var value = $(this).val();

            item = {};
            item ["id"] = id;
            item["value"] = value;

            jsonObj.push(item);
        });
        return jsonObj;

    }

    $('#designButton').click(function() {
        //collect information here
        data["name"] = $('#partName').val();
        data["summary"] = $('#summary').val();
        data["deviceImage"] = $('#summary').val();
        var contactInformation = {};
        data["contactInformation"] = contactInformation;
        var designInformation = {};
        data["designInformation"] = designInformation;
        var standardAssays = {};
        data["standardAssays"] = standardAssays;
        var functionalityAssays = {};
        data["functionalityAssays"] = {};
        data = createJSON();
        alert(JSON.stringify(data))

    });











});
