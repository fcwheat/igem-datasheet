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
    $('#designButton').click(function() {
        //collect information here
        var data = {};
        data["name"] = $('#partName').val();
        data["summary"] = $('#summary').val();
        data["deviceImage"] = $('#pigeonImage').val();
        //gather contact information
        var contactInformation = {};
        $('div#contactInfo input').each(function() {
            var key = $(this).attr("id");
            var value = $(this).val();
            contactInformation[key] = value;
        });
        $('div#contactInfo textarea').each(function() {
            var key = $(this).attr("id");
            var value = $(this).val();
            contactInformation[key] = value;
        });
        data["contactInformation"] = contactInformation;
        //gather design information
        var designInformation = {};
        $('div#standardDesignInfo input').each(function() {
            var key = $(this).attr("id");
            var value = $(this).val();
            contactInformation[key] = value;
        });
        $('div#standardDesignInfo textarea').each(function() {
            var key = $(this).attr("id");
            var value = $(this).val();
            contactInformation[key] = value;
        });
        data["designInformation"] = designInformation;


        var standardAssays = [];
        //DEVINA POPULATE THIS ARRAY
        $('div#standardAssays input').each(function() {
            var key = $(this).attr("id");
            var value = $(this).val(); 
            standardAssay[key] = value; 
        })
        
        data["standardAssays"]=standardAssays;
        var functionalityAssays =[];
        //DEVINA POPULATE THIS ARRAY
        $('div#functionalityAssays input').each(function() {
            var key = $(this).attr("id");
            var value = $(this).val(); 
            functionalityAssays[key] = value; 
        })
        

        data["functionalityAssays"] =functionalityAssays
        alert(JSON.stringify(data));

    });











});
