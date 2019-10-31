
/**
 * String of animation dots
 */
var dots = ".....";

/**
 * Number of dots to display
 */
var numberOfDots = 0;

//Added comment for testing


function getFormattedSql() {

	$("[name=outputSQL]").val("Formatting");
	
	//Set function and interval to show working
	var t = setInterval("animateFormatting()", 500);    

    var parameters = new Object();

    parameters.inputSQL = $("[name=inputSQL]").val();

    if ($("[name=indentCheckBox]").is(":checked")) {
        parameters.indent=true;
        parameters.indentAmount=$("[name=indentAmount]").val();
    }

    parameters.selectedStyle = $("[name=styleRadio]:checked").val();

 	if ($("[name=addQuotesAndReformatCheckBox]").is(":checked")) {
		 parameters.addQuotesAndReformat=true;
    }

	if ($("[name=addQuotesOnlyCheckBox]").is(":checked")) {
		 parameters.addQuotesOnly=true;
	}   

    if ($("[name=removeQuotesAndReformatCheckBox]").is(":checked")) {
        parameters.removeQuotesAndReformat=true;
   }

   if ($("[name=removeQuotesOnlyCheckBox]").is(":checked")) {
        parameters.removeQuotesOnly=true;
   }

    $.ajax({
        url: "ReformatSql",
        type: "POST",
        data: parameters,
        dataType: "html",
        success: function (result) {
            clearInterval(t);
            updatePage(result);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.status);
            alert(thrownError);
        }
    });
}

function updatePage(data) {

	$("[name=outputSQL]").val(data);
}


//Timer to display formatting animation
function animateFormatting() {
	
	numberOfDots = numberOfDots + 1;
	
	if (numberOfDots > 5) {
		numberOfDots = 1;
	}
	
	$("[name=outputSQL]").val("Formatting" + dots.substring(0, numberOfDots));
}

/*
 * This is the about information for the SQL formatter.
 */
function getSqlAbout() {

	alert("This SQL formatter will format most generic sql commands but may not include all the "
            + "most common commands at this point."
            + "\n\nThis formatter is not a validator, it will format generic sql statements that are "
            + "properly structured."
			+ "\n\nThis formatter will format a single line command: \n\t select this as "
			+ "that from something where you = me\nor a multi line command: \n\t select this as that"
			+ "\n\t from something \n\t where you = me"
			+ "\n\nThe \"Indent options\" are self evident: \n\t indent or not. \n\t indent amount. "
			+ "\n\nThe Java String \"Quote\" options can be used when you need to paste the results into a Java "
			+ "program as a String:"
			+ "\n\t The first option will format the input sql and add quotes for use as a Java String. "
			+ "\n\t The second option will quote any data you paste into the input box, without "
			+ "formatting. "
            + "\n\nThe Java String \"Remove Quote\" options can be used when you need to cut a string from a "
            + "java program and \nremove the quotes for use elsewhere, as follows: "
            + "\n\t The first option will remove quotes and reformat the input sql. "
            + "\n\t The second option will remove quotes only (any input), without formatting. "
            + "\n\n**NOTE - For the remove quotes to work please note things like:"
            + "\n\t For java Strings on multiple lines the \" and + should be on the same line."
            + "\n\t All java Strings end with a semi-colon, so the last line should end with \" and ;."
			+ "\n\nIf you have suggestions for enhancements or encounter errors send an email "
			+ "to Pat@PatsTools.com and I'll look into it. ");

	return true;
}
