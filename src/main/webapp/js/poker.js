/**
 * Created by Pat Keeler on 4/16/2020.
 */
$(document).ready(function() {

    let playerCount = 0;

    let _addOn      = $('#addOn');
    let _bounty     = $('#bounty');
    let _buyIn      = $('#buyIn');
    let _fee        = $('#fee');
    let _lastMan    = $('#lastMan');
    let _players    = $('#players');
    let _playerName = $('#playerName');

    _players.hide();
    _playerName.focus();
    emptyAllFields();


    _playerName.keypress(function(e) {
        let code = (e.keyCode ? e.keyCode : e.which);
        if (code == 13) {
            e.preventDefault();
            setAmountsToFixed();
            addToPlayers();
            computeTotals();
        }
    });


    function emptyAllFields() {
        _playerName.val('');
        _buyIn.val();
        _bounty.val();
        _fee.val();
        _lastMan.val();
        _addOn.val();
        $('.playerBuyIn').val();
        $('.playerBounty').val();
        $('.playerFee').val();
        $('.playerAddOn').val();
        $('#totalPlayers').val();
        $('#buyInTotal').val();
        $('#bountyTotal').val();
        $('#feeTotal').val();
        $('#lastManTotal').val();
        $('#addOnTotal').val();
        $('#chopPlayers').val();
        $('#chopAmount').val();
        $('#lastManPlayers').val();
        $('#lastManAmount').val();
    }


    $('#savePlayer').click(function () {
        setAmountsToFixed();
        addToPlayers();
        computeTotals();
    });


    function addToPlayers() {
        let name = _playerName;
        let buyIn = _buyIn.val();
        let bounty = _bounty.val();
        let fee = _fee.val();
        let lastMan = _lastMan.val();
        let markup =
            "<tr class='item'>" +
            "<td><input id='checker' type='checkbox' name='record'></td>" +
            "<td>" + name.val() + "</td>" +
            "<td class='playerBuyIn'>" + buyIn + "</td>" +
            "<td class='playerBounty'>" + bounty + "</td>" +
            "<td class='playerFee'>" + fee + "</td>" +
            "<td class='playerLastMan'>" + lastMan + "</td>" +
            "<td class='playerAddOn'>0.00</td>" +
            "</tr>";
        $('#tbodyRow').append(markup);
        _players.show();
        playerCount ++;
        name.val('');
        name.focus();
    }


    function setAmountsToFixed() {
        let buyIn = parseFloat(_buyIn.val());
        let bounty = parseFloat(_bounty.val());
        let lastMan = parseFloat(_lastMan.val());
        let fee = parseFloat(_fee.val());
        let addOn = parseFloat(_addOn.val());
        _buyIn.val(buyIn.toFixed(2));
        _bounty.val(bounty.toFixed(2));
        _fee.val(fee.toFixed(2));
        _lastMan.val(lastMan.toFixed(2));
        _addOn.val(addOn.toFixed(2));
    }


    // Set all checked boxes on
    $("#selectAll").click(function() {
        $("#tbodyRow").find('input[name="record"]').each(function(){
            $(this).prop('checked', true);
        });
        _playerName.focus();
    });


    // Set all checked boxes off
    $("#unSelectAll").click(function() {
        $("#tbodyRow").find('input[name="record"]').each(function(){
            $(this).prop('checked', false);
        });
        _playerName.focus();
    });


    // Find and remove selected table rows
    $("#delete-row").click(function() {
        $("#tbodyRow").find('input[name="record"]').each(function(){
            if($(this).is(":checked")){
                $(this).parents("tr").remove();
                playerCount --;
                computeTotals();
                if (playerCount === 0) {
                    _players.hide();
                }
            }
        });
        _playerName.focus();
    });


    // Add BuyIn amount to selected table rows
    $("#btnPlusRebuy").click(function() {
        let buyIn     = 0;
        let newBuyIn  = 0;
        let bounty    = 0;
        let newBounty = 0;
        let total     = 0;

        //Loop through all checked CheckBoxes in GridView.
        $("#tbodyRow input[type=checkbox]:checked").each(function () {
            let row = $(this).closest("tr")[0];
            buyIn  = ($('#buyIn')).val();
            newBuyIn  = row.cells[2].innerHTML;
            total  = parseFloat(buyIn) + parseFloat(newBuyIn);
            row.cells[2].innerHTML = total.toFixed(2);

            bounty = ($('#bounty')).val();
            newBounty = row.cells[3].innerHTML;
            total  = parseFloat(bounty) + parseFloat(newBounty);
            row.cells[3].innerHTML = total.toFixed(2);

            row.cells[0].children[0].checked = false;
        });
        computeTotals();
    });


    // Add the AddOn amount to selected table rows
    $("#btnPlusAddOn").click(function(){
        let addOn  = 0;
        let newAddOn  = 0;
        let total  = 0;

        //Loop through all checked CheckBoxes in GridView.
        $("#tbodyRow input[type=checkbox]:checked").each(function () {
            let row = $(this).closest("tr")[0];
            addOn  = ($('#addOn')).val();
            newAddOn  = row.cells[6].innerHTML;
            if (newAddOn == 0) {
                total  = parseFloat(addOn) + parseFloat(newAddOn);
                row.cells[6].innerHTML = total.toFixed(2);

                row.cells[0].children[0].checked = false;
            }
            else {
                let name = row.cells[1].innerHTML;
                alert("Rejected: " + name + " has already done an Addon!");

                row.cells[0].children[0].checked = false;
            }
        });
        computeTotals();
    });


    // Add BuyIn amount to selected table rows
    $("#btnMinusRebuy").click(function() {
        let buyIn     = 0;
        let newBuyIn  = 0;
        let bounty    = 0;
        let newBounty = 0;
        let total     = 0;

        //Loop through all checked CheckBoxes in GridView.
        $("#tbodyRow input[type=checkbox]:checked").each(function () {
            let row = $(this).closest("tr")[0];
            buyIn  = ($('#buyIn')).val();
            newBuyIn  = row.cells[2].innerHTML;
            if (parseFloat(newBuyIn) > parseFloat(buyIn)) {
                total  = parseFloat(newBuyIn) - parseFloat(buyIn);
                row.cells[2].innerHTML = total.toFixed(2);

                bounty = ($('#bounty')).val();
                newBounty = row.cells[3].innerHTML;
                total  =  parseFloat(newBounty) - parseFloat(bounty);
                row.cells[3].innerHTML = total.toFixed(2);

                row.cells[0].children[0].checked = false;
            } else {
                let name = row.cells[1].innerHTML;
                alert("Rejected: " + name + " has not done a rebuy!");

                row.cells[0].children[0].checked = false;
            }
        });
        computeTotals();
    });


    // Add the AddOn amount to selected table rows
    $("#btnMinusAddOn").click(function(){
        let addOn  = 0;
        let newAddOn  = 0;
        let total  = 0;

        //Loop through all checked CheckBoxes in GridView.
        $("#tbodyRow input[type=checkbox]:checked").each(function () {
            let row = $(this).closest("tr")[0];
            addOn  = ($('#addOn')).val();
            newAddOn  = row.cells[6].innerHTML;
            if (newAddOn > 0) {
                total  =  parseFloat(newAddOn) - parseFloat(addOn);
                row.cells[6].innerHTML = total.toFixed(2);

                row.cells[0].children[0].checked = false;
            } else {
                let name = row.cells[1].innerHTML;
                alert("Rejected: " + name + " has not done an AddOn!");

                row.cells[0].children[0].checked = false;
            }
        });
        computeTotals();
    });


    // Accumulate table columns
    function computeTotals() {

        let buyInTotal = 0;
        let bountyTotal = 0;
        let feeTotal = 0;
        let lastManTotal = 0;
        let addOnTotal = 0;

        let temp = 0;

        let table = $('table tbody#tbodyRow');
        table.find('tr').each(function (i) {
            let $tds = $(this).find('td'),

                //First parseFloat does not work
                temp = parseFloat($tds.eq(2).val());

            buyInTotal += parseFloat($tds.eq(2).text());
            bountyTotal += parseFloat($tds.eq(3).text());
            feeTotal += parseFloat($tds.eq(4).text());
            lastManTotal += parseFloat($tds.eq(5).text());
            addOnTotal += parseFloat($tds.eq(6).text());
        });
        setTotals(buyInTotal, bountyTotal, feeTotal, lastManTotal, addOnTotal);
    }


    //Set the Running totals
    function setTotals(buyInTotal, bountyTotal, feeTotal, lastManTotal, addOnTotal) {
        $('#totalPlayers').val(playerCount);
        $('#buyInTotal').val(buyInTotal.toFixed(2));
        $('#bountyTotal').val(bountyTotal.toFixed(2));
        $('#feeTotal').val(feeTotal.toFixed(2));
        $('#lastManTotal').val(lastManTotal.toFixed(2));
        $('#addOnTotal').val(addOnTotal.toFixed(2));
    }


    //Compute chop amount
    $('#chopBtn').click(function() {
        let players = $('#chopPlayers').val();
        let buyInAmount = $('#buyInTotal').val();
        let addOnAmount = $('#addOnTotal').val();
        let total = parseFloat(buyInAmount) + parseFloat(addOnAmount);
        let each  = total / players;
        $('#chopAmount').val(each.toFixed(2));
    });


    //Compute BuyIn AddOn and LastMan amount
    $('#chopAllBtn').click(function() {
        let players = $('#chopPlayers').val();
        let buyInAmount = $('#buyInTotal').val();
        let addOnAmount = $('#addOnTotal').val();
        let lastManAmount = $('#lastManTotal').val();
        let total = parseFloat(buyInAmount) +
            parseFloat(addOnAmount) +
            parseFloat(lastManAmount);
        let each  = total / players;
        $('#chopAmount').val(each.toFixed(2));
    });


    //Compute last man chop amount
    $('#chopLastManBtn').click(function() {
        let players = $('#lastManPlayers').val();
        if (players > 0) {
            let lastManAmount = $('#lastManTotal').val();
            let total = parseFloat(lastManAmount);
            let each  = total / players;
            $('#lastManAmount').val(each.toFixed(2));
        }
        else {
            $('#lastManAmount').val(0.00.toFixed(2));
        }
    });


    //Add winner row
    $('#addWinnerBtn').click(function() {
        let markup =
            "<tr class='item'>" +
            "<td><input size='7' id='checker' type='checkbox' name='record'></td>" +
            "<td><input size='7' type='textbox' class='percent'></td>" +
            "<td><input size='7' type='textbox' class='winAmount'></td>" +
            "</tr>";
        $('#winners').append(markup);
    });


    // Find and remove selected winner rows
    $("#deleteWinnerBtn").click(function() {
        $("#winners").find('input[name="record"]').each(function(){
            if($(this).is(":checked")){
                $(this).parents("tr").remove();
            }
        });
    });


    //Compute winner amounts
    $('#computeBtn').click(function() {
        let buyInTotal = $('#buyInTotal').val();
        let addOnTotal = $('#addOnTotal').val();
        let total = parseFloat(buyInTotal) + parseFloat(addOnTotal);
        let each  = parseFloat(total / 10);

        //Loop through all checked CheckBoxes in GridView.
        $("#winners").find('tr').each(function () {
            let row = $(this).find('.percent');
            let percent = parseFloat(row.val() / 10);
            let playerAmount = parseFloat(percent) * parseFloat(each);
            $(this).find('.winAmount').val(playerAmount.toFixed(2));
        });

    });


    //Compute winner amounts
    $('#computeLastManBtn').click(function() {
        let buyInTotal = $('#buyInTotal').val();
        let addOnTotal = $('#addOnTotal').val();
        let lastManTotal = $('#lastManTotal').val();
        let total = parseFloat(buyInTotal) +
            parseFloat(addOnTotal) +
            parseFloat(lastManTotal);
        let each  = parseFloat(total / 10);

        //Loop through all checked CheckBoxes in GridView.
        $("#winners").find('tr').each(function () {
            let row = $(this).find('.percent');
            let percent = parseFloat(row.val() / 10);
            let playerAmount = parseFloat(percent) * parseFloat(each);
            $(this).find('.winAmount').val(playerAmount.toFixed(2));
        });

    });
});



/**
 * This is user information for the Poker app.
 */
function getPokerAbout() {

    alert("This Poker app was written to help keep up with the numbers for poker tournaments. "
        + "It is generic and will be useful for most tournaments, hopefully yours."
        + "\n\n\tFollow these steps to get started:"
        + "\n\t1. The cursor will be focused on the name textbox, type in the first player\'s name."
        + "\n\t2. Tab to each amount box and enter the amount for that column, or leave at 0.00."
        + "\n\t3. Tab one more time to the \"Save Player\" button and hit enter, the first player is saved."
        + "\n\t4. Now for each entrant type in the players name, change amounts if needed, and hit enter. "
        + "The amounts you entered last will be the defaults for the next player. "
        + "\n\n\tThe app will keep totals of rebuys and addons as you play by using the buttons for rebuys and addons."
        + "\n\tPlay around with the Chop and Winner sections to view the different results "
        + "depending on the default amounts, totals and number of chops or winners. "
        + "\n\tFor winners, add rows and enter the percentage for each winning position. "
        + "The percentages should be entered without decimals, i.e. 50, 33, 17 or 40, 30, 20, 10, etc, totaling 100."
        + "\n\nHave Fun!");

    return true;
}
