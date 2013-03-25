/*
 * This updates the form when options are clicked to change what is available.
 */
function checkIndentAmountEnabled() {

	if (document.mainForm.indentCheckBox.checked == true) {
		document.mainForm.indentAmount.disabled = false;
	} else if (document.mainForm.indentCheckBox.checked == false) {
		document.mainForm.indentAmount.disabled = true;
	}

	return true;
}

/*
 * This verifies indent amount.
 */
function verifyIndent() {

	// alert("Indent amount = " + document.mainForm.indentAmount.value);

	if ((document.mainForm.indentAmount.value < "1")
			|| (document.mainForm.indentAmount.value > "9")) {
		if (document.mainForm.indentAmount.value != " ") {
			alert("Indent Amount must be betwen 1 and 9!");
			document.mainForm.indentAmount.value = 2;
		}
	}

	return true;
}

/*
 * This turns off Quotes Only when Reformat and Quotes is checked..
 */
function verifyAddQuotesAndReformat() {

	// alert("verifyAddQuotesAndReformat()");

	if (document.mainForm.addQuotesAndReformatCheckBox.checked == true) {
		document.mainForm.addQuotesOnlyCheckBox.checked = false;
        document.mainForm.removeQuotesAndReformatCheckBox.checked = false;
        document.mainForm.removeQuotesOnlyCheckBox.checked = false;
	}

	return true;
}

/*
 * This turns off Reformat and Quotes when Quotes Only is checked.
 */
function verifyAddQuotesOnly() {

	// alert("verifyAddQuotesOnly()")

	if (document.mainForm.addQuotesOnlyCheckBox.checked == true) {
		document.mainForm.addQuotesAndReformatCheckBox.checked = false;
        document.mainForm.removeQuotesAndReformatCheckBox.checked = false;
        document.mainForm.removeQuotesOnlyCheckBox.checked = false;
	}

	return true;
}

/*
 * This turns off Quotes Only when Reformat and Quotes is checked..
 */
function verifyRemoveQuotesAndReformat() {

	// alert("verifyRemoveQuotesAndReformat()");

	if (document.mainForm.removeQuotesAndReformatCheckBox.checked == true) {
        document.mainForm.addQuotesAndReformatCheckBox.checked = false;
        document.mainForm.addQuotesOnlyCheckBox.checked = false;
        document.mainForm.removeQuotesOnlyCheckBox.checked = false;
	}

	return true;
}

/*
 * This turns off Reformat and Quotes when Quotes Only is checked.
 */
function verifyRemoveQuotesOnly() {

	// alert("verifyRemoveQuotesOnly()")

	if (document.mainForm.removeQuotesOnlyCheckBox.checked == true) {
        document.mainForm.addQuotesAndReformatCheckBox.checked = false;
        document.mainForm.addQuotesOnlyCheckBox.checked = false;
		document.mainForm.removeQuotesAndReformatCheckBox.checked = false;
	}

	return true;
}
