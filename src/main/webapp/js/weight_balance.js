
/**
 * Aircraft data array.
 */
function getAircraftArray() {
	
	var aircraft = new Array();
	
	aircraft['N244SL']=['1522.95','107.2', '163260.24','0', '0'];
	aircraft['N422HL']=['1555.68','107.32','166955.9', '.2','311.69'];
	aircraft['N7518D']=['1519',   '105.7', '160558.3', '.3','455.7'];
	
	return aircraft;
}


/**
 * Default invalidNumber error return message.
 */
var invalidAircraft = "You must select an Aircraft ID to calculate Weight and Balance!";


/**
 * Variable to hold selected number
 * Default to 0 for validation check when answer button pressed.
 */
var selectedAircraft;


/**
 * Get Selected Aircraft.
 */
function getAircraftID(sel) {

	selectedAircraft = sel.options[sel.selectedIndex].value;
	
	if (selectedAircraft == 99) {
		alert(invalidAircraft);
		return;
	}
	
	var aircraftArray = getAircraftArray();
	
	var wbParms = aircraftArray[selectedAircraft];

	var bew = wbParms[0];
	var longArm = wbParms[1];
    var longMom = wbParms[2];
	var latArm = wbParms[3];
    var latMom = wbParms[4];
	
	$("[name=bewWeight]").val(bew);
	$("[name=bewLongArm]").val(longArm);
    $("[name=bewLongMom]").val(longMom);
	$("[name=bewLatArm]").val(latArm);
    $("[name=bewLatMom]").val(latMom);

	return;
}


/**
 * Validate radio buttons and start.
 */
function computeWB() {

	alert("This site is under construction, please try again later.")
	return;
	
}

