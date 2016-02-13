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

    if (isNaN($("[name=pwdLength]").val())) {
        alert("You may only enter a password length between 1 and 99!");
        $("[name=pwdLength]").css('background-color', 'yellow');
        $("[name=pwdLength]").focus();
        return;
    } else {
        $("[name=pwdLength]").css('background-color', 'white');
    }

    var exclusions = $("[name=excludeChars]").val();

    //var temp = exclusions.split('');
    //alert("exclusions: " + temp);

    var pwdLen = parseInt($("[name=pwdLength]").val());

    var range = 0;
    var pwds = ["","","",""];

    //Loop and generate a password for each depth level.
    for (var n = 0; n < 4; n++) {
        switch(n) {
            case 0:
                range = 15
                break;
            case 1:
                range = 35
                break;
            case 2:
                range = 61
                break;
            default:
                range = 73
        }

        var buffer = "";

        for (var i = 0; buffer.length < pwdLen; i++) {

            var karacter = PasswordImage[getRandomInteger(range)];

            if (isExcludedChar(exclusions, karacter)) {
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

	return randomnumber = Math.floor(Math.random() * range + 1);
}


/**
 * Load the passwords onto the page forms.
 *
 * @param pwds
 */
function setPassowrdsOnPage(pwds) {

    $("[name=hexOnly]").val(pwds[0]);
    $("[name=hexAllUpper]").val(pwds[1]);
    $("[name=hexUpperLower]").val(pwds[2]);
    $("[name=hexAllSpecial]").val(pwds[3]);
}


/**
 * Return true if character is excluded.
 *
 * @param excluded
 * @param a
 * @returns {boolean}
 */
function isExcludedChar(excluded, a) {

    for (var i = 0; i < excluded.length; i++) {
        if (excluded[i] == a) {
            return true;
        }
    }
}


/*
 * This is the user information for Password Generator.
 */
function getPasswordAbout() {

    alert("This Password Generator creates 4 levels of password strength "
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
