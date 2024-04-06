$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8099/api/v1/demandes/getdemandes/" + localStorage.getItem('userId'),
        type: "GET",
        success: function(data) {
            data.forEach(function(demande) {
                $('#exchangeRequestsTable tbody').append(
                    `<tr>
                        <td>${demande.nomPrenomDemandeur}</td>
                        <td>${demande.deviseVoulu}</td>
                        <td>${demande.deviseOfferte}</td>
                        <td>${demande.montantVoulu}</td>
                        <td>${demande.statut}</td>
                        <td>
                            ${(demande.statut != 'PAYER' && demande.statut != 'ANNULER' && demande.statut != 'TERMINER') ? '<button class="pay-button" data-id="' + demande.idDemande + '">Payer</button>' : ''} 
                            ${(demande.statut != 'PAYER' && demande.statut != 'ANNULER' && demande.statut != 'TERMINER') ? '<button class="cancel-button" data-id="' + demande.idDemande + '">Annuler</button>' : ''}
                            <a href="offre.html?id=${demande.idDemande}" class="view-offers-button">Voir Offres</a>
                        </td>
                        <td class="d-none">${demande.idDemande}</td>
                    </tr>`
                );
            });

            $('.pay-button').click(function() {
                var idDemande = $(this).data('id');
                console.log(idDemande);
                var deviseOfferte = $(this).closest('tr').find('td:nth-child(3)').text().trim();
                var montant = $(this).closest('tr').find('td:nth-child(4)').text().trim();
                if (deviseOfferte === 'XOF') {
                    // Redirection vers la page pour le paiement via Mobile Money
                    $.ajax({
                        url: "http://localhost:8099/paiement/pay-by-mobilemoney" ,
                        type: "POST",
                        success: function(response) {
                            console.log(response);
                            alert('Demande payée avec succès !');
                            location.reload(); // Recharger la page pour mettre à jour le statut
                        },
                        error: function(error) {
                            console.error("Une erreur s'est produite :", error);
                            alert('Erreur lors du paiement ');
                        }
                    });
                }
                else
                {
                    // Diriger vers stripe pour payer la demande
                    window.location.href = 'stripePay.html?amount=' + montant;
                }
            });


            $('.cancel-button').click(function() {
                var idDemande = $(this).data('id');
                $.ajax({
                    url: "http://localhost:8099/api/v1/demandes/annulerdemande/" + idDemande ,
                    type: "PUT",
                    success: function() {
                        alert('Demande annulée avec succès !');
                        location.reload();  // Recharger la page pour mettre à jour le statut
                    },
                    error: function() {
                        alert('Erreur lors de l\'annulation de la demande ');
                    }
                });
            });
        }
    });
});

