/**
 *
 */
package dev.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import dev.entities.CollegueEmailNomPrenomsPhotoUrlRoles;
import dev.entities.InfosAuthentification;

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

    // Permet de s'authentifier, en générant un cookie pour maintenir la session en cours
    @PostMapping(value = "/auth")
    public ResponseEntity<CollegueEmailNomPrenomsPhotoUrlRoles> authenticate(@RequestBody InfosAuthentification authenticationRequest, HttpServletResponse response) throws URISyntaxException {
    	HttpEntity<InfosAuthentification> requestEntity = new HttpEntity<InfosAuthentification>(authenticationRequest);

        RestTemplate rt = new RestTemplate();
    	ResponseEntity<String> responseFromApi = rt.exchange("https://biraben-collegues-api.herokuapp.com/auth", HttpMethod.POST, requestEntity, String.class);

    	String responseHeader = responseFromApi.getHeaders().getFirst("Set-Cookie");
    	String body = responseFromApi.getBody();

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
		return ResponseEntity.ok(rep2.getBody());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity mauvaiseInfosConnexion(BadCredentialsException e) {
	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
