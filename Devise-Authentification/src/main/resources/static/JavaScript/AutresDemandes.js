$(document).ready(function() {
    $.ajax({
        url: 'http://localhost:8099/api/v1/demandes/getdemandeautresutilisateurs/' + localStorage.getItem('userId'),
        type: 'GET',
        success: function(data) {
            var tbody = $('#requests-table tbody');
            tbody.empty();

            data.forEach(function(demande) {
                var row = $('<tr>');

                $('<td>')
                    .addClass('user-name')
                    .attr('title', 'Voir commentaires')
                    .data('user-id', demande.idDemandeur)
                    .text(demande.nomPrenomDemandeur)
                    .appendTo(row);

                $('<td>').text(demande.deviseVoulu).appendTo(row);
                $('<td>').text(demande.deviseOfferte).appendTo(row);
                $('<td>').text(demande.montantVoulu).appendTo(row);
                $('<td>').append($('<button>')
                    .text('Faire une offre')
                    .click(function() {
                        faireOffre(demande); // Fonction pour gérer l'action du bouton
                    }))
                    .appendTo(row);
                $('<td hidden>').text(demande.idDemande).appendTo(row);
                row.appendTo(tbody);

            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log('Erreur lors de la récupération des données : ' + textStatus);
        }
    });

    $(document).on('click', '.user-name', function() {
        var userId = $(this).data('user-id');
        window.location.href = 'Commentaire.html?userId=' + userId;
    });

    function faireOffre(demande) {
        var offre = {
            idDemande:demande.idDemande ,
            idOffreur : localStorage.getItem('userId'),
        };

        $.ajax({
            url: 'http://localhost:8099/api/v1/Offres/addoffre',
            type: 'POST',
            data: JSON.stringify(offre),
            contentType: 'application/json',
            success: function(data) {
                console.log(data);
                alert('Offre envoyée avec succès !');
                location.reload();
            },

            error: function(jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 401) { // Si le code d'état est 401 Unauthorized
                    alert('Votre session a expiré. Veuillez vous reconnecter.');
                    // Redirigez l'utilisateur vers la page de connexion ou rafraîchissez le token si possible
                    window.location.href = 'Connexion.html';
                } else {
                    // Traitement en cas d'erreur de la requête
                    alert('Erreur lors de l\'envoi de l\'offre');
                    console.log('Erreur lors de l\'envoi de l\'offre : ' +  textStatus);
                }
            }
        });
    }
});
