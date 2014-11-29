/**
 * Modified by Pat on 11/27/2014.
 */


mathApp.controller ('mathController', function ($scope) {

    /**
     * Default invalidNumber error return message.
     */
    $scope.invalidNumber = "You must enter valid numbers for your answer, please try again!";

    /**
     * boolean setting for Hint function.mathController
     */
    $scope.hintVar = false;
    $scope.hintSet = function(setting) {
        $scope.hintVar = setting;
    };

    /**
     * boolean setting to SHOW divide function.
     */
    $scope.divideVar = false;
    $scope.divideSet = function(setting) {
        $scope.divideVar = setting;
    };

    /**
     * Default message if start sequence not followed.
     */
    $scope.notStarted = "You must select a number, a math function and click on the \"Start\" " +
        "button before using this funtion, \nPlease do that now.";

    /** Variable to hold selected number
     * Default to 0 for validation check when answer button pressed.
     */
    $scope.selectedNumber = 0;

    /** Variable to hold math function
     * Default to 0 for validation check when answer button pressed.
     */
    $scope.selectedMathFunction = "0";

    /** Keep count of number correct */
    $scope.rightCount = 0;

    /** Keep count of number correct */
    $scope.wrongCount = 0;

    /** Boolean to keep count of incorrect guesses. */
    $scope.repetition = 1;

    /** Radio of selected math function. */
    $scope.numberRadio;

    /** Radio of number selected to learn. */
    $scope.functionRadio;

    /** Radio of number selected to learn. */
    $scope.chosenNumber;

    /** Radio of number selected to learn. */
    $scope.randomnumber;

    /** Radio of number selected to learn. */
    $scope.operator;

    /** Radio of number selected to learn. */
    $scope.otherAnswer;


    /**
     * Validate radio buttons and start.
     */
    $scope.startTutor = function()  {

        $scope.selectedNumber = $scope.numberRadio;
        //alert("selectedNumber = " + $scope.selectedNumber);

        //$scope.selectedNumber = $("[name=numberRadio]:checked").val();

        if ($scope.selectedNumber == null) {
            alert($scope.notStarted);
            return false;
        }

        $scope.selectedMathFunction = $scope.functionRadio;

        if ($scope.selectedMathFunction == null) {
            alert($scope.notStarted);
            return false;
        }

        //Set up the test entries
        $scope.initializeTestItems();

    }


    /**
     * Populate the test variables for the user to begin
     *
     * @param a
     * @param b
     */
    $scope.initializeTestItems = function() {

        //Holder for divide or other "answer" display
        // answer = other - default for add, subtract and multiply.
        var answer = "other";

        //Set chosen number
        $scope.chosenNumber = $scope.selectedNumber;

        //Add
        if ($scope.selectedMathFunction == 1) {
            $scope.operator = "+";
            $scope.randomNumber = $scope.getRandomInteger(12);
        }
        //Subtract
        else if ($scope.selectedMathFunction == 2) {
            $scope.operator = "-";
            $scope.randomNumber = $scope.getRandomInteger($scope.selectedNumber);
        }
        //Multiply
        else if ($scope.selectedMathFunction == 3) {
            $scope.operator = "x";
            $scope.randomNumber = $scope.getRandomInteger(12);
        }
        //Divide
        else if ($scope.selectedMathFunction == 4) {
            $scope.operator = "/";
            $scope.randomNumber = $scope.getRandomInteger($scope.selectedNumber);

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
        if (parseInt($scope.rightCount) == 0 && parseInt($scope.wrongCount) == 0) {
            $("[name=numberRight]").val(0);
            $("[name=numberWrong]").val(0);
            $("[name=percentage]").val(0);
            $("[name=remainder]").val(0);
            $("[name=percentage]").css("background", "#FFFFFF");
        }

        //set the answer display
        $scope.answerDisplay(answer);

        //hide the Hint button
        $scope.hintSet(false);

    }


    /**
     * Check Answer function.
     *
     * Verifies the answer is correct, records the result and
     * resets for the next challenge.
     */
    $scope.checkAnswer = function() {

        var answer = -1;

        //Make sure the tutor has been started correctly first.
        if ($scope.selectedNumber == null || $scope.selectedMathFunction == null) {
            alert(notStarted);
            return false;
        }

        //Make sure there is an answer to check.
        if ($scope.selectedMathFunction != 4) {
            if (! $scope.verifyNumber($scope.otherAnswer)) {
                alert($scope.invalidNumber);
                return false;
            }
        }
        else {
            if (! $scope.verifyNumber($("[name=divideAnswer]").val())) {
                alert($scope.invalidNumber);
                return false;
            }
        }
        //Make sure remainder is blank or a number
        if ($("[name=remainder]").val() != "") {
            if (! $scope.verifyNumber($("[name=remainder]").val())) {
                alert($scope.invalidNumber);
                return false;
            }
        }
        //Compute answer based on math function

        //Addition
        if ($scope.selectedMathFunction == 1) {
            answer = parseInt($scope.selectedNumber) + parseInt($scope.randomNumber);

            if (answer == $scope.otherAnswer) {
                $scope.correctAnswer();

                //Reset random number
                $scope.randomNumber = $scope.getRandomInteger(12);
            }
            else {
                $scope.wrongAnswer();
            }
        }
        //Subtraction
        else if ($scope.selectedMathFunction == 2) {
            answer = parseInt($scope.selectedNumber) - parseInt($scope.randomNumber);

            if (answer == $scope.otherAnswer) {
                $scope.correctAnswer();

                //Reset random number
                $scope.randomNumber = $scope.getRandomInteger($scope.selectedNumber);
            }
            else {
                $scope.wrongAnswer();
            }
        }
        //Multiplication
        else if ($scope.selectedMathFunction == 3) {
            answer = parseInt($scope.selectedNumber) * parseInt($scope.randomNumber);

            if (answer == $scope.otherAnswer) {
                $scope.correctAnswer();

                //Reset random number
                $scope.randomNumber = $scope.getRandomInteger(12);
            }
            else {
                $scope.wrongAnswer();
            }
        }
        //Division
        else if ($scope.selectedMathFunction == 4) {

            var remainder;
            var mod;

            if ($("[name=remainder]").val() == "") {
                remainder = parseInt(0);
            }
            else {
                remainder = parseInt($("[name=remainder]").val());
            }

            answer = parseInt($scope.selectedNumber) / parseInt($scope.randomNumber);
            answer = Math.floor(answer);

            mod = parseInt($scope.selectedNumber) % parseInt($scope.randomNumber);

            if ( answer == $("[name=divideAnswer]").val() &&
                mod == $("[name=remainder]").val() ) {

                $scope.correctAnswer();

                //For division get a number that gives mod zero
                $scope.randomNumber = $scope.getRandomInteger($scope.selectedNumber);

            }
            else {
                $scope.wrongAnswer();
            }
        }

    }


    /**
     * Determine the correct answer display
     *
     * @param display
     */
    $scope.answerDisplay = function(display) {

        //Display for all but divide
        if (display == "other") {

            //Show other and hide divide
            $("[name=otherDiv]").removeClass("hide").addClass("show");
            //$("[name=divideDiv]").removeClass("show").addClass("hide");
            $scope.divideSet(false);

            //blank answer, set focus
            $scope.otherAnswer = "";
            $("[name=otherAnswer]").focus();
        }
        //Display divide answer boxes
        else if (display == "divide") {

            $("[name=otherDiv]").removeClass("show").addClass("hide");
            //$("[name=divideDiv]").removeClass("hide").addClass("show");
            $scope.divideSet(true)

            //blank answer, set focus
            $("[name=divideAnswer]").val("");
            $("[name=remainder]").val("0");
            $("[name=divideAnswer]").focus();
        }
        //Doh!
        else {
            alert("invalid answer display!");
        }
    }


    $scope.hintDisplay = function(display) {

        $scope.hintSet(display);
    }


    /**
     * Reset the score
     */
    $scope.resetScore = function() {

        //set score to blanks.
        $("[name=numberWrong]").val(0);
        $("[name=numberRight]").val(0);
        $("[name=percentage]").val(0);

        //set counts to zero
        $scope.wrongCount = 0;
        $scope.rightCount = 0;

        //Reset background color to white
        $("[name=percentage]").css("background", "#FFFFFF");
        $("[name=responseText]").val("");
        $("[name=responseText]").css("background", "#FFFFFF");

    }

    /**
     * Correct answer.
     */
    $scope.correctAnswer = function() {

        //Signal correct
        $("[name=responseText]").val("Correct");
        $("[name=responseText]").css("background", "#40FF00");

        if ($scope.selectedMathFunction != 4) {
            //Set otherAnswer to blank and focus
            $scope.otherAnswer = "";
            $("[name=otherAnswer]").focus();
        }
        else {
            //Set divideAnswer to blank and focus
            $("[name=divideAnswer]").val("");
            $("[name=divideAnswer]").focus();
            $("[name=remainder]").val("0");
        }


        //Increment number correct
        $scope.rightCount += 1;
        $("[name=numberRight]").val($scope.rightCount);

        //Compute new percentage
        $scope.computePercentage();

        //reset
        $scope.repetition = 1;

        //hide hint button
        $scope.hintDisplay(false);

    }


    /**
     * Wrong answer.
     */
    $scope.wrongAnswer = function() {

        if ($scope.repetition == 2) {
            //Signal wrong answer
            $("[name=responseText]").val("Wrong");
            $("[name=responseText]").css("background", "#FF0000");

            if ($scope.selectedMathFunction != 4) {
                //Set otherAnswer to blank and focus
                $scope.otherAnswer = "";
                $("[name=otherAnswer]").focus();
            }
            else {
                //Set divideAnswer to blank and focus
                $("[name=divideAnswer]").val("");
                $("[name=divideAnswer]").focus();
            }

            //Increment number incorrect
            $scope.wrongCount += 1;
            $("[name=numberWrong]").val($scope.wrongCount);

            //reset
            $scope.repetition = 1;

            //show hint button
            $scope.hintDisplay(false);

        }
        else {
            //bump by one
            $scope.repetition += 1;

            //Signal wrong answer
            $("[name=responseText]").val("Try again");
            $("[name=responseText]").css("background", "#FFFF00");

            //show hint button
            $scope.hintDisplay(true);

        }

        //Compute new percentage
        $scope.computePercentage();

    }


    /**
     * Compute percentage
     */
    $scope.computePercentage = function() {

        var percentage;

        if ($scope.rightCount == 0) {

            //Set percentage = 0
            percentage = 0;
        }
        else {

            //Get total
            var total = parseInt($scope.rightCount) + parseInt($scope.wrongCount);

            //Get percentage
            percentage = parseInt($scope.rightCount) / total;

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
    $scope.getRandomInteger = function(range) {

        return $scope.randomnumber = Math.floor(Math.random() * range + 1);
    }


    /**
     *
     */
    $scope.checkEnterKey = function(e) {

        //See if event populated
        if (typeof e == 'undefined' && window.event) {

            e = window.event;
        }

        if (e.keyCode == 13)
        {
            $scope.checkAnswer();
        }

    }


    /**
     * Verify that number passed in is a valid integer.
     *
     * @param a
     */
    $scope.verifyNumber = function(a) {

        if (a >= 0 && a <= 999) {
            return true;
        }

        return false;
    }


    /**
     * Hint - shows the possible correct answers from minimum to maximum.
     */
    $scope.getHint = function() {

        if ($scope.selectedNumber == null || $scope.selectedMathFunction == null) {
            alert(notStarted);
            return false;
        }

        //Addition
        if ($scope.selectedMathFunction == 1) {
            $scope.getAdditionHints();
            //Set focus on answer
            $("[name=otherAnswer]").focus();
        }
        //Subtraction
        else if ($scope.selectedMathFunction == 2) {
            $scope.getSubtractionHints();
            //Set focus on answer
            $("[name=otherAnswer]").focus();
        }
        //Multiplication
        else if ($scope.selectedMathFunction == 3) {
            $scope.getMultiplicationHints();
            //Set focus on answer
            $("[name=otherAnswer]").focus();
        }
        //Division
        else if ($scope.selectedMathFunction == 4) {
            $scope.getDivisionHints();
            //Set focus on answer
            $("[name=divideAnswer]").focus();
        }

        //Set focus on answer
        $("[name=otherAnswer]").focus();

    }


    /**
     * Get addition possibilities.
     */
    $scope.getAdditionHints = function() {

        var display = "";
        var newLine = "\n";

        for (var i = 0; i < 12; i++) {
            display += newLine + $scope.selectedNumber + " + " + parseInt(i + 1) + " = " +
            (parseInt($scope.selectedNumber) + parseInt(i + 1));
        }

        alert("Hint: " + display);
    }


    /**
     * Get division possibilities.
     */
    $scope.getDivisionHints = function() {

        var display = "";
        var newLine = "\n";

        for (var i = 0; i < $scope.selectedNumber; i++) {
            var mod = parseInt($scope.selectedNumber) % parseInt(i + 1);
            var number = parseInt($scope.selectedNumber) / parseInt(i + 1);
            number = Math.floor(number);

            display += newLine + $scope.selectedNumber + " / " + parseInt(i + 1) + " = " +
            number + ", remainder: " + mod;
        }

        alert("Hint: " + display);
    }


    /**
     * Get multiplication possibilities.
     */
    $scope.getMultiplicationHints = function() {

        var display = "";
        var newLine = "\n";

        for (var i = 0; i < 12; i++) {
            display += newLine + parseInt(i + 1) + " x " + $scope.selectedNumber + " = " +
            (parseInt(parseInt(i + 1) * $scope.selectedNumber));
        }

        alert("Hint: " + display);
    }


    /**
     * Get subtraction possibilities.
     */
    $scope.getSubtractionHints = function() {

        var display = "";
        var newLine = "\n";

        for (var i = 0; i < $scope.selectedNumber; i++) {
            display += newLine + $scope.selectedNumber + " - " + parseInt(i + 1) + " = " +
            (parseInt($scope.selectedNumber) - parseInt(i + 1));
        }

        alert("Hint: " + display);
    }


    /**
     * This is the about information for the Math Tutor.
     */
    $scope.getMathAbout = function() {

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

});
