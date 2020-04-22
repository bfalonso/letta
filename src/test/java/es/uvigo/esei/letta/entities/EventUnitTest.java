package es.uvigo.esei.letta.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.Date;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class EventUnitTest {
	@Test
	public void testEvent() {
		final int id = 1;
		final String title = "Evento De Test";
		final String description = "Este evento consiste en una prueba de unidad de la entidad Event.";
		final Date event_date = new Date(System.currentTimeMillis());
		final Date creation_date = new Date(System.currentTimeMillis());
		final String location = "Ourense";
		final String category = "cinema";
		final int capacity = 20;
		final int num_participants = 15;
		final int duration = 15;
		
		final Event event = new Event(id, title, description, event_date, creation_date, location, category, capacity, num_participants, duration);
		
		assertThat(event.getId(), is(equalTo(id)));
		assertThat(event.getTitle(), is(equalTo(title)));
		assertThat(event.getDescription(), is(equalTo(description)));
		assertThat(event.getEvent_date(), is(equalTo(event_date)));
		assertThat(event.getLocation(), is(equalTo(location)));
		assertThat(event.getCategory(), is(equalTo(category)));
		assertThat(event.getCapacity(), is(equalTo(capacity)));
		assertThat(event.getNum_participants(), is(equalTo(num_participants)));
	}
	
	@Test(expected = NullPointerException.class)
	public void testEventNullTitle() {
		new Event(1, null, "Este evento consiste en una prueba de unidad de la entidad Event.", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "Ourense", "cinema", 20, 15, 5);
	}
	
	@Test(expected = NullPointerException.class)
	public void testEventNullDescription() {
		new Event(1, "Evento De Test", null, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "Ourense", "cinema", 20, 15, 5);
	}
	
	@Test(expected = NullPointerException.class)
	public void testEventNullEventDate() {
		new Event(1, "Evento De Test", "Este evento consiste en una prueba de unidad de la entidad Event.", null, new Date(System.currentTimeMillis()), "Ourense", "cinema", 20, 15, 5);
	}
	
	@Test(expected = NullPointerException.class)
	public void testEventNullCreationDate() {
		new Event(1, "Evento De Test", "Este evento consiste en una prueba de unidad de la entidad Event.", new Date(System.currentTimeMillis()),null,  "Ourense", "cinema", 20, 15, 5);
	}
	
	@Test(expected = NullPointerException.class)
	public void testEventNullLocation() {
		new Event(1, "Evento De Test", "Este evento consiste en una prueba de unidad de la entidad Event.", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), null, "cinema", 20, 15, 5);
	}
	
	@Test(expected = NullPointerException.class)
	public void testEventNullCategory() {
		new Event(1, "Evento de Test", "Este evento consiste en una prueba de unidad de la entidad Event.", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "Ourense", null, 20, 15, 5);
	}
	
	@Test
	public void testEqualsObject() {
		long date = System.currentTimeMillis();
		final Event eventA = new Event(1, "Title", "Description", new Date(date), new Date(date), "Ourense", "cinema", 20, 15, 5);
		final Event eventB = new Event(1, "Title", "Description", new Date(date), new Date(date), "Ourense", "cinema", 20, 15, 5);
		
		assertTrue(eventA.equals(eventB));
	}
	
	@Test
	public void testEqualsHashcode() {
		EqualsVerifier.forClass(Event.class)
			.withIgnoredFields("title", "description", "event_date", "creation_date", "location", "category", "capacity", "num_participants", "duration")
			.suppress(Warning.STRICT_INHERITANCE)
			.suppress(Warning.NONFINAL_FIELDS)
		.verify();
	}
}
