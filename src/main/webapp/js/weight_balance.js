
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

//	alert("This site is under construction, please try again later.")
//	return;

    if ( $("[name=tailNumber]").val() == "99" ) {
        alert("You must select an aircraft to compute a weight and balance!");
    }

    if (! hasValue($("[name=pilotWeight]").val())) {
        alert("You must provide pilot weight to compute a weight and balance!");
        return;
    }

    //Populate the crew and passenger data.
    computeCrewAndPassCompartment();

    //Verify fuel values entered and compute, else return message.
    if (hasValue($("[name=mainToGals]").val()) && hasValue($("[name=mainLdgGals]").val()) &&
        hasValue($("[name=mainLdgGals]").val()) && hasValue($("[name=auxLdgGals]").val())) {

        computeFuelData();
    }
    else {
        alert("You must enter the takeoff and landing main and aux fuel values!");
    }

    //Compute final weights, moments and arms.
    computeToAndLdgData();

}

/**
 * Compute the crew and passenger data.
 */
function computeCrewAndPassCompartment() {

    //variable to compute moments
    var moment = 0;

    //Pilot long moment
    moment = ($("[name=pilotWeight]").val() * $("[name=pilotLongArm]").val());
    $("[name=pilotLongMom]").val(moment);

    //Pilot lat moment
    moment = ($("[name=pilotWeight]").val() * $("[name=pilotLatArm]").val());
    $("[name=pilotLatMom]").val(moment);

    //Pilot bags
    if (hasValue($("[name=pilotBagsWeight]").val())) {
        //Pilot bag long moment
        moment = ($("[name=pilotBagsWeight]").val() * $("[name=pilotBagsLongArm]").val());
        $("[name=pilotBagsLongMom]").val(moment);

        //Pilot bag lat moment
        moment = ($("[name=pilotBagsWeight]").val() * $("[name=pilotBagsLatArm]").val());
        $("[name=pilotBagsLatMom]").val(moment);
    }

    //Co Pilot
    if (hasValue($("[name=coPilotWeight]").val())) {
        //Co Pilot long moment
        moment = ($("[name=coPilotWeight]").val() * $("[name=coPilotLongArm]").val());
        $("[name=coPilotLongMom]").val(moment);

        //Co Pilot lat moment
        moment = ($("[name=coPilotWeight]").val() * $("[name=coPilotLatArm]").val());
        $("[name=coPilotLatMom]").val(moment);
    }

    //Co Pilot bags
    if (hasValue($("[name=coPilotBagsWeight]").val())) {
        //Co Pilot bag long moment
        moment = ($("[name=coPilotBagsWeight]").val() * $("[name=coPilotBagsLongArm]").val());
        $("[name=coPilotBagsLongMom]").val(moment);

        //Co Pilot bag lat moment
        moment = ($("[name=coPilotBagsWeight]").val() * $("[name=coPilotBagsLatArm]").val());
        $("[name=coPilotBagsLatMom]").val(moment);
    }

    //Right aft pass & bags
    if (hasValue($("[name=rightPassWeight]").val())) {
        //Right Pass long moment
        moment = ($("[name=rightPassWeight]").val() * $("[name=rightPassLongArm]").val());
        $("[name=rightPassLongMom]").val(moment);

        //Right Pass lat moment
        moment = ($("[name=rightPassWeight]").val() * $("[name=rightPassLatArm]").val());
        $("[name=rightPassLatMom]").val(moment);
    }

    //Left aft pass & bags
    if (hasValue($("[name=leftPassWeight]").val())) {
        //Left Pass long moment
        moment = ($("[name=leftPassWeight]").val() * $("[name=leftPassLongArm]").val());
        $("[name=leftPassLongMom]").val(moment);

        //Left Pass lat moment
        moment = ($("[name=leftPassWeight]").val() * $("[name=leftPassLatArm]").val());
        $("[name=leftPassLatMom]").val(moment);
    }

}


/**
 * Compute the Fuel data.
 */
function computeFuelData() {

    //Variable for fuel calculations
    var fuel = 0;

    //Main fuel takeoff
    fuel = $("[name=mainToGals]").val() * $("[name=fuelWeightPerGal]").val();
    $("[name=mainFuelWeight]").val(fuel);

    //Aux fuel takeoff
    fuel = $("[name=auxToGals]").val() * $("[name=fuelWeightPerGal]").val();
    $("[name=auxFuelWeight]").val(fuel);

    //Compute landing fuel in gallons
    fuel = (parseFloat($("[name=mainToGals]").val()) + parseFloat($("[name=auxToGals]").val()))
            -
           (parseFloat($("[name=mainLdgGals]").val()) + parseFloat($("[name=auxLdgGals]").val()));

    //Set landing fuel in gallons
    $("[name=fuelBurned]").val(fuel);

    //Compute landing fuel in pounds
    fuel = $("[name=fuelBurned]").val() * $("[name=fuelWeightPerGal]").val();

    //Set landing fuel in pounds
    $("[name=fuelBurnWeight]").val(fuel);

}



function computeToAndLdgData() {

    //Complete fuel moments
    computeFuelMoments();

    //Complete aircraft weight and arms with zero fuel
    computeZeroFuelWeight();

    //Compute Take off weight and arms with fuel
    computeToWeightAndArms();
}

/**
 * Determine if weight is valued and needs to be computed.
 *
 * @param value
 * @returns {boolean}
 */
function hasValue (value) {

    if (value == "" || value < 1) {
        return false;
    }

    return true;
}


/**
 * Compute the fuel moments
 */
function computeFuelMoments() {

    //Variable for moment calculations
    var moment = 0;

    //Main fuel long moment
    moment = $("[name=mainFuelWeight]").val() * $("[name=mainFuelLongArm]").val();
    $("[name=mainFuelLongMom]").val(moment);
    //Main fuel lat moment
    moment = $("[name=mainFuelWeight]").val() * $("[name=mainFuelLatArm]").val();
    $("[name=mainFuelLatMom]").val(moment);

    //Aux fuel long moment
    moment = $("[name=auxFuelWeight]").val() * $("[name=auxFuelLongArm]").val();
    $("[name=auxFuelLongMom]").val(moment);
    //Aux fuel lat moment
    moment = $("[name=auxFuelWeight]").val() * $("[name=auxFuelLatArm]").val();
    $("[name=auxFuelLatMom]").val(moment);
}



function computeZeroFuelWeight() {

    //Total accumulators
    var weight = 0;
    var longMoment = 0;
    var latMoment = 0;

    //Weight
    //Crew and Passengers
    weight+= parseFloat($("[name=bewWeight]").val());
    weight+= parseFloat($("[name=pilotWeight]").val());
    if (hasValue($("[name=pilotBagsWeight]").val())) {
        weight+= parseFloat($("[name=pilotBagsWeight]").val());
    }
    if (hasValue($("[name=coPilotWeight]").val())) {
        weight+= parseFloat($("[name=coPilotWeight]").val());
    }
    if (hasValue($("[name=coPilotBagsWeight]").val())) {
        weight+= parseFloat($("[name=coPilotBagsWeight]").val());
    }
    if (hasValue($("[name=rightPassWeight]").val())) {
        weight+= parseFloat($("[name=rightPassWeight]").val());
    }
    if (hasValue($("[name=leftPassWeight]").val())) {
        weight+= parseFloat($("[name=leftPassWeight]").val());
    }

    //If doors off add their data
    if ($("[name=fwdRtDoorCheckBox]").is(":checked")) {
        weight+= parseFloat($("[name=fwdRtDoorWeight]").val());
    }
    if ($("[name=fwdLtDoorCheckBox]").is(":checked")) {
        weight+= parseFloat($("[name=fwdLtDoorWeight]").val());
    }
    if ($("[name=aftRtDoorCheckBox]").is(":checked")) {
        weight+= parseFloat($("[name=aftRtDoorWeight]").val());
    }
    if ($("[name=aftLtDoorCheckBox]").is(":checked")) {
        weight+= parseFloat($("[name=aftLtDoorWeight]").val());
    }
    //Populate zero fuel weight
    $("[name=noFuelWeight]").val(weight);


    //Longitude
    //Crew and Passengers
    longMoment+= parseFloat($("[name=bewLongMom]").val());
    longMoment+= parseFloat($("[name=pilotLongMom]").val());
    if (hasValue($("[name=pilotBagsWeight]").val())) {
        longMoment+= parseFloat($("[name=pilotBagsLongMom]").val());
    }
    if (hasValue($("[name=coPilotWeight]").val())) {
        longMoment+= parseFloat($("[name=coPilotLongMom]").val());
    }
    if (hasValue($("[name=coPilotBagsWeight]").val())) {
        longMoment+= parseFloat($("[name=coPilotBagsLongMom]").val());
    }
    if (hasValue($("[name=rightPassWeight]").val())) {
        longMoment+= parseFloat($("[name=rightPassLongMom]").val());
    }
    if (hasValue($("[name=leftPassWeight]").val())) {
        longMoment+= parseFloat($("[name=leftPassLongMom]").val());
    }

    //If doors off add their data
    if ($("[name=fwdRtDoorCheckBox]").is(":checked")) {
        longMoment+= parseFloat($("[name=fwdRtDoorLongMom]").val());
    }
    if ($("[name=fwdLtDoorCheckBox]").is(":checked")) {
        longMoment+= parseFloat($("[name=fwdLtDoorLongMom]").val());
    }
    if ($("[name=aftRtDoorCheckBox]").is(":checked")) {
        longMoment+= parseFloat($("[name=aftRtDoorLongMom]").val());
    }
    if ($("[name=aftLtDoorCheckBox]").is(":checked")) {
        longMoment+= parseFloat($("[name=aftLtDoorLongMom]").val());
    }
    //Populate zero fuel long moment
    $("[name=noFuelLongMom]").val(longMoment);



    //Latitude
    //Crew and Passengers
    latMoment+= parseFloat($("[name=bewLatMom]").val());
    latMoment+= parseFloat($("[name=pilotLatMom]").val());
    if (hasValue($("[name=pilotBagsWeight]").val())) {
        latMoment+= parseFloat($("[name=pilotBagsLatMom]").val());
    }
    if (hasValue($("[name=coPilotWeight]").val())) {
        latMoment+= parseFloat($("[name=coPilotLatMom]").val());
    }
    if (hasValue($("[name=coPilotBagsWeight]").val())) {
        latMoment+= parseFloat($("[name=coPilotBagsLatMom]").val());
    }
    if (hasValue($("[name=rightPassWeight]").val())) {
        latMoment+= parseFloat($("[name=rightPassLatMom]").val());
    }
    if (hasValue($("[name=leftPassWeight]").val())) {
        latMoment+= parseFloat($("[name=leftPassLatMom]").val());
    }

    //If doors off add their data
    if ($("[name=fwdRtDoorCheckBox]").is(":checked")) {
        latMoment+= parseFloat($("[name=fwdRtDoorLatMom]").val());
    }
    if ($("[name=fwdLtDoorCheckBox]").is(":checked")) {
        latMoment+= parseFloat($("[name=fwdLtDoorLatMom]").val());
    }
    if ($("[name=aftRtDoorCheckBox]").is(":checked")) {
        latMoment+= parseFloat($("[name=aftRtDoorLatMom]").val());
    }
    if ($("[name=aftLtDoorCheckBox]").is(":checked")) {
        latMoment+= parseFloat($("[name=aftLtDoorLatMom]").val());
    }
    //Populate zero fuel lat moment
    $("[name=noFuelLatMom]").val(latMoment);

    //Compute the lat and long arms locations
    $("[name=noFuelLongArm]").val(parseFloat(longMoment / weight).toFixed(2));
    $("[name=noFuelLatArm]").val(parseFloat(latMoment / weight).toFixed(2));

    //Compute Take off weight, lat and long arms
    weight+= parseFloat($("[name=mainFuelWeight]").val()) + parseFloat($("[name=auxFuelWeight]").val());
    $("[name=totalWeight]").val(weight);

    longMoment+= parseFloat($("[name=mainFuelLongMom]").val()) + parseFloat($("[name=auxFuelLongMom]").val());
    $("[name=totalLongMom]").val(longMoment);
    $("[name=totalLongArm]").val(parseFloat(longMoment / weight).toFixed(2));

    latMoment+= parseFloat($("[name=mainFuelLatMom]").val()) + parseFloat($("[name=auxFuelLatMom]").val());
    $("[name=totalLatMom]").val(latMoment);
    $("[name=totalLatArm]").val(parseFloat(latMoment / weight).toFixed(2));

    //Landing weight
    $("[name=ldgWeight]").val($("[name=totalWeight]").val() - $("[name=fuelBurnWeight]").val());

}