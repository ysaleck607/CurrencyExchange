$(document).ready(function() {
    // Appel à l'API pour récupérer la liste des offres
    $.ajax({
        url: 'http://localhost:8099/api/v1/Offres/getdoffresutilisateur/' + localStorage.getItem('userId'),
        type: 'GET',
        success: function(data) {
            // Boucle sur chaque offre et ajoute une ligne à la table
            $.each(data, function(index, offre) {
                var chatButton = '';
                // Vérifier si le statut de l'offre est "ENATTENTE" pour afficher le bouton de chat
               /* if (offre.statutOffre === 'ENATTENTE') {
                    chatButton = '<button class="chat-button" data-offeror-id="' + offre.idDemandeur + '">Chat</button>';
                }*/
                //${(offre.statutOffre === 'EN_ATTENTE') ? '<button class="chat-button" data-offer-id="' + offre.idOffre + '">Chat</button>' : ''}
                $('#offersTable tbody').append(`
                    <tr>
                        <td class="offeror-name" data-user-id="${offre.idDemandeur} " title="Voir commentaires">${offre.nomPrenomDemandeur}</td>
                        <td>${offre.nomPrenomOffreur}</td> 
                        <td>${offre.deviseVoulu}</td>
                        <td>${offre.deviseOfferte}</td>
                        <td>${offre.montantVoulu}</td>
                        <td>${offre.statutOffre}</td>
                        <td class="actions">
                             ${offre.statutOffre  != 'ENATTENTE' ? '<button class="chat-button" data-user-id="' + offre.idDemandeur + '">Chat</button>' : ''}
                             ${offre.statutOffre != 'PAYER' && offre.statutOffre != 'TERMINER' ? '<button class="pay-button" data-id="' + offre.idOffre + '">Payer</button>' : ''}
                             ${offre.statutOffre == 'TERMINER' ? '<button class="leave-comment-button" data-user-id="' + offre.idDemandeur + '">Laisser un commentaire</button>' : ''}
                            <input type="hidden" id="id" value="${offre.idOffre}" />
                            <input type="hidden" id="id2" value="${offre.idDemande}" />
                            
                        </td>
                    </tr>
                `);
            });
            // Ajoute un écouteur d'événements click pour chaque bouton de paiement
            $('.pay-button').on('click', function() {
                var offerId = $(this).data('id');
                var deviseVoulue = $(this).closest('tr').find('td:nth-child(3)').text().trim();

                if (deviseVoulue === 'CFA') {
                    // Redirection vers la page pour le paiement via Mobile Money
                    window.location.href = 'paiement_mobilemoney.html?id=' + idDemande;
                }
                else
                {
                    // Effectuer la requête AJAX pour payer la demande
                    $.ajax({
                        url: "http://localhost:8099/api/v1/Offres/payerOffre/" + offerId,
                        type: 'PUT',
                        success: function(data) {
                            console.log(data);
                            alert('Paiement réussi !');
                            location.reload();

                        },
                        error: function(jqXHR, textStatus, errorThrown) {
                            if (jqXHR.status === 401) { // Si le code d'état est 401 Unauthorized
                                alert('Votre session a expiré. Veuillez vous reconnecter.');
                                // Redirigez l'utilisateur vers la page de connexion ou rafraîchissez le token si possible
                                window.location.href = 'Connexion.html';
                            } else {
                                // Traitement en cas d'erreur de la requête
                                alert('Erreur lors du paiement  ');
                                console.log('Erreur lors du paiement : ' +  textStatus);
                            }
                        }
                    });
                }

            });

            $('.leave-comment-button').click(function() {
                $('#comment-section').show();
                $('#submit-comment').data('user-id', $(this).data('user-id'));
            });

            $('.offeror-name').click(function() {
                var userId = $(this).data('user-id');
                window.location.href = 'Commentaire.html?userId=' + userId;
            });

            // Ajout de l'événement click pour le bouton de chat
            $('.chat-button').click(function() {
                var offerorUserId = localStorage.getItem('userId');
                var demanderUserId = //$(this).data('user-id');
                    $(this).closest('tr').find('.offeror-name').data('user-id');
                var noEncodedDemandeurNomPrenom = $(this).closest('tr').find('.offeror-name').text().trim();
                var noEncodedOffreurNomPrenom = $(this).closest('tr').find('td:eq(1)').text().trim();
                console.log(offerorUserId);
                console.log(demanderUserId);
                var demandeurNomPrenom = encodeURIComponent(noEncodedDemandeurNomPrenom);
                var offreurNomPrenom = encodeURIComponent(noEncodedOffreurNomPrenom);
                // Redirection vers la page de chat avec les paramètres nécessaires
                window.location.href = 'chat.html?offerorUserId=' + offerorUserId + '&offreurName=' + offreurNomPrenom
                    + '&demandeurUserId=' + demanderUserId + '&demandeurName=' + demandeurNomPrenom;
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert('Erreur lors de la récupération des offres : ' + textStatus);
        }
    });
    $('#submit-comment').click(function() {
        var commentText = $('#comment-text').val();
        var userId = $(this).data('user-id');
        var rating = $('#rating').val();
        var uti = localStorage.getItem('userId');
        var offerId = $('#id').val();
        var demandId = $('#id2').val();
        console.log(userId);
        console.log(uti);
        console.log(offerId);
        console.log(demandId);
        var data ={
            IdUtilisateur: userId,
            IdUtilisateurNote: uti,
            commentaire: commentText,
            note: rating,
            idOffre: offerId,
            idDemande: demandId
        }

        $.ajax({
            url: 'http://localhost:8099/api/v1/commentaires/ajouterCommentaire',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(response) {
                alert('Commentaire envoyé avec succès.');
                $('#comment-section').hide();
            },
            error: function(jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 401) { // Si le code d'état est 401 Unauthorized
                    alert('Votre session a expiré. Veuillez vous reconnecter.');
                    // Redirigez l'utilisateur vers la page de connexion ou rafraîchissez le token si possible
                    window.location.href = 'Connexion.html';
                } else {
                    // Traitement en cas d'erreur de la requête
                    alert('Erreur lors de la soumission du commentaire : ');
                    console.log('Erreur lors de la soumission du commentaire : ' +  textStatus);
                }
            }
        });
    });
});
