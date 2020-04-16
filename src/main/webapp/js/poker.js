/**
 * Created by Pat Keeler on 4/16/2020.
 */
$(document).ready(function() {
    let players = 0;
    $('#player_name').focus();
    $('#players').hide();
    emptyAllFields();
    $('#player_name').keypress(function(e) {
        var code = (event.keyCode ? event.keyCode : event.which);
        if (code == 13) {
            e.preventDefault();
            setAmountsToFixed();
            addToPlayers();
            computeTotals();
        }
    });


    function emptyAllFields() {
        $('#player_name').val('');
        $('#buyIn').val('');
        $('#bounty').val('');
        $('#lastMan').val('');
        $('#addOn').val('');
        $('.playerBuyIn').val('');
        $('.playerBounty').val('');
        $('.playerAddOn').val('');
        $('#totalPlayers').val('');
        $('#buyInTotal').val('');
        $('#bountyTotal').val('');
        $('#lastManTotal').val('');
        $('#addOnTotal').val('');
        $('#chopPlayers').val('');
        $('#chopAmount').val('');
        $('#lastManPlayers').val('');
        $('#lastManAmount').val('');
    }


    $('#savePlayer').click(function () {
        setAmountsToFixed();
        addToPlayers();
        computeTotals();
    });


    function addToPlayers() {
        let name = $('#player_name').val();
        let buyIn = $('#buyIn').val();
        let bounty = $('#bounty').val();
        let lastMan = $('#lastMan').val();
        let addOn = $('#addOn').val();
        let markup =
            "<tr class='item'>" +
            "<td><input id='checker' type='checkbox' name='record'></td>" +
            "<td type='text'>" + name + "</td>" +
            "<td type='text' class='playerBuyIn'>" + buyIn + "</td>" +
            "<td type='text' class='playerBounty'>" + bounty + "</td>" +
            "<td type='text' class='playerLastMan'>" + lastMan + "</td>" +
            "<td type='text' class='playerAddOn'>0.00</td>" +
            "</tr>";
        $('#tbodyRow').append(markup);
        $('#players').show();
        players ++;
        $('#player_name').val('');
        $('#player_name').focus();
    };


    function setAmountsToFixed() {
        let buyIn = parseFloat($('#buyIn').val());
        let bounty = parseFloat($('#bounty').val());
        let lastMan = parseFloat($('#lastMan').val());
        let addOn = parseFloat($('#addOn').val());
        $('#buyIn').val(buyIn.toFixed(2));
        $('#bounty').val(bounty.toFixed(2));
        $('#lastMan').val(lastMan.toFixed(2));
        $('#addOn').val(addOn.toFixed(2));
    }


    // Find and remove selected table rows
    $("#delete-row").click(function() {
        $("#tbodyRow").find('input[name="record"]').each(function(){
            if($(this).is(":checked")){
                $(this).parents("tr").remove();
                players --;
                computeTotals();
                if (players == 0) {
                    $('#players').hide();
                }
            }
        });
        $('#player_name').focus();
    });


    // Add BuyIn amount to selected table rows
    $("#btnRebuy").click(function() {
        let buyIn  = 0;
        let newBuyIn  = 0;
        let bounty = 0;
        let newBounty = 0;
        let total  = 0;

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
    $("#btnAddOn").click(function(){
        let addOn  = 0;
        let newAddOn  = 0;
        let total  = 0;

        //Loop through all checked CheckBoxes in GridView.
        $("#tbodyRow input[type=checkbox]:checked").each(function () {
            let row = $(this).closest("tr")[0];
            addOn  = ($('#addOn')).val();
            newAddOn  = row.cells[5].innerHTML;
            total  = parseFloat(addOn) + parseFloat(newAddOn);
            row.cells[5].innerHTML = total.toFixed(2);

            row.cells[0].children[0].checked = false;
        });
        computeTotals();
    });


    // Accumulate table columns
    function computeTotals() {

        let buyInTotal = 0;
        let bountyTotal = 0;
        let lastManTotal = 0;
        let addOnTotal = 0;

        let temp = 0;

        let table = $('table tbody#tbodyRow');
        table.find('tr').each(function (i) {
            var $tds = $(this).find('td'),

                //First parseFloat does not work
                temp = parseFloat($tds.eq(2).val());

            buyInTotal += parseFloat($tds.eq(2).text());
            bountyTotal += parseFloat($tds.eq(3).text());
            lastManTotal += parseFloat($tds.eq(4).text());
            addOnTotal += parseFloat($tds.eq(5).text());
        });
        setTotals(buyInTotal, bountyTotal, lastManTotal, addOnTotal);
    }


    //Set the Running totals
    function setTotals(buyInTotal, bountyTotal, lastManTotal, addOnTotal) {
        $('#totalPlayers').val(players);
        $('#buyInTotal').val(buyInTotal.toFixed(2));
        $('#bountyTotal').val(bountyTotal.toFixed(2));
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


    //Compute chop amount
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
        let total = parseInt(buyInTotal) + parseInt(addOnTotal);
        let each  = parseInt(total / 10);

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
        let total = parseInt(buyInTotal) +
            parseInt(addOnTotal) +
            parseInt(lastManTotal);
        let each  = parseInt(total / 10);

        //Loop through all checked CheckBoxes in GridView.
        $("#winners").find('tr').each(function () {
            let row = $(this).find('.percent');
            let percent = parseFloat(row.val() / 10);
            let playerAmount = parseFloat(percent) * parseFloat(each);
            $(this).find('.winAmount').val(playerAmount.toFixed(2));
        });

    });

});

