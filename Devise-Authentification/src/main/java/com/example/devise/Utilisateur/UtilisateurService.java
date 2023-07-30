package com.example.devise.Utilisateur;

import com.example.devise.Config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilisateurService {
    private final  UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public List<Utilisateur> obtenirListUtilisateurs() {
        return  utilisateurRepository.findAll();
    }

    public void enregistrerNouvelUtilisateur(Utilisateur utilisateur) {
        System.out.println(utilisateur);
    }

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository
                .findByEmail(request.getEmail());
        if (utilisateurOptional.isPresent()) {
            throw new IllegalStateException("Email deja utilise par un autre utilisateur");
        }
        var user = Utilisateur.builder()
                .prenom(request.getPrenom())
                .nom(request.getNom())
                .email(request.getEmail())
                .dateNaissance(request.getDateNaissance())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .adresse(request.getAdresse())
                .dateCreation(LocalDate.now())
                .dateMAJ(LocalDate.now())
                .role(Role.USER)
                .build();
        utilisateurRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getMotDePasse()
                )
        );
        var user = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .idUser(user.getIdUtilisateur())
                .build();
    }

    public void deleteUser(Long userId) {
        boolean exists = utilisateurRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException(
              "L'utilisateur avec l'id " + userId + " n'existe pas"
            );
        }
        utilisateurRepository.deleteById(userId);
    }
    @Transactional
    public void updateUser(Long userId, String email, String password) {
         Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "L'utilisateur avec l'id " + userId + " n'existe pas"
                ));
        if(email != null && email.length() > 0 &&
           !Objects.equals(utilisateur.getEmail(), email)) {
            Optional<Utilisateur> utilisateurOptional = utilisateurRepository
                    .findByEmail(email);
            if (utilisateurOptional.isPresent()) {
                throw new IllegalStateException("Email deja utilise par un autre utilisateur");
            }
            utilisateur.setEmail(email);
        }

        if(password != null && password.length() > 0 &&
                !Objects.equals(utilisateur.getMotDePasse(), password)) {
            Optional<Utilisateur> utilisateurOptional = utilisateurRepository
                    .findByEmail(email);
            if (utilisateurOptional.isPresent()) {
                throw new IllegalStateException("Email deja utilise par un autre utilisateur");
            }
            utilisateur.setEmail(email);
        }
    }
}
