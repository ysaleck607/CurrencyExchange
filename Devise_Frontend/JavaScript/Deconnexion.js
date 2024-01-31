$(document).ready(function() {
    // Ce code ne sera exécuté qu'après le chargement complet du DOM

    $('#logout-btn').click(function() {
        // Supprimez le token d'authentification du stockage local
        localStorage.removeItem('userToken');
        localStorage.removeItem('userId');

        // Redirigez l'utilisateur vers la page de connexion
        window.location.href = 'Connexion.html';
    });
});
