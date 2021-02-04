
function search() {
    $.ajax({
        url: "http://api.algafood.local:8080/payment-method",
        type: "get",

        success: function (response) {
            fillIn(response);
        }
    });
}

function register() {
    var paymentMethodJson = JSON.stringify({
        "description": $("#description-field").val()
    });

    console.log(paymentMethodJson);

    $.ajax({
        url: "http://api.algafood.local:8080/payment-method",
        type: "post",
        data: paymentMethodJson,
        contentType: "application/json",

        success: function (response) {
            alert("Forma de pagamento adicionada!");
            search();
        },

        error: function (error) {
            if (error.status == 400) {
                var problem = JSON.parse(error.responseText);
                alert(problem.userMessage);
            } else {
                alert("Erro ao cadastrar forma de pagamento!");
            }
        }
    });
}

function remove(paymentMethod) {
    $.ajax({
        url: "http://api.algafood.local:8080/payment-method/" + paymentMethod.id,
        type: "delete",

        success: function (response) {
            search();

            alert("Forma de pagamento removida!");
        },

        error: function (error) {
            if (error.status >= 400 && error.status <= 499) {
                var problem = JSON.parse(error.responseText);
                alert(problem.userMessage);
            } else {
                alert("Erro ao remover forma de pagamento!");
            }
        }
    });
}

function fillIn(paymentMethods) {
    $("#table tbody tr").remove();

    $.each(paymentMethods, function (i, paymentMethod) {
        var row = $("<tr>");

        var actionLink = $("<a href='#'>")
            .text("Excluir")
            .click(function (event) {
                event.preventDefault();
                remove(paymentMethod);
            });

        row.append(
            $("<td>").text(paymentMethod.id),
            $("<td>").text(paymentMethod.description),
            $("<td>").append(actionLink)
        );

        row.appendTo("#table");
    });
}


$("#btn-search").click(search);
$("#btn-register").click(register);