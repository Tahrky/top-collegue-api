package dev.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column
    int value;

    @Column
    LocalDateTime date;

    @ManyToOne
    Collegue collegue;

    public Note() {
	this.date = LocalDateTime.now();
    }

    public Note(int id, int value, Collegue collegue) {
	super();
	this.id = id;
	this.value = value;
	this.date = LocalDateTime.now();
	this.collegue = collegue;
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

    public Collegue getCollegue() {
	return collegue;
    }

    public void setCollegue(Collegue collegue) {
	this.collegue = collegue;
    }
}
