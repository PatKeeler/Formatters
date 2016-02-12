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
	
function getPasswords(pwdLen) {

    var num = $("[name=pwdLength]").val();
    var test = parseInt(num);
    alert("test = " + test);

    //set pwdLen for testing
    pwdLen = 16;

    var range = 0;

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

        for (var i = 0; i < pwdLen; i++) {

            buffer += PasswordImage[getRandomInteger(range)];
        }

        alert(buffer);
    }

	return true;
}


/**
 * Random number generator.
 *
 * Range is depth into PasswordImage array.
 */
function getRandomInteger(range) {
	
	//alert("two");

	return randomnumber = Math.floor(Math.random() * range + 1);
}
