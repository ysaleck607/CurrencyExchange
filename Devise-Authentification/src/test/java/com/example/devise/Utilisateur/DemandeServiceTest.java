package com.example.devise.Utilisateur;

import com.example.devise.Config.EmailService;
import com.example.devise.Demande.*;
import com.example.devise.Offre.Offre;
import com.example.devise.Offre.OffreRepository;
import com.example.devise.Offre.StatutOffre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DemandeServiceTest {
    @InjectMocks
    private DemandeService demandeService;
    @Mock
    private DemandeRepository demandeRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private OffreRepository offreRepository;

    @Mock
    private EmailService emailService;
    private Utilisateur utilisateur;

    @BeforeEach
    public void setUp() {
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

    @Test
    public void testAddDemand() {
        AddDemandRequest request = new AddDemandRequest();
        request.setIdDemandeur(1L);
        request.setDeviseVoulue("EUR");
        request.setMontantVoulu(BigDecimal.valueOf(100));
        request.setDeviseOfferte("USD");

        demandeService.addDemand(request);

        // You can add assertions or verify interactions if needed
        verify(demandeRepository, times(1)).save(any(Demande.class));
    }

    @Test
    public void testDeleteDemand() {
        Long demandId = 1L;

        when(demandeRepository.existsById(demandId)).thenReturn(true);

        assertDoesNotThrow(() -> demandeService.deleteDemand(demandId));

        verify(demandeRepository, times(1)).deleteById(demandId);
    }

    @Test
    public void testGetDemandUtilisateurEnCours() {
        Long idUser = 1L;
        List<String> statuts = Arrays.asList(StatutDemand.ENATTENTE.name(), StatutDemand.ACCEPTER.name(), StatutDemand.ENCOURS.name(),
                StatutDemand.PAYER.name());

        when(demandeRepository.findByIdDemandeurAndStatutIn(idUser, statuts)).thenReturn(Arrays.asList(
                Optional.of(new Demande()),
                Optional.of(new Demande())
        ));

        List<Optional<Demande>> result = demandeService.getDemandUtilisateurEnCours(idUser);

        assertEquals(2, result.size());
        assertNotNull(result.get(0).orElse(null));
        assertNotNull(result.get(1).orElse(null));

        verify(demandeRepository, times(1)).findByIdDemandeurAndStatutIn(idUser, statuts);
    }

    @Test
    public void testGetDemandUtilisateurTerminer() {
        Long idUser = 1L;
        List<String> statuts = Arrays.asList(StatutDemand.ANNULER.name(), StatutDemand.TERMINER.name());

        when(demandeRepository.findByIdDemandeurAndStatutIn(idUser, statuts)).thenReturn(Arrays.asList(
                Optional.of(new Demande()),
                Optional.of(new Demande())
        ));

        List<Optional<Demande>> result = demandeService.getDemandUtilisateurTerminer(idUser);

        assertEquals(2, result.size());
        assertNotNull(result.get(0).orElse(null));
        assertNotNull(result.get(1).orElse(null));

        verify(demandeRepository, times(1)).findByIdDemandeurAndStatutIn(idUser, statuts);
    }

    /*@Test
    public void testPayerDemandeWithAcceptedOffre() {
        Long idDemande = 1L;
        Long idOffreur = 2L;

        Demande demande = new Demande();
        demande.setStatut(StatutDemand.ACCEPTER.name());

        Offre offre = new Offre();
        offre.setIdOffreur(idOffreur);
        offre.setStatutOffre(StatutOffre.ACCEPTER.name());

        when(demandeRepository.findById(idDemande)).thenReturn(Optional.of(demande));
        when(offreRepository.findByIdDemandeAndStatutOffre(idDemande, StatutOffre.ACCEPTER.name())).thenReturn(Arrays.asList(offre));
        when(utilisateurRepository.findById(idOffreur)).thenReturn(Optional.of(utilisateur));

        demandeService.payerDeamnde(idDemande);

        assertEquals(StatutDemand.PAYER.name(), demande.getStatut());
        assertEquals(StatutOffre.ACCEPTER.name(), offre.getStatutOffre());

        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
        verify(demandeRepository, times(1)).findById(idDemande);
        verify(offreRepository, times(1)).findByIdDemandeAndStatutOffre(idDemande, StatutOffre.ACCEPTER.name());
        verify(utilisateurRepository, times(1)).findById(idOffreur);
    }*/

    /*@Test
    public void testPayerDemandeWithNoAcceptedOffre() {
        Long idDemande = 1L;

        Demande demande = new Demande();
        demande.setStatut(StatutDemand.ENCOURS.name());

        when(demandeRepository.findById(idDemande)).thenReturn(Optional.of(demande));
        when(offreRepository.findByIdDemandeAndStatutOffre(idDemande, StatutOffre.ACCEPTER.name())).thenReturn(new ArrayList<>());

        assertThrows(RuntimeException.class, () -> demandeService.payerDeamnde(idDemande));

        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
        verify(demandeRepository, times(1)).findById(idDemande);
        //verify(offreRepository, times(1)).findByIdDemandeAndStatutOffre(idDemande, StatutOffre.ACCEPTER.name());
        verify(utilisateurRepository, never()).findById(anyLong());
    }*/

   /* @Test
    public void testPayerDemandeWithInvalidDemandeStatut() {
        Long idDemande = 1L;

        Demande demande = new Demande();
        demande.setStatut(StatutDemand.PAYER.name());

        when(demandeRepository.findById(idDemande)).thenReturn(Optional.of(demande));

        assertThrows(RuntimeException.class, () -> demandeService.payerDeamnde(idDemande));

        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
        verify(demandeRepository, times(1)).findById(idDemande);
        verify(offreRepository, times(1)).findByIdDemandeAndStatutOffre(anyLong(), anyString());
        verify(utilisateurRepository, never()).findById(anyLong());
    }*/

    @Test
    public void testAnnulerDemandeWithNoAcceptedOffre() {
        Long idDemande = 1L;

        Demande demande = new Demande();
        demande.setStatut(StatutDemand.ENATTENTE.name());

        when(demandeRepository.findById(idDemande)).thenReturn(Optional.of(demande));
        when(offreRepository.findByIdDemande(idDemande)).thenReturn(new ArrayList<>());

        demandeService.annulerDeamnde(idDemande);

        assertEquals(StatutDemand.ANNULER.name(), demande.getStatut());

        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
        verify(demandeRepository, times(1)).findById(idDemande);
        verify(offreRepository, times(1)).findByIdDemande(idDemande);
        verify(utilisateurRepository, never()).findById(anyLong());
    }

    /*@Test
    public void testAnnulerDemandeWithAcceptedOffre() {
        Long idDemande = 1L;

        Demande demande = new Demande();
        demande.setStatut(StatutDemand.ENATTENTE.name());

        Offre offre = new Offre();
        offre.setStatutOffre(StatutOffre.ACCEPTER.name());

        when(demandeRepository.findById(idDemande)).thenReturn(Optional.of(demande));
        when(offreRepository.findByIdDemande(idDemande)).thenReturn(new ArrayList<Object[]>());

        assertThrows(RuntimeException.class, () -> demandeService.annulerDeamnde(idDemande));

        assertEquals(StatutDemand.ENATTENTE.name(), demande.getStatut());

        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
        verify(demandeRepository, times(1)).findById(idDemande);
        verify(offreRepository, times(1)).findByIdDemande(idDemande);
        verify(utilisateurRepository, never()).findById(anyLong());
    }*/

    @Test
    public void testAccepterOffreWithValidData() {
        Long offreId = 1L;
        Long idDemande = 1L;

        Demande demande = new Demande();
        demande.setStatut(StatutDemand.ENCOURS.name());

        Offre offre = new Offre();
        offre.setStatutOffre(StatutOffre.ENATTENTE.name());
        offre.setIdOffreur(2L);

        Utilisateur offreur = new Utilisateur();
        offreur.setIdUtilisateur(2L);
        offreur.setEmail("offreur@example.com");

        when(offreRepository.findById(offreId)).thenReturn(java.util.Optional.of(offre));
        when(demandeRepository.findById(offre.getIdDemande())).thenReturn(java.util.Optional.of(demande));
        when(utilisateurRepository.findById(offre.getIdOffreur())).thenReturn(java.util.Optional.of(offreur));

        demandeService.accepterOffre(offreId);

        assertEquals(StatutOffre.ACCEPTER.name(), offre.getStatutOffre());
        assertEquals(StatutDemand.ACCEPTER.name(), demande.getStatut());

        verify(emailService, times(1)).sendEmail(eq("offreur@example.com"), anyString(), anyString());
        verify(offreRepository, times(1)).findById(offreId);
        verify(demandeRepository, times(1)).findById(offre.getIdDemande());
        verify(utilisateurRepository, times(1)).findById(offre.getIdOffreur());
    }

    @Test
    public void testAccepterOffreWithInvalidDemandStatut() {
        Long offreId = 1L;

        Demande demande = new Demande();
        demande.setStatut(StatutDemand.PAYER.name());

        Offre offre = new Offre();
        offre.setStatutOffre(StatutOffre.ENATTENTE.name());

        when(offreRepository.findById(offreId)).thenReturn(java.util.Optional.of(offre));
        when(demandeRepository.findById(offre.getIdDemande())).thenReturn(java.util.Optional.of(demande));

        assertThrows(RuntimeException.class, () -> demandeService.accepterOffre(offreId));

        assertEquals(StatutOffre.ENATTENTE.name(), offre.getStatutOffre());
        assertEquals(StatutDemand.PAYER.name(), demande.getStatut());

        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
        verify(offreRepository, times(1)).findById(offreId);
        verify(demandeRepository, times(1)).findById(offre.getIdDemande());
        verify(utilisateurRepository, never()).findById(anyLong());
    }

    @Test
    public void testRefuserOffreWithValidData() {
        Long offreId = 1L;

        Offre offre = new Offre();
        offre.setStatutOffre(StatutOffre.ENATTENTE.name());
        offre.setIdOffreur(2L);
        offre.setIdDemande(1L);

        Utilisateur offreur = new Utilisateur();
        offreur.setIdUtilisateur(2L);
        offreur.setEmail("offreur@example.com");

        when(offreRepository.findById(offreId)).thenReturn(java.util.Optional.of(offre));
        when(demandeRepository.findById(offreId)).thenReturn(java.util.Optional.of(new Demande()));
        when(utilisateurRepository.findById(offre.getIdOffreur())).thenReturn(java.util.Optional.of(offreur));

        demandeService.refuserOffre(offreId);

        assertEquals(StatutOffre.REFUSER.name(), offre.getStatutOffre());

        verify(emailService, times(1)).sendEmail(eq("offreur@example.com"), anyString(), anyString());
        verify(offreRepository, times(1)).findById(offreId);
        verify(utilisateurRepository, times(1)).findById(offre.getIdOffreur());
    }

    @Test
    public void testRefuserOffreWithInvalidStatut() {
        Long offreId = 1L;

        Offre offre = new Offre();
        offre.setStatutOffre(StatutOffre.ACCEPTER.name());

        when(offreRepository.findById(offreId)).thenReturn(java.util.Optional.of(offre));

        assertThrows(RuntimeException.class, () -> demandeService.refuserOffre(offreId));

        assertEquals(StatutOffre.ACCEPTER.name(), offre.getStatutOffre());

        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
        verify(offreRepository, times(1)).findById(offreId);
        verify(utilisateurRepository, never()).findById(anyLong());
    }

    @Test
    public void testGetDemandAutresUtilisateurs() {
        Long idUser = 1L;

        Object[] demandeInfo = new Object[3];
        Demande demande = new Demande();
        demande.setIdDemande(1L);
        demande.setIdDemandeur(2L);
        demande.setDeviseVoulu("USD");
        demande.setMontantVoulu(BigDecimal.valueOf(100));
        demande.setDeviseOfferte("EUR");
        demande.setDateDemande(LocalDateTime.now());
        demande.setStatut(StatutDemand.ENATTENTE.name());

        demandeInfo[0] = demande;
        demandeInfo[1] = "John";
        demandeInfo[2] = "Doe";

        List<Object[]> demandes = new ArrayList<>();
        demandes.add(demandeInfo);

        when(demandeRepository.getDemandsWithDemandeurNamesExceptUser(idUser)).thenReturn(demandes);

        List<DemandeResponse> result = demandeService.getDemandAutresUtilisateurs(idUser);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getIdDemande());
        assertEquals(2L, result.get(0).getIdDemandeur());
        assertEquals("John Doe", result.get(0).getNomPrenomDemandeur());
        assertEquals("USD", result.get(0).getDeviseVoulu());
        assertEquals(BigDecimal.valueOf(100), result.get(0).getMontantVoulu());
        assertEquals("EUR", result.get(0).getDeviseOfferte());
        assertEquals(StatutDemand.ENATTENTE.name(), result.get(0).getStatut());

        verify(demandeRepository, times(1)).getDemandsWithDemandeurNamesExceptUser(idUser);
    }

    @Test
    public void testGetDemandesUtilisateur() {
        Long idUser = 1L;

        Object[] demandeInfo = new Object[3];
        Demande demande = new Demande();
        demande.setIdDemande(1L);
        demande.setIdDemandeur(1L);
        demande.setDeviseVoulu("USD");
        demande.setMontantVoulu(BigDecimal.valueOf(100));
        demande.setDeviseOfferte("EUR");
        demande.setDateDemande(LocalDateTime.now());
        demande.setStatut(StatutDemand.ENATTENTE.name());

        demandeInfo[0] = demande;
        demandeInfo[1] = "Jane";
        demandeInfo[2] = "Smith";

        List<Object[]> demandes = new ArrayList<>();
        demandes.add(demandeInfo);

        when(demandeRepository.getDemandesWithNamesByUserId(idUser)).thenReturn(demandes);

        List<DemandeResponse> result = demandeService.getDemandesUtilisateur(idUser);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getIdDemande());
        assertEquals(1L, result.get(0).getIdDemandeur());
        assertEquals("Jane Smith", result.get(0).getNomPrenomDemandeur());
        assertEquals("USD", result.get(0).getDeviseVoulu());
        assertEquals(BigDecimal.valueOf(100), result.get(0).getMontantVoulu());
        assertEquals("EUR", result.get(0).getDeviseOfferte());
        assertEquals(StatutDemand.ENATTENTE.name(), result.get(0).getStatut());

        verify(demandeRepository, times(1)).getDemandesWithNamesByUserId(idUser);
    }
}
