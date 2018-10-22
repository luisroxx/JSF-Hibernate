/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {
    //hang on event of form with id=myform
    $("#myform").submit(function(e) {

        //prevent Default functionality
        e.preventDefault();

        //do your own request an handle the results
        $.ajax({
                url: "http://localhost:8080/prjEstudoPrimefaces-war/api/auth",
                type: 'post',
                contentType: 'application/json',
                dataType: 'application/json',
                data: JSON.stringify($("#myform").serializeArray()),
                success: function(data) {
                 
                }
        });

    });

});



//JSON.stringify