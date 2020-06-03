package es.uvigo.esei.letta.entities;

import static java.util.Objects.requireNonNull;

public class Inscription {
	
	private int id;
	private String login;
	private int eventId;
	
	// Constructor needed for the JSON conversion
	Inscription() {}
	
	public Inscription(int id, String login, int eventId) {
		this.id = id;
		this.login = requireNonNull(login, "login can't be null.");
		this.eventId = requireNonNull(eventId, "eventId can't be null.");
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public int getEventId() {
		return this.eventId;
	}
	
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
}
