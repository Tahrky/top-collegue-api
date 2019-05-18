package dev.entities;

public class CollegueNomPrenomsPhotoUrlVote implements Comparable<CollegueNomPrenomsPhotoUrlVote> {

    String nom;
    String prenoms;
    String photoUrl;
    int vote;

    public CollegueNomPrenomsPhotoUrlVote() {}

    public CollegueNomPrenomsPhotoUrlVote(String nom, String prenoms, String photoUrl, int vote) {
	super();
	this.nom = nom;
	this.prenoms = prenoms;
	this.photoUrl = photoUrl;
	this.vote = vote;
    }

    public int getVote() {
	return vote;
    }

    public void setVote(int vote) {
	this.vote = vote;
    }

    /**
     * Getter
     * @return the nom
     */
    public String getNom() {
	return nom;
    }
    /**
     * Setter
     * @param nom the nom to set
     */
    public void setNom(String nom) {
	this.nom = nom;
    }
    /**
     * Getter
     * @return the penoms
     */
    public String getPrenoms() {
	return prenoms;
    }
    /**
     * Setter
     * @param penoms the penoms to set
     */
    public void setPrenoms(String penoms) {
	this.prenoms = penoms;
    }
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

    @Override
    public int compareTo(CollegueNomPrenomsPhotoUrlVote tri) {
	if (this.vote > tri.getVote ()) {
	    return -1;
	}
	else if (this.vote < tri.getVote ()) {
	    return 1;
	}
	else {
	    return 0;
	}
    }
}
