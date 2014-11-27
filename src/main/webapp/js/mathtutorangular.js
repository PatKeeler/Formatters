/**
 * Modified by Pat on 11/27/2014.
 */

/**
 * Default invalidNumber error return message.
 */
var invalidNumber = "You must enter valid numbers for your answer, please try again!";


/**
 * Default message if start sequence not followed.
 */
var notStarted = "You must select a number, a math function and click on the \"Start\" " +
    "button before using this funtion, \nPlease do that now.";


/**
 * Variable to hold selected number
 * Default to 0 for validation check when answer button pressed.
 */
var selectedNumber;


/**
 * Variable to hold math function
 * Default to 0 for validation check when answer button pressed.
 */
var selectedMathFunction;


/**
 * Keep count of number correct
 */
var rightCount = 0;


/**
 * Keep count of number correct
 */
var wrongCount = 0;


/**
 * Boolean to keep count of incorrect guesses.
 */
var repetition = 1;


/**
 * Validate radio buttons and start.
 */
function startTutor() {

    selectedNumber = $("[name=numberRadio]:checked").val();

    if (selectedNumber == null) {
        alert(notStarted);
        return false;
    }

    selectedMathFunction = $("[name=functionRadio]:checked").val();

    if (selectedMathFunction == null) {
        alert(notStarted);
        return false;
    }

    //Set up the test entries
    initializeTestItems();

}


/**
 * Populate the test variables for the user to begin
 *
 * @param a
 * @param b
 */
function initializeTestItems() {

    //Holder for divide or other "answer" display
    // answer = other - default for add, subtract and multiply.
    var answer = "other";

    //Set chosen number
    $("[name=chosenNumber]").val(selectedNumber);

    //Add
    if (selectedMathFunction == 1) {
        $("[name=operator]").val("+");
        $("[name=randomNumber]").val(getRandomInteger(12));
    }
    //Subtract
    else if (selectedMathFunction == 2) {
        $("[name=operator]").val("-");
        $("[name=randomNumber]").val(getRandomInteger(selectedNumber));
    }
    //Multiply
    else if (selectedMathFunction == 3) {
        $("[name=operator]").val("x");
        $("[name=randomNumber]").val(getRandomInteger(12));
    }
    //Divide
    else if (selectedMathFunction == 4) {
        $("[name=operator]").val("/");
        $("[name=randomNumber]").val(getRandomInteger(selectedNumber));

        //set answer = divide
        answer = "divide";
    }
    else {
        alert("Doh!");
    }

    // blank responseText and background color to white.
    $("[name=responseText]").val("");
    $("[name=responseText]").css("background", "#FFFFFF");

    //set scores to zero if counts are zero
    if (parseInt(rightCount) == 0 && parseInt(wrongCount) == 0) {
        $("[name=numberRight]").val(0);
        $("[name=numberWrong]").val(0);
        $("[name=percentage]").val(0);
        $("[name=percentage]").css("background", "#FFFFFF");
    }

    //set the answer display
    answerDisplay(answer);

    //hide the Hint button
    $("[name=hintdiv]").removeClass("show").addClass("hide");

}


/**
 * Check Answer function.
 *
 * Verifies the answer is correct, records the result and
 * resets for the next challenge.
 */
function checkAnswer() {

    var answer = -1;

    //Make sure the tutor has been started correctly first.
    if (selectedNumber == null || selectedMathFunction == null) {
        alert(notStarted);
        return false;
    }

    //Make sure there is an answer to check.
    if (selectedMathFunction != 4) {
        if (! verifyNumber($("[name=otherAnswer]").val())) {
            alert(invalidNumber);
            return false;
        }
    }
    else {
        if (! verifyNumber($("[name=divideAnswer]").val())) {
            alert(invalidNumber);
            return false;
        }
    }
    //Make sure remainder is blank or a number
    if ($("[name=remainder]").val() != "") {
        if (! verifyNumber($("[name=remainder]").val())) {
            alert(invalidNumber);
            return false;
        }
    }
    //Compute answer based on math function

    //Addition
    if (selectedMathFunction == 1) {
        answer = parseInt(selectedNumber) + parseInt($("[name=randomNumber]").val());

        if (answer == $("[name=otherAnswer]").val()) {
            correctAnswer();

            //Reset random number
            $("[name=randomNumber]").val(getRandomInteger(12));
        }
        else {
            wrongAnswer();
        }
    }
    //Subtraction
    else if (selectedMathFunction == 2) {
        answer = parseInt(selectedNumber) - parseInt($("[name=randomNumber]").val());

        if (answer == $("[name=otherAnswer]").val()) {
            correctAnswer();

            //Reset random number
            $("[name=randomNumber]").val(getRandomInteger(selectedNumber));
        }
        else {
            wrongAnswer();
        }
    }
    //Multiplication
    else if (selectedMathFunction == 3) {
        answer = parseInt(selectedNumber) * parseInt($("[name=randomNumber]").val());

        if (answer == $("[name=otherAnswer]").val()) {
            correctAnswer();

            //Reset random number
            $("[name=randomNumber]").val(getRandomInteger(12));
        }
        else {
            wrongAnswer();
        }
    }
    //Division
    else if (selectedMathFunction == 4) {

        var remainder;
        var mod;

        if ($("[name=remainder]").val() == "") {
            remainder = parseInt(0);
        }
        else {
            remainder = parseInt($("[name=remainder]").val());
        }

        answer = parseInt(selectedNumber) / parseInt($("[name=randomNumber]").val());
        answer = Math.floor(answer);

        mod = parseInt(selectedNumber) % parseInt($("[name=randomNumber]").val());

        if ( answer == $("[name=divideAnswer]").val() &&
            mod == $("[name=remainder]").val() ) {

            correctAnswer();

            //For division get a number that gives mod zero
            $("[name=randomNumber]").val(getRandomInteger(selectedNumber));

        }
        else {
            wrongAnswer();
        }
    }

}


/**
 * Determine the correct answer display
 *
 * @param display
 */
function answerDisplay(display) {

    //Display for all but divide
    if (display == "other") {

        //Show other and hide divide
        $("[name=otherDiv]").removeClass("hide").addClass("show");
        $("[name=divideDiv]").removeClass("show").addClass("hide");

        //blank answer, set focus
        $("[name=otherAnswer]").val("");
        $("[name=otherAnswer]").focus();
    }
    //Display divide answer boxes
    else if (display == "divide") {

        $("[name=otherDiv]").removeClass("show").addClass("hide");
        $("[name=divideDiv]").removeClass("hide").addClass("show");

        //blank answer, set focus
        $("[name=divideAnswer]").val("");
        $("[name=divideAnswer]").focus();
        $("[name=remainder]").val("0");
    }
    //Doh!
    else {
        alert("invalid answer display!");
    }
}


function hintDisplay(display) {

    if (display == "hide") {
        $("[name=hintdiv]").removeClass("show").addClass("hide");
    }
    else {
        $("[name=hintdiv]").removeClass("hide").addClass("show");
    }
}


/**
 * Reset the score
 */
function resetScore() {

    //set score to blanks.
    $("[name=numberWrong]").val(0);
    $("[name=numberRight]").val(0);
    $("[name=percentage]").val(0);

    //set counts to zero
    wrongCount = 0;
    rightCount = 0;

    //Reset background color to white
    $("[name=percentage]").css("background", "#FFFFFF");

}

/**
 * Correct answer.
 */
function correctAnswer() {

    //Signal correct
    $("[name=responseText]").val("Correct");
    $("[name=responseText]").css("background", "#40FF00");

    if (selectedMathFunction != 4) {
        //Set otherAnswer to blank and focus
        $("[name=otherAnswer]").val("");
        $("[name=otherAnswer]").focus();
    }
    else {
        //Set divideAnswer to blank and focus
        $("[name=divideAnswer]").val("");
        $("[name=divideAnswer]").focus();
        $("[name=remainder]").val("0");
    }


    //Increment number correct
    rightCount += 1;
    $("[name=numberRight]").val(rightCount);

    //Compute new percentage
    computePercentage();

    //reset
    repetition = 1;

    //hide hint button
    hintDisplay("hide");

}


/**
 * Wrong answer.
 */
function wrongAnswer() {

    if (repetition == 2) {
        //Signal wrong answer
        $("[name=responseText]").val("Wrong");
        $("[name=responseText]").css("background", "#FF0000");

        if (selectedMathFunction != 4) {
            //Set otherAnswer to blank and focus
            $("[name=otherAnswer]").val("");
            $("[name=otherAnswer]").focus();
        }
        else {
            //Set divideAnswer to blank and focus
            $("[name=divideAnswer]").val("");
            $("[name=divideAnswer]").focus();
        }

        //Increment number incorrect
        wrongCount += 1;
        $("[name=numberWrong]").val(wrongCount);

        //reset
        repetition = 1;

        //show hint button
        hintDisplay("hide");

    }
    else {
        //bump by one
        repetition += 1;

        //Signal wrong answer
        $("[name=responseText]").val("Try again");
        $("[name=responseText]").css("background", "#FFFF00");

        //hide hint button
        hintDisplay("show");

    }

    //Compute new percentage
    computePercentage();

}


/**
 * Compute percentage
 */
function computePercentage() {

    var percentage;

    if (rightCount == 0) {

        //Set percentage = 0
        percentage = 0;
    }
    else {

        //Get total
        var total = parseInt(rightCount) + parseInt(wrongCount);

        //Get percentage
        percentage = parseInt(rightCount) / total;

        //Move decimal 2 positions to make percentage
        percentage = percentage * 100;

        //Get whole number
        percentage = Math.round(percentage);

    }

    //show percentage
    $("[name=percentage]").val(percentage);

    //Set color for score
    if (percentage >= 90) {
        //Green
        $("[name=percentage]").css("background", "#40FF00");
    }
    else if(percentage >= 80) {
        //Yellow
        $("[name=percentage]").css("background", "#FFFF00");
    }
    else if(percentage >= 70) {
        //Orange
        $("[name=percentage]").css("background", "#FF6600");
    }
    else if(percentage >= 1) {
        //Red
        $("[name=percentage]").css("background", "#FF0000");
    }
    else {
        //White
        $("[name=percentage]").css("background", "#FFFFFF");
    }


}


/**
 * Random number generator, a java script function.
 *
 * Uses an input parameter to create the random integer for flexibility.
 */
function getRandomInteger(range) {

    return randomnumber=Math.floor(Math.random() * range + 1);
}


/**
 *
 */
function checkEnterKey(e) {

    //See if event populated
    if (typeof e == undefined && window.event) {

        e = window.event;
    }

    if (e.keyCode == 13)
    {
        checkAnswer();
    }

}


/**
 * Verify that number passed in is a valid integer.
 *
 * @param a
 */
function verifyNumber(a) {

    if (a >= 0 && a <= 999) {
        return true;
    }

    return false;
}


/**
 * Hint - shows the possible correct answers from minimum to maximum.
 */
function  getHint() {

    if (selectedNumber == null || selectedMathFunction == null) {
        alert(notStarted);
        return false;
    }

    //Addition
    if (selectedMathFunction == 1) {
        getAdditionHints();
        //Set focus on answer
        $("[name=otherAnswer]").focus();
    }
    //Subtraction
    else if (selectedMathFunction == 2) {
        getSubtractionHints();
        //Set focus on answer
        $("[name=otherAnswer]").focus();
    }
    //Multiplication
    else if (selectedMathFunction == 3) {
        getMultiplicationHints();
        //Set focus on answer
        $("[name=otherAnswer]").focus();
    }
    //Division
    else if (selectedMathFunction == 4) {
        getDivisionHints();
        //Set focus on answer
        $("[name=divideAnswer]").focus();
    }

    //Set focus on answer
    $("[name=otherAnswer]").focus();

}


/**
 * Get addition possibilities.
 */
function getAdditionHints() {

    var display = "";
    var newLine = "\n";

    for (var i = 0; i < 12; i++) {
        display += newLine + selectedNumber + " + " + parseInt(i + 1) + " = " +
        (parseInt(selectedNumber) + parseInt(i + 1));
    }

    alert("Hint: " + display);
}


/**
 * Get division possibilities.
 */
function getDivisionHints() {

    var display = "";
    var newLine = "\n";

    for (var i = 0; i < selectedNumber; i++) {
        var mod = parseInt(selectedNumber) % parseInt(i + 1);
        var number = parseInt(selectedNumber) / parseInt(i + 1);
        number = Math.floor(number);

        display += newLine + selectedNumber + " / " + parseInt(i + 1) + " = " +
        number + ", remainder: " + mod;
    }

    alert("Hint: " + display);
}


/**
 * Get multiplication possibilities.
 */
function getMultiplicationHints() {

    var display = "";
    var newLine = "\n";

    for (var i = 0; i < 12; i++) {
        display += newLine + selectedNumber + " x " + parseInt(i + 1) + " = " +
        (parseInt(selectedNumber) * parseInt(i + 1));
    }

    alert("Hint: " + display);
}


/**
 * Get subtraction possibilities.
 */
function getSubtractionHints() {

    var display = "";
    var newLine = "\n";

    for (var i = 0; i < selectedNumber; i++) {
        display += newLine + selectedNumber + " - " + parseInt(i + 1) + " = " +
        (parseInt(selectedNumber) - parseInt(i + 1));
    }

    alert("Hint: " + display);
}


/**
 * This is the about information for the Math Tutor.
 */
function getMathAbout() {

    alert("This Elementary Math Tutor helps teach the basic math skills through the use of repetition. "
    + "The tutor uses random \nnumber generation to repetitively challenge the user for answers "
    + "to the selected number and a randomly generated number."
    + "\n\nFollow these steps to get started:"
    + "\n\n\t1. Select the radio button by the number you want to practice."
    + "\n\t2. Select the radio button by the math function you want to practice."
    + "\n\t3. Press the \"Start\" button to begin the training."
    + "\n\t4. Enter the answer for the generated problem."
    + "\n\nThe tutor will count each time you get the answer correct. You will get 2 chances "
    + "to enter the correct number, if you \nget them both wrong it will count as a wrong answer. "
    + "If you want to change the answer you have entered, use the \nbackspace button on your keyboard "
    + "to erase the answer."
    + "\n\nYou can check your answer by pressing the \"Check Answer\" button or by pressing the \"Enter\" key."
    + "\n\nThe problems are randomly generated so sometimes you might get the same problem 2 or 3 times "
    + "in a row."
    + "\n\nYou can change the number and/or math function at any time by selecting the new values and "
    + "pressing the \"Start\" button again. "
    + "\n\nWhen you select \"Divide\" the remainder is set to \"0\" "
    + " so you only need to change it if the remainder is not zero. "
    + "\n\nThis tool only keeps score for the current session. It shows the number correct, the number "
    + "wrong and the percentage correct."
    + "\n\nNeed help with the answer?  Press the \"Hint\" button for help anytime you can see it. "
    + "The \"Hint\" button will help you \nlearn the right answers.  But, when you use hints, study them so "
    + "you don't have to look at hints so often."
    + "\n\nIf you have suggestions for enhancements or encounter errors, send an email "
    + "to Pat@PatsTools.com and I'll look into it. ");

    return true;
}
