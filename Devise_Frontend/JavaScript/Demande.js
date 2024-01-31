$(document).ready(function() {
    // Faire une requête à l'API pour obtenir la liste des devises
    $.ajax({
        url: 'https://api.exchangerate-api.com/v4/latest/CAD',
        method: 'GET',
        success: function(data) {
            var currencies = Object.keys(data.rates);
            // Ajouter chaque devise aux menus déroulants
            for(var i = 0; i < currencies.length; i++) {
                $('#source-currency').append(new Option(currencies[i], currencies[i]));
                $('#target-currency').append(new Option(currencies[i], currencies[i]));
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert('Erreur lors de la récupération des devises : ' + textStatus);
        }
    });

    // Écouteur d'événement pour la soumission du formulaire
    $('#exchangeForm').submit(function(event) {
        // Empêche le rechargement de la page
        event.preventDefault();

        // Récupération des valeurs du formulaire
        var amount = $('#amount').val();
        var sourceCurrency = $('#source-currency').val();
        var targetCurrency = $('#target-currency').val();

        // Vérification si la devise offerte est la même que la devise souhaitée
        if (sourceCurrency === targetCurrency) {
            alert("La devise offerte ne peut pas être la même que la devise souhaitée. Veuillez choisir des devises différentes.");
            return false; // Interrompt l'exécution de la fonction
        }

        // Créer un objet de données à envoyer au serveur backend
        var data = {
            idDemandeur: localStorage.getItem('userId'),
            montantVoulu: amount,
            deviseOfferte: sourceCurrency,
            deviseVoulue: targetCurrency
        };

        // Effectuer la requête AJAX
        $.ajax({
            type: "POST",
            url: "http://localhost:8099/api/v1/demandes/adddemand",
            data: JSON.stringify(data),
            contentType: "application/json",
            headers: { 'Authorization': 'Bearer ' + localStorage.getItem('userToken') },
            success: function(response) {
                // Traiter la réponse du serveur en cas de succès
                console.log(response)
                alert("Votre demande d'échange a été publiée avec succès !");
                window.location.href = 'TableauBord.html';
            },
            error: function(jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 401) { // Si le code d'état est 401 Unauthorized
                    alert('Votre session a expiré. Veuillez vous reconnecter.');
                    // Redirigez l'utilisateur vers la page de connexion ou rafraîchissez le token si possible
                    window.location.href = 'Connexion.html';
                } else {
                    console.log(localStorage.getItem('userToken'));
                    // Traitement en cas d'erreur de la requête
                    alert("Une erreur s'est produite lors de la publication de la demande d'échange :");
                    console.log("Une erreur s'est produite lors de la publication de la demande d'échange :" +  textStatus);
                }
            }
        });
    });
});
