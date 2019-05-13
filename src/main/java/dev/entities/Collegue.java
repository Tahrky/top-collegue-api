/**
 *
 */
package dev.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author BIRABEN-BIANCHI Hugo
 */
@Entity
public class Collegue {
    @Id
    String email;
    @Column
    String nom;
    @Column
    String prenoms;
    @Column
    String photoUrl;

    public Collegue() {
    }

    public Collegue(String nom, String prenoms, String email, String photoUrl) {
	super();
	this.nom = nom.toLowerCase();
	this.prenoms = prenoms;
	this.email = email;
	this.photoUrl = photoUrl;
    }

    /**
     * Getter
     *
     * @return the nom
     */
    public String getNom() {
	return nom;
    }

    /**
     * Setter
     *
     * @param nom the nom to set
     */
    public void setNom(String nom) {
	this.nom = nom.toLowerCase();
    }

    /**
     * Getter
     *
     * @return the prenoms
     */
    public String getPrenoms() {
	return prenoms;
    }

    /**
     * Setter
     *
     * @param prenoms the prenoms to set
     */
    public void setPrenoms(String prenoms) {
	this.prenoms = prenoms;
    }

    /**
     * Getter
     *
     * @return the email
     */
    public String getEmail() {
	return email;
    }

    /**
     * Setter
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * Getter
     *
     * @return the photoUrl
     */
    public String getPhotoUrl() {
	return photoUrl;
    }

    /**
     * Setter
     *
     * @param photoUrl the photoUrl to set
     */
    public void setPhotoUrl(String photoUrl) {
	this.photoUrl = photoUrl;
    }

}
