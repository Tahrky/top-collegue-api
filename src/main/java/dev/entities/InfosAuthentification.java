/**
 *
 */
package dev.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author BIRABEN-BIANCHI Hugo
 */
public class InfosAuthentification {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String motDePasse;

    private String photoUrl;

    /**
     * Getter
     * @return the photoUrl
     */
    public String getPhotoUrl() {
	return photoUrl;
    }

    /**
     * Setter
     * @param photoUrl the photoUrl to set
     */
    public void setPhotoUrl(String photoUrl) {
	this.photoUrl = photoUrl;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String nomUtilisateur) {
	this.email = nomUtilisateur;
    }

    public String getMotDePasse() {
	return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
	this.motDePasse = motDePasse;
    }
}
