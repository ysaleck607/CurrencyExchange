$(document).ready(function() {

    // Écouteur d'événement pour la soumission du formulaire
    $('#exchangeForm').submit(function(event) {
        // Empêche le rechargement de la page
        event.preventDefault();

        // Récupération des valeurs du formulaire
        var baseAmount =$('#baseAmount').val();
        var amount = $('#targetAmount').val();
        var sourceCurrency = $('#baseCurrency').val();
        var targetCurrency = $('#targetCurrency').val();

        // Vérification si la devise offerte est la même que la devise souhaitée
        if (sourceCurrency === targetCurrency) {
            alert("La devise offerte ne peut pas être la même que la devise souhaitée. Veuillez choisir des devises différentes.");
            return false; // Interrompt l'exécution de la fonction
        }

        // Créer un objet de données à envoyer au serveur backend
        var data = {
            idDemandeur: localStorage.getItem('userId'),
            montantVoulu: amount,
            montantOffert: baseAmount,
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
