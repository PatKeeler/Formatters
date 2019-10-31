/**
 * Created by Pat Keeler on 11/16/2015.
 */


/**
 * The range of characters to make up the password.
 *
 * @type { string[] }
 */
var PasswordImage = [
            // 16 chars - Hex
            "0","1","2","3","4","5","6","7","8","9",
            "A","B","C","D","E","F",

            // 36 chars - Numbers and upper case letters
            "G","H","I","J","K","L","M",
            "N","O","P","Q","R","S","T",
            "U","V","W","X","Y","Z",

            // 62 chars - all but special characters
            "a","b","c","d","e","f","g","h","i","j","k","l","m",
            "n","o","p","q","r","s","t","u","v","w","x","y","z",

            // 74 chars - all.
            "!","@","#","$","%","^","*","(",")","_","+","="
    ];


function getPasswords() {

    const pwdLength = $("[name=pwdLength]");

    if (isNaN(pwdLength.val())) {
        alert("You may only enter a password length between 1 and 99!");
        pwdLength.css('background-color', 'yellow');
        pwdLength.focus();
        return;
    } else {
        pwdLength.css('background-color', 'white');
    }

    var exclusions = $("[name=excludeChars]").val();

    var pwdLen = parseInt(pwdLength.val());

    var range = 0;
    var pwds = ["","","",""];

    //Loop and generate a password for each depth level.
    for (var n = 0; n < 3; n++) {
        switch(n) {
            case 0:
                range = 15;
                break;
            case 1:
                range = 61;
                break;
            default:
                range = 73
        }

        var buffer = "";

        for (var i = 0; buffer.length < pwdLen; i++) {
            var karacter = PasswordImage[getRandomInteger(range)];
            if (exclusions.includes(karacter)) {
                continue;
            }
            buffer += karacter;
        }

        pwds[n] = buffer;
    }

    setPassowrdsOnPage(pwds);

	return true;
}


/**
 * Random number generator.
 *
 * Range is depth into PasswordImage array.
 */
function getRandomInteger(range) {

	return Math.floor(Math.random() * range + 1);
}

//Added comment for testing


/**
 * Load the passwords onto the page forms.
 *
 * @param pwds
 */
function setPassowrdsOnPage(pwds) {

    $("[name=hexOnly]").val(pwds[0]);
    $("[name=hexUpperLower]").val(pwds[1]);
    $("[name=hexAllSpecial]").val(pwds[2]);
}


/*
 * This is the user information for Password Generator.
 */
function getPasswordAbout() {

    alert("This Password Generator creates 3 levels of password strength "
        + "\n   which are defined in the table under the \"Generate\" button. "
        + "\n"
        + "\n  First - enter the password length you want. "
        + "\n     The default is 16, you can change it to any length less "
        + "\n     than 100. "
        + "\n"
        + "\n  Next - Add in any characters you do not want in the passwords. "
        + "\n     This space is used if some of the special characters are not "
        + "\n     allowed in passwords for the system you are logging into."
        + "\n"
        + "\n  Next - Select the \"Generate\" button to generate the passwords, "
        + "\n     you can select this button as many times as you want until "
        + "\n     you get a password you like. "
        + "\n"
        + "\n  Finally - Select the password you want and paste it into your "
        + "\n     systems new password settings. "
        + "\n"
        + "\n  Have fun! ");

    return true;
}
