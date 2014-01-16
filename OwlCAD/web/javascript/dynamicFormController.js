// Javascript for dynamicForm
// Function to hide sections 
$(document).ready(function() {
    
    $.get("ParserServlet",function(data) {
       //use data to fill out parts of the form.
       data = $.parseJSON(data);
//       $('#name').val(data['name']);
//        $("#summary").text(data.summary);
        $("#name").text(data.name);
    });
    
    
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
        } else if (selected === "Restriction Map") {
            $('#restrictionMap').removeClass("hidden");
        } else if (selected === "Flow Cytometry") {
            $('#functionalityAssays').removeClass("hidden");
        }

    });
    $('button#removeRDButton').click(function() {
        $('#restrictionMap').addClass("hidden");
        $('#restrictionMap input').each(function() {
            //clear the values
            $(this).val("");
        });
    });
    $('button#removeFlowCytometryButton').click(function() {
        $('#functionalityAssays').addClass("hidden");
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
        data["name"] = $('#name').val();
        data["summary"] = $('#summary').val();
        data["deviceImage"] = $('#pigeonImage').val();
        //gather contact information
        var contactInformation = {};
        $('div#contactInformation input').each(function() {
            var key = $(this).attr("id");
            var value = $(this).val();
            contactInformation[key] = value;
        });
        $('div#contactInformation textarea').each(function() {
            var key = $(this).attr("id");
            var value = $(this).val();
            contactInformation[key] = value;
        });
        data["contactInformation"] = contactInformation;
        //gather design information
        var designInformation = {};
        $('div#standardDesignInformation input').each(function() {
            var key = $(this).attr("id");
            var value = $(this).val();
            contactInformation[key] = value;
        });
        $('div#standardDesignInformation textarea').each(function() {
            var key = $(this).attr("id");
            var value = $(this).val();
            contactInformation[key] = value;
        });
        data["designInformation"] = designInformation;


        var restrictionMap = {};
        //DEVINA POPULATE THIS ARRAY
        $('div#restrictionMap input').each(function() {
            var key = $(this).attr("id");
            var value = $(this).val(); 
            restrictionMap[key] = value; 
        })
        
        data["restrictionMap"]=restrictionMap;
        var functionalityAssays ={};
        //DEVINA POPULATE THIS ARRAY
        $('div#functionalityAssays div.experiment input').each(function() {
            var key = $(this).attr("id");
            var value = $(this).val(); 
            functionalityAssays[key] = value; 
        });
        var pre ={};
        $('div#functionalityAssays div.setup div#preinductionGrowthConditions input').each(function() {
            var key = $(this).attr("id");
            var value = $(this).val(); 
            pre[key] = value; 
        });

        var post ={};
        $('div#functionalityAssays div.setup div#inductionGrowthConditions input').each(function() {
            var key = $(this).attr("id");
            var value = $(this).val(); 
            post[key] = value; 
        });
        functionalityAssays["preInductionGrowthConditions"] = pre;
        functionalityAssays["inductionGrowthConditions"] = post;
        


        data["functionalityAssays"] =functionalityAssays;
        $.post("DataServlet",{"sending":JSON.stringify(data)},function(){
            window.location.replace("/OwlCAD/output.html")
        });

    });











});
