package dev.entities;

import java.time.LocalDateTime;

public class NoteDTO {

    int id;
    int vote;
    LocalDateTime date;
    String email;

    public NoteDTO() {
	this.date = LocalDateTime.now();
    }

    public NoteDTO(int id, String email) {
	super();
	this.id = id;
	this.email = email;
	this.date = LocalDateTime.now();
    }

    public NoteDTO(int id, int vote, LocalDateTime date, String email) {
	super();
	this.id = id;
	this.vote = vote;
	this.date = date;
	this.email = email;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getVote() {
	return vote;
    }

    public void setVote(int vote) {
	this.vote = vote;
    }

    public LocalDateTime getDate() {
	return date;
    }

    public void setDate(LocalDateTime date) {
	this.date = date;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }
}
