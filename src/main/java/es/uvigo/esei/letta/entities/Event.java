package es.uvigo.esei.letta.entities;

import static java.util.Objects.requireNonNull;
import java.util.Date;

public class Event implements Comparable<Event>{
	
	private int id;
	private String title;
	private String description;
	private Date event_date;
	private Date creation_date;
	private String location;
	private String category;
	private int capacity;
	private int num_participants;
	private int duration;
	
	Event () {}
	
	public Event(int id, String title, String description, Date event_date, Date creation_date, String location, String category, int capacity, int num_participants, int duration) {
		
		this.id = id;
		this.title = requireNonNull(title, "Title can't be null");
		this.description = requireNonNull(description, "Description can't be null");
		this.event_date = requireNonNull(event_date, "Event date can't be null");
		this.creation_date = requireNonNull(creation_date, "Creation date can't be null");
		this.location = requireNonNull(location, "Location can't be null");
		this.category = requireNonNull(category, "Category can't be null");
		this.capacity = requireNonNull(capacity, "Capacity can't be null");
		this.num_participants = requireNonNull(num_participants, "Number of participants can't be null");
		this.duration = requireNonNull(num_participants, "Duration can't be null");
		
	}

	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Date getEvent_date() {
		return event_date;
	}
	
	public Date getCreation_date() {
		return creation_date;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getCategory() {
		return category;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public int getNum_participants() {
		return num_participants;
	}

	@Override
	public int compareTo(Event o) {
		return o.getCreation_date().compareTo(this.getCreation_date());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Event))
			return false;
		Event other = (Event) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	public int getDuration() {
		return duration;
	}

}
