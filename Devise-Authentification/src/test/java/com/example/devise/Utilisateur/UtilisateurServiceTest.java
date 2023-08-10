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

    @BeforeEach
    public void setUp() {

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
        RegisterRequest request = RegisterRequest.builder()
                .prenom("John")
                .nom("James")
                .dateNaissance(LocalDate.now())
                .adresse("Rue Galt Ouest")
                .motDePasse("12345678")
                .email("ysaleck600@gmail.com")
                .role(Role.USER)
                .build();
        Utilisateur utilisateur = Utilisateur.builder()
                .prenom("John")
                .nom("James")
                .dateNaissance(LocalDate.now())
                .adresse("Rue Galt Ouest")
                .motDePasse("12345678")
                .email("ysaleck600@gmail.com")
                .role(Role.USER)
                .build();
        when(utilisateurRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(utilisateurRepository.save(any())).thenReturn(utilisateur);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("mocked-token");

        // Call the method
        AuthenticationResponse response = utilisateurService.register(request);

        // Assertions
        assertEquals("mocked-token", response.getToken());
    }

    @Test
    public void testAuthenticate() {
        // Mock the behavior of dependencies
        AuthenticationRequest request = new AuthenticationRequest(/* initialize request parameters */);
        Utilisateur utilisateur = new Utilisateur(/* initialize user */);
        when(utilisateurRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(utilisateur));
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtService.generateToken(utilisateur)).thenReturn("mocked-token");

        // Call the method
        AuthenticationResponse response = utilisateurService.authenticate(request);

        // Assertions
        assertEquals("mocked-token", response.getToken());
    }

    @Test
    public void testDeleteUser() {
        // Mock the behavior of dependencies
        Long userId = 1L;
        when(utilisateurRepository.existsById(userId)).thenReturn(true);

        // Call the method
        utilisateurService.deleteUser(userId);

        // Verify the interaction
        verify(utilisateurRepository).deleteById(userId);
    }

    // Similar tests can be written for other methods
}
