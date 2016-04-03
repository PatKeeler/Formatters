/**
 * Created by Grandpa on 3/14/2016.
 */


/**
 * String of animation dots
 */
var dots = ".....";

/**
 * Number of dots to display
 */
var numberOfDots = 0;


function getFormattedSms(data) {

    // Check for the various File API support.
    if (window.File && window.FileReader && window.FileList && window.Blob) {
        // Great success! All the File APIs are supported.
        //alert("All supported");
    } else {
        alert('The File APIs are not fully supported in this browser' +
            ', please update your browser to a newer version.');
    }

    $("[name=outputSMS]").val("Formatting");

    //Set function and interval to show working
    var t = setInterval("animateFormatting()", 500);

    // obtain input element through DOM
    var upFile = document.getElementById('file').files[0];

    var yourName = $("[name=yourName]").val();
    var theirName = $("[name=theirName]").val();

    var formData = new FormData();
    formData.append('yourName', yourName);
    formData.append('theirName', theirName);
    formData.append('inputSmsFile', upFile);

    $.ajax({
        url: "ReformatSms",
        data: formData,
        type: "POST",
        dataType: "html",
        processData: false,
        contentType: false,
        enctype: 'multipart/form-data',
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

    $("[name=outputSMS]").val(data);
    $("[name=outputSMS]").focus();
}


//Timer to display formatting animation
function animateFormatting() {

    numberOfDots = numberOfDots + 1;

    if (numberOfDots > 5) {
        numberOfDots = 1;
    }

    $("[name=outputSMS]").val("Formatting" + dots.substring(0, numberOfDots));
}

/*
 * This is the about information for the SMS formatter.
 */
function getSmsAbout() {

    alert("This tool will read a phone SMS text message backup/restore file"
        + "\n  and format it back into readable text. This was written for"
        + "\n  an Android S4 but should work on most backup/restore text "
        + "\n  files."
        + "\n\nTo Start - Save the user\'s text message chain you want to "
        + "\n  convert back to a readable format. The saved file will have a"
        + "\n  date-time name with an .xml extension which is in this"
        + "\n  format - \"sms-yyyymmddhhmmss.xml\".  Look for it in the"
        + "\n  \"Messaging\" directory on your phone."
        + "\n\nNext - plug your phone into your computer, from here you can: "
        + "\n  copy the file to your computer and browse to it with the "
        + "\n  \"Choose File\" button"
        + "\n     OR "
        + "\n  access the file on your phone with the \"Choose File\" button. "
        + "\n\nNext - Type in the names as follows: "
        + "\n  In the \"Enter your name:\" field you should put the name of "
        + "\n    the person who owns the phone. "
        + "\n  In the \"Enter other parties name:\" field you should put the "
        + "\n    name of the person who you texted with. "
        + "\n\nNow - Select the \"Process\" button to format the file."
        + "\n\nYou should see your formatted text in the "
        + "\n  \"Formatted SMS Message\" text box. "
        + "\n\nFinally - To create a new file with the formatted text messages:"
        + "\n  Put your cursor in the text box, select all the text and copy it "
        + "\n  to your clipboard. Now create a new file on your computer and "
        + "\n  paste the copied text into the new file. "
        + "\n\n**NOTE - I developed and tested this tool with a \"Chrome\" browser. "
        + "\n  If you have problems with your browser you can download Chrome"
        + "\n  for free and use it. Or, updating your browser may help."
        + "\n\nIf you have suggestions for enhancements or encounter errors send an "
        + "email to Pat@PatsTools.com and I'll look into it. ");

    return true;
}
