
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
	
    $("[name=tailNumber]").css('background-color', 'white');

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
        $("[name=tailNumber]").css('background-color', 'red');
        $("[name=tailNumber]").focus();
        return;
    }

    if (! hasValue($("[name=pilotWeight]").val())) {
        alert("You must provide pilot weight to compute a weight and balance!");
        $("[name=pilotWeight]").css('background-color', 'red');
        $("[name=pilotWeight]").focus();
        return;
    }
    else {
        $("[name=pilotWeight]").css('background-color', 'white');
    }


    //Populate the crew and passenger data.
    computeCrewAndPassCompartment();

    //Verify fuel values entered and compute, else return message.

    //Set background color to white before checks
    $("[name=mainToGals]").css('background-color', 'white');
    $("[name=mainLdgGals]").css('background-color', 'white');
    $("[name=auxToGals]").css('background-color', 'white');
    $("[name=auxLdgGals]").css('background-color', 'white');

    //Make sure fuel has been entered
    if (! hasValue($("[name=mainToGals]").val()) || ! hasValue($("[name=mainLdgGals]").val()) ||
        ! hasValue($("[name=auxToGals]").val()) ||  ! hasValue($("[name=auxLdgGals]").val())) {

        alert("You must enter the takeoff and landing main and aux fuel values!");
        if (! hasValue($("[name=mainToGals]").val())) {
            $("[name=mainToGals]").css('background-color', 'red');
            $("[name=mainToGals]").focus();
        }
        if (! hasValue($("[name=mainLdgGals]").val())) {
            $("[name=mainLdgGals]").css('background-color', 'red');
            $("[name=mainLdgGals]").focus();
        }
        if (! hasValue($("[name=auxToGals]").val())) {
            $("[name=auxToGals]").css('background-color', 'red');
            $("[name=auxToGals]").focus();
        }
        if (! hasValue($("[name=auxLdgGals]").val())) {
            $("[name=auxLdgGals]").css('background-color', 'red');
            $("[name=auxLdgGals]").focus();
        }

        return
    }
    else {

        if (parseInt($("[name=mainLdgGals]").val()) >= parseInt($("[name=mainToGals]").val())) {

            alert("Main take off fuel must be greater than landing fuel!");
            $("[name=mainToGals]").css('background-color', 'red');
            $("[name=mainLdgGals]").css('background-color', 'red');
            $("[name=mainToGals]").focus();
            return;
        }

        if (parseInt($("[name=auxLdgGals]").val()) >= parseInt($("[name=auxToGals]").val())) {
            alert("Aux take off fuel must be greater than landing fuel!");
            $("[name=auxToGals]").css('background-color', 'red');
            $("[name=auxLdgGals]").css('background-color', 'red');
            $("[name=auxToGals]").focus();
            return;
        }

        //Alert if landing fuel less than approximately 30 minutes
        var landingFuel = parseInt($("[name=mainLdgGals]").val())
                        + parseInt($("[name=auxLdgGals]").val());

        if (landingFuel <= 6) {
            alert("Caution: You may be landing with low fuel on board!");
        }

        //All checks pass
        computeFuelData();

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
    $("[name=mainFuelWeight]").val(fuel.toFixed(0));

    //Aux fuel takeoff
    fuel = $("[name=auxToGals]").val() * $("[name=fuelWeightPerGal]").val();
    $("[name=auxFuelWeight]").val(fuel.toFixed(0));

    //Compute landing fuel in gallons
    fuel = (parseFloat($("[name=mainToGals]").val()) + parseFloat($("[name=auxToGals]").val()))
            -
           (parseFloat($("[name=mainLdgGals]").val()) + parseFloat($("[name=auxLdgGals]").val()));

    //Set landing fuel in gallons
    var fuelBurned = fuel.toFixed(0);

    //Compute landing fuel in pounds
    fuel = fuelBurned * $("[name=fuelWeightPerGal]").val();

    //Set landing fuel in pounds
    $("[name=fuelBurnWeight]").val(fuel.toFixed(0));

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
    $("[name=mainFuelLongMom]").val(moment.toFixed(0));
    //Main fuel lat moment
    moment = $("[name=mainFuelWeight]").val() * $("[name=mainFuelLatArm]").val();
    $("[name=mainFuelLatMom]").val(moment.toFixed(0));

    //Aux fuel long moment
    moment = $("[name=auxFuelWeight]").val() * $("[name=auxFuelLongArm]").val();
    $("[name=auxFuelLongMom]").val(moment.toFixed(0));
    //Aux fuel lat moment
    moment = $("[name=auxFuelWeight]").val() * $("[name=auxFuelLatArm]").val();
    $("[name=auxFuelLatMom]").val(moment.toFixed(0));
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
    $("[name=noFuelWeight]").val(weight.toFixed(0));


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
    $("[name=noFuelLongMom]").val(longMoment.toFixed(0));



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
    $("[name=noFuelLatMom]").val(latMoment.toFixed(0));

    //Compute the lat and long arms locations
    $("[name=noFuelLongArm]").val(parseFloat(longMoment / weight).toFixed(2));
    $("[name=noFuelLatArm]").val(parseFloat(latMoment / weight).toFixed(2));

    //Compute Take off weight, lat and long arms
    weight+= parseFloat($("[name=mainFuelWeight]").val()) + parseFloat($("[name=auxFuelWeight]").val());
    $("[name=totalWeight]").val(weight.toFixed(0));

    longMoment+= parseFloat($("[name=mainFuelLongMom]").val()) + parseFloat($("[name=auxFuelLongMom]").val());
    $("[name=totalLongMom]").val(longMoment.toFixed(0));
    $("[name=totalLongArm]").val(parseFloat(longMoment / weight).toFixed(2));

    latMoment+= parseFloat($("[name=mainFuelLatMom]").val()) + parseFloat($("[name=auxFuelLatMom]").val());
    $("[name=totalLatMom]").val(latMoment.toFixed(0));
    $("[name=totalLatArm]").val(parseFloat(latMoment / weight).toFixed(2));

    //Landing weight
    var ldgWeight = $("[name=totalWeight]").val() - $("[name=fuelBurnWeight]").val();
    $("[name=ldgWeight]").val(ldgWeight.toFixed(0));

}


function computePerformance() {

    //Verify required fields are populated
    if (! hasValue($("[name=currentAltimeter]").val())) {
        alert("You must provide the current altimeter to compute PA & DA!");
        $("[name=currentAltimeter]").css('background-color', 'red');
        $("[name=currentAltimeter]").focus();
        return;
    }
    else {
        $("[name=currentAltimeter]").css('background-color', 'white');
    }

    if (! hasValue($("[name=tempCelsius]").val())) {
        alert("You must provide the temperature to compute PA & DA!");
        $("[name=tempCelsius]").css('background-color', 'red');
        $("[name=tempCelsius]").focus();
        return;
    }
    else {
        $("[name=tempCelsius]").css('background-color', 'white');
    }

    if (! hasValue($("[name=fieldElevation]").val())) {
        alert("You must provide the field elevation to compute PA & DA!");
        $("[name=fieldElevation]").css('background-color', 'red');
        $("[name=fieldElevation]").focus();
        return;
    }
    else {
        $("[name=fieldElevation]").css('background-color', 'white');
    }

    // Compute the PA
    var pa = (((29.92 - parseFloat($("[name=currentAltimeter]").val()))) * 1000) + parseFloat($("[name=fieldElevation]").val());
    $("[name=pressureAltitude]").val(pa.toFixed(0));

    var da = getISATemp(pa);

    $("[name=densityAltitude]").val(da.toFixed(0));

}


function getISATemp(pa) {

    var oat = $("[name=tempCelsius]").val();

    var isa_T = 15 - 1.98 / 1000 * pa;

    // density altitude = pa + (120 x (OAT - ISA Temp))
    var da = pa + (120 * (oat - isa_T));

    return da;
}


/*
 * This is the about information for the SQL formatter.
 */
function getWBAbout() {

	alert("This Weight and Balance calculator is written specifically for "
        + "\nRobinson R-44 Raven II aircraft."
        + "\n\nThe user must perform the following: "
		+ "\n  Select the aircraft to compute Weight and Balance for."
        + "\n  Enter the weights for crew and passengers."
		+ "\n  Enter the take off main and aux fuel in gallons."
        + "\n  Enter the landing main and aux fuel in gallons."
		+ "\n  Select the \"Compute\" button to generate the weight and "
        + "\n  balance figures for the provided data."
        + "\n\nThe minimum requirements are highlighted in yellow."
		+ "\n\nYou may change any of the figures on the form and select "
        + "\nthe \"Compute\" button as many times as necessary. "
        + "\n\nIf you change any of the figures on the form you must select "
        + "\nthe \"Compute\" button again to re-compute the form. ");

	return true;
}
