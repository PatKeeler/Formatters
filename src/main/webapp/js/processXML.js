
/**
 * String of animation dots
 */
var dots = ".....";

/**
 * Number of dots to display
 */
var numberOfDots = 0;


function getFormattedXml() {

    $("[name=xmlOut]").val("Formatting");
	
	//Set function and interval to show working
	var t = setInterval("animateFormatting()", 500);    

	var parameters = new Object();

    parameters.inputXML=$("[name=inputXML]").val();

	if ($("[name=indentCheckBox]").is(":checked")) {
	    parameters.indent=true;
	    parameters.indentAmount=$("[name=indentAmount]").val();
	}

    $.post("ReformatXml",
             parameters ,
            function(data){
  		 	    clearInterval(t);
                updatePage(data);
            },
            "html"
    );
}

function updatePage(data) {
	
	$("[name=xmlOut]").val(data);
    
}


//Timer to display formatting animation
function animateFormatting() {
	
	numberOfDots = numberOfDots + 1;
	
	if (numberOfDots > 5) {
		numberOfDots = 1;
	}
	
	$("[name=xmlOut]").val("Formatting" + dots.substring(0, numberOfDots));
}

/*
 * This is the about information for the XML formatter.
 */
function getXmlAbout() {

	alert("This XML formatter was created to remove invalid character errors during SoapUI "
			+ "processing that normally \noccur when you cut a SOAP message from a log and try "
			+ "to run it in SOAP UI (or any SOAP tool).\n\nThis formatter takes in an XML string, "
			+ "removes all white space and special characters and re-formats the XML."
			+ "\nThe output should run in SoapUI, error free."
			+ "\n\nThe \"Indent options\" are self evident: \n    indent or not. \n    indent amount. "
			+ "\n\nIf you have suggestions for enhancements or encounter errors send an email "
			+ "to keelerpl@gmail.com and I'll \nlook into it.");

	return true;
}
