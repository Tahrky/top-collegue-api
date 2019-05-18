/**
 *
 */
package dev.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    @ElementCollection
    List <String> roles;

    @OneToMany(mappedBy = "collegue")
    private List<Vote> voteTab;

    public void setVote(Vote vote) {
	this.voteTab.add (vote);
    }

    public Collegue() {
    }

    public Collegue(String email, String nom, String prenoms, String photoUrl) {
	super();
	this.nom = nom.toLowerCase();
	this.prenoms = prenoms;
	this.email = email;
	this.photoUrl = photoUrl;
    }

    public Collegue(String email, String nom, String prenoms, String photoUrl, List <String> roles) {
	super();
	this.nom = nom.toLowerCase();
	this.prenoms = prenoms;
	this.email = email;
	this.photoUrl = photoUrl;
	this.roles = roles;
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

    public List<Vote> getVoteTab() {
	return voteTab;
    }

    public List<String> getRoles() {
	return roles;
    }

    public void setRoles(List<String> roles) {
	this.roles = roles;
    }

    public void setVoteTab(List<Vote> voteTab) {
	this.voteTab = voteTab;
    }
}
