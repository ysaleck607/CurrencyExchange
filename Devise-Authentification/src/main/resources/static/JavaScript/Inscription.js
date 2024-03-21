$(document).ready(function() {
    $('#inscriptionForm').submit(function(event) {
        event.preventDefault(); // Empêche le formulaire de se soumettre normalement

        // Récupérer les valeurs des champs du formulaire
        const nom = $("#nom").val();
        const prenom = $("#prenom").val();
        const adresse = $("#adresse").val();
        const email = $("#email").val();
        const motDePasse = $("#password").val();
        const confirmMotDepasse = $("#confirmPassword").val();
        const dateNaissance= $("#datenaissance").val();

        var formData = {
            nom: nom,
            prenom: prenom,
            adresse: adresse,
            email: email,
            motDePasse: motDePasse,
            dateNaissance: dateNaissance,
            role: "USER"
        };
       /* if (motDePasse !== confirmMotDepasse) {
            // Les mots de passe ne correspondent pas
            alert('Les mots de passe ne correspondent pas');
            return; // Interrompt l'exécution de la fonction
        }*/

        // Envoi de la requête AJAX avec les données du formulaire
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8099/api/v1/utilisateurs/register',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function(response) {
                // Traitement de la réponse du serveur en cas de succès
                console.log(response);
                alert('Inscription réussie !');
                window.location.href = 'Connexion.html';
            },
            error: function(jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 401) { // Si le code d'état est 401 Unauthorized
                    alert('Votre session a expiré. Veuillez vous reconnecter.');
                    // Redirigez l'utilisateur vers la page de connexion ou rafraîchissez le token si possible
                    window.location.href = 'Connexion.html';
                } else {
                    // Traitement en cas d'erreur de la requête
                    alert('Erreur lors de lors de l\'inscription');
                    console.log('Erreur lors de lors de l\' inscription : ' +  textStatus);
                }
            }
        });
    });
});
