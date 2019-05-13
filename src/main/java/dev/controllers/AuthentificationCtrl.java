/**
 *
 */
package dev.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import dev.entities.Collegue;
import dev.entities.CollegueEmailNomPrenomsPhotoUrlRoles;
import dev.entities.InfosAuthentification;
import dev.exception.CollegueNonTrouveException;
import dev.repository.CollegueRepository;

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

    // Permet de s'authentifier, en générant un cookie pour maintenir la session en cours
    @PostMapping(value = "/auth")
    public ResponseEntity<CollegueEmailNomPrenomsPhotoUrlRoles> authenticate(@RequestBody InfosAuthentification authenticationRequest, HttpServletResponse response) throws URISyntaxException {
	HttpEntity<InfosAuthentification> requestEntity = new HttpEntity<>(authenticationRequest);

	RestTemplate rt = new RestTemplate();
	ResponseEntity<String> responseFromApi = rt.exchange("http://localhost:8081/auth", HttpMethod.POST, requestEntity, String.class);

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
		.get(new URI("http://localhost:8081/me2"))
		.header("Cookie", responseFromApi.getHeaders().getFirst("Set-Cookie"))
		.build();

	ResponseEntity<CollegueEmailNomPrenomsPhotoUrlRoles> rep2 = rt.exchange(requestEntity2, CollegueEmailNomPrenomsPhotoUrlRoles.class);
	CollegueEmailNomPrenomsPhotoUrlRoles col = rep2.getBody();
	colRepo.save(new Collegue (col.getEmail(), col.getNom(), col.getPrenoms(), col.getPhotoUrl()));

	return ResponseEntity.ok(col);
    }

    @GetMapping (value="/collegues")
    public List <Collegue> getCollegues () {
	return colRepo.findAll();
    }

    @PatchMapping (value="/upvote")
    public void upvote (@RequestBody String email) {
	Collegue colCourant = colRepo.findById(email).orElseThrow(CollegueNonTrouveException::new);
	colCourant.setVote (colCourant.getVote ()+10);
	colRepo.save(colCourant);
    }

    @PatchMapping (value="/downvote")
    public void downvote (@RequestBody String email) {
	Collegue colCourant = colRepo.findById(email).orElseThrow(CollegueNonTrouveException::new);
	colCourant.setVote (colCourant.getVote ()-10);
	colRepo.save(colCourant);
    }



    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity mauvaiseInfosConnexion(BadCredentialsException e) {
	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
