/**
 *
 */
package dev.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import dev.entities.Collegue;
import dev.entities.CollegueEmailNomPrenomsPhotoUrlRoles;
import dev.entities.CollegueNomPrenomsPhotoUrlVote;
import dev.entities.InfosAuthentification;
import dev.entities.Vote;
import dev.entities.VoteDTO;
import dev.exception.CollegueNonTrouveException;
import dev.repository.CollegueRepository;
import dev.repository.VoteRepository;

/**
 *
 * @author BIRABEN-BIANCHI Hugo
 */
@RestController
//@CrossOrigin(origins= {"http://localhost:4200", "https://tahrky.github.io" }, allowCredentials = "true")
public class AuthentificationCtrl {

    @Value("${jwt.expires_in}")
    private Integer EXPIRES_IN;

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @Value("${jwt.secret}")
    private String SECRET;

    @Autowired
    CollegueRepository colRepo;

    @Autowired
    VoteRepository voteRepo;

    // Permet de s'authentifier, en générant un cookie pour maintenir la session en cours
    @PostMapping(value = "/auth")
    public ResponseEntity<CollegueEmailNomPrenomsPhotoUrlRoles> authenticate(@RequestBody @Valid InfosAuthentification authenticationRequest, HttpServletResponse response) throws URISyntaxException {
	HttpEntity<InfosAuthentification> requestEntity = new HttpEntity<>(authenticationRequest);
	RestTemplate rt = new RestTemplate();
	ResponseEntity<String> responseFromApi = rt.exchange("https://biraben-collegues-api.herokuapp.com/auth", HttpMethod.POST, requestEntity, String.class);

	String responseHeader = responseFromApi.getHeaders().getFirst("Set-Cookie");

	String[] cookie = responseHeader.split(";");
	String[] cookie2 = cookie[0].split ("=");
	String token = cookie2[1];

	Cookie authCookie = new Cookie(TOKEN_COOKIE, token);
	authCookie.setHttpOnly(true);
	authCookie.setMaxAge(EXPIRES_IN * 1000);
	authCookie.setPath("/");
	response.addCookie(authCookie);

	RequestEntity<?> requestEntity2 = RequestEntity
		.get(new URI("https://biraben-collegues-api.herokuapp.com/me2"))
		.header("Cookie", responseFromApi.getHeaders().getFirst("Set-Cookie"))
		.build();

	ResponseEntity<CollegueEmailNomPrenomsPhotoUrlRoles> rep2 = rt.exchange(requestEntity2, CollegueEmailNomPrenomsPhotoUrlRoles.class);
	CollegueEmailNomPrenomsPhotoUrlRoles col = rep2.getBody();

	if (authenticationRequest.getPhotoUrl () != null && authenticationRequest.getPhotoUrl ().length() > 0 ) {
	    colRepo.save(new Collegue (col.getEmail(), col.getNom(), col.getPrenoms(), authenticationRequest.getPhotoUrl (), col.getRoles()));
	}
	else {
	    colRepo.save(new Collegue (col.getEmail(), col.getNom(), col.getPrenoms(), col.getPhotoUrl(), col.getRoles()));
	}

	return ResponseEntity.ok(col);
    }

    @GetMapping (value="/collegues")
    public List <CollegueEmailNomPrenomsPhotoUrlRoles> getCollegues () {
	return colRepo.findAll().stream ().map(col -> new CollegueEmailNomPrenomsPhotoUrlRoles (col.getEmail(), col.getNom(), col.getPrenoms(), col.getPhotoUrl(), col.getRoles())).collect(Collectors.toList()) ;
    }

    @GetMapping (value="/classement")
    public List <CollegueNomPrenomsPhotoUrlVote> getClassement () {
	List <Collegue> listeCollegues = colRepo.findAll();

	List <CollegueNomPrenomsPhotoUrlVote> listeVotes = listeCollegues.stream().map(col -> new CollegueNomPrenomsPhotoUrlVote (col.getNom (), col.getPrenoms(), col.getPhotoUrl(), col.somme ())).collect (Collectors.toList ());
	Collections.sort(listeVotes);
	return listeVotes;
    }

    @PostMapping (value="/vote")
    public void vote (@RequestBody VoteDTO vote) {
	Collegue colCourant = colRepo.findById(vote.getEmail()).orElseThrow(CollegueNonTrouveException::new);
	Vote voteTemp = new Vote (0, colCourant);

	if (vote.getVote() == 1) {
	    voteTemp.setValue (10);
	    colCourant.setVote (voteTemp);
	}
	else if (vote.getVote() == 2) {
	    voteTemp.setValue (-10);
	    colCourant.setVote (voteTemp);
	}

	voteRepo.save(voteTemp);
	colRepo.save(colCourant);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity mauvaiseInfosConnexion(BadCredentialsException e) {
	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
