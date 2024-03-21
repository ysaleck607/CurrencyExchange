$(document).ready(function() {
    $('form').on('submit', function(event) {
        event.preventDefault(); // Empêche le comportement par défaut du formulaire

        var email = $('#email').val();
        var password = $('#password').val();

        $.ajax({
            url: 'http://localhost:8099/api/v1/utilisateurs/authenticate',
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify({ email: email, motDePasse: password }),
            contentType: 'application/json',
            success: function(response) {
                // Ici, nous supposons que la réponse est un objet qui contient un champ "userId"
                var userId = response.idUser;
                var userToken = response.token;
                console.log('L\'utilisateur est connecté avec l\'ID :', userId);
                // Stocker l'ID de l'utilisateur dans le stockage local pour une utilisation ultérieure
                localStorage.setItem('userId', userId);
                console.log(userId);
                localStorage.setItem('userToken', userToken);
                
                window.location.href = 'TableauBord.html';
            },
            error: function(jqXHR, textStatus, errorThrown) {
                // Authentification échouée
                console.log("Erreur lors de la connexion : " + textStatus);
            }
        });
    });
});

