package com.example.devise.Utilisateur;
import com.example.devise.Config.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UtilisateurServiceTest {
    @InjectMocks
    private UtilisateurService utilisateurService;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    private RegisterRequest request;
    private Utilisateur utilisateur;
    private final Long userId = 1L;

    @BeforeEach
    public void setUp() {
        request = RegisterRequest.builder()
                .prenom("John")
                .nom("James")
                .dateNaissance(LocalDate.now())
                .adresse("Rue Galt Ouest")
                .motDePasse("12345678")
                .email("ysaleck600@gmail.com")
                .role(Role.USER)
                .build();
        utilisateur = Utilisateur.builder()
                .prenom("John")
                .nom("James")
                .dateNaissance(LocalDate.now())
                .adresse("Rue Galt Ouest")
                .motDePasse("12345678")
                .email("ysaleck600@gmail.com")
                .role(Role.USER)
                .build();
    }

//    @Test
//    public void testRegister() {
//        // Créer une instance de RegisterRequest pour le test
//        RegisterRequest request = new RegisterRequest();
//        request.setPrenom("John");
//        request.setNom("Doe");
//        request.setEmail("john.doe@example.com");
//        // ... complétez avec les autres champs
//
//        // Définir le comportement simulé du repository
//        when(utilisateurRepository.findByEmail(eq(request.getEmail()))).thenReturn(Optional.empty());
//
//        // Définir le comportement simulé du service JWT
//        when(jwtService.generateToken(any())).thenReturn("mocked-jwt-token");
//
//        // Appeler la méthode à tester
//        AuthenticationResponse response = utilisateurService.register(request);
//
//        // Vérifier les appels de méthode et les résultats
//        verify(utilisateurRepository, times(1)).save(any());
//        verify(jwtService, times(1)).generateToken(any());
//
//        // Vérifier que le token retourné est correct
//        assertNotNull(response);
//        assertEquals("mocked-jwt-token", response.getToken());
//    }


    @Test
    public void testRegister() {
        // Mock the behavior of dependencies

        when(utilisateurRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(utilisateurRepository.save(any())).thenReturn(utilisateur);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("mocked-token");

        // Call the method
        AuthenticationResponse response = utilisateurService.register(request);

        // Vérifier les appels de méthode et les résultats
        verify(utilisateurRepository, times(1)).save(any());
        verify(jwtService, times(1)).generateToken(any());

        // Assertions
        assertEquals("mocked-token", response.getToken());
    }

    @Test
    public void testAuthenticate() {
        // Mock the behavior of dependencies
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("ysaleck600@gmail.com")
                .motDePasse("12345678")
                .build();
        //Utilisateur utilisateur = new Utilisateur(/* initialize user */);
        when(utilisateurRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(utilisateur));
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtService.generateToken(utilisateur)).thenReturn("mocked-token");

        // Call the method
        AuthenticationResponse response = utilisateurService.authenticate(request);

        // Vérifier les appels de méthode et les résultats
        verify(utilisateurRepository, times(1)).findByEmail(any());
        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtService, times(1)).generateToken(any());

        // Assertions
        assertEquals("mocked-token", response.getToken());
    }

    @Test
    public void testDeleteUser() {
        // Mock the behavior of dependencies

        when(utilisateurRepository.existsById(userId)).thenReturn(true);

        // Call the method
        utilisateurService.deleteUser(userId);

        // Verify the interaction
        verify(utilisateurRepository).deleteById(userId);
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        String newEmail = "new.email@example.com";
        String newPassword = "newPassword";

//        Utilisateur existingUser = new Utilisateur();
//        existingUser.setIdUtilisateur(userId);
//        existingUser.setEmail("old.email@example.com");

        when(utilisateurRepository.findById(userId)).thenReturn(Optional.of(utilisateur));
        when(utilisateurRepository.findByEmail(newEmail)).thenReturn(Optional.empty());
//        when(utilisateurRepository.findByEmail(utilisateur.getEmail())).thenReturn(Optional.of(utilisateur));
        //when(passwordEncoder.encode(newPassword)).thenReturn("encodedPassword");

        utilisateurService.updateUser(userId, newEmail, newPassword);

        verify(utilisateurRepository).findById(userId);
        verify(utilisateurRepository, times(2)).findByEmail(any());
        //verify(passwordEncoder, times(1)).encode(any());
        //verify(utilisateurRepository).findByEmail(utilisateur.getEmail());
        //verify(utilisateurRepository).save(utilisateur);
        assertEquals(newEmail, utilisateur.getEmail());
        assertEquals(newPassword, utilisateur.getMotDePasse());
    }

    @Test
    public void testObtenirUtilisateur() {
        Long userId = 1L;
        Utilisateur user = new Utilisateur();
        user.setIdUtilisateur(userId);

        when(utilisateurRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<Utilisateur> result = utilisateurService.obtenirUtilisateur(userId);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getIdUtilisateur());

        verify(utilisateurRepository, times(1)).findById(userId);
    }

    @Test
    public void testObtenirUtilisateur_UserNotFound() {
        Long userId = 1L;

        when(utilisateurRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<Utilisateur> result = utilisateurService.obtenirUtilisateur(userId);

        assertFalse(result.isPresent());

        verify(utilisateurRepository, times(1)).findById(userId);
    }

    // Similar tests can be written for other methods
}
