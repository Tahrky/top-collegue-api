package dev.entities;

import java.time.LocalDateTime;

public class NoteDTO {

    int id;
    int value;
    LocalDateTime date;
    String email;

    public NoteDTO() {
	this.date = LocalDateTime.now();
    }

    public NoteDTO(int id, int value, String email) {
	super();
	this.id = id;
	this.value = value;
	this.email = email;
	this.date = LocalDateTime.now();
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getValue() {
	return value;
    }

    public void setValue(int value) {
	this.value = value;
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

    public boolean isVote() {
	return vote;
    }

    public void setVote(boolean vote) {
	this.vote = vote;
    }
}
