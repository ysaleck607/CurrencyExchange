$(document).ready(function() {
    // Écouter l'événement de soumission du formulaire
    $('#profileForm').submit(function(event) {
        event.preventDefault(); // Empêcher le rechargement de la page

        // Créer l'objet de données à envoyer au serveur
        var data = {
            //adresse : $('#address').val(),
            email : $('#email').val(),
            motDePasse: $('#password').val()
        };
         var userId = localStorage.getItem('userId')
        // Envoyer la requête AJAX
        $.ajax({
            url: 'http://localhost:8099/api/v1/utilisateurs/update/' + userId + '?' + "email="+data.email,
            type: 'PUT',
            data: JSON.stringify(data),
            contentType: 'application/json',
            headers: { 'Authorization': 'Bearer ' + localStorage.getItem('userToken') },
            success: function(response) {
                // Traitement réussi de la requête
                console.log('Profil modifié avec succès');
                window.location.href = "TableauBord.html";

            },
            error: function(jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 401) { // Si le code d'état est 401 Unauthorized
                    alert('Votre session a expiré. Veuillez vous reconnecter.');
                    // Redirigez l'utilisateur vers la page de connexion ou rafraîchissez le token si possible
                    window.location.href = 'Connexion.html';
                } else {
                    // Traitement en cas d'erreur de la requête
                    console.log('Erreur lors de la modification du profil : ' + textStatus);
                }
            }

        });
    });
});
