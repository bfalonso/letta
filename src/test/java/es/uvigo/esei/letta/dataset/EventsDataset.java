package es.uvigo.esei.letta.dataset;

import static java.util.Arrays.binarySearch;
import static java.util.Arrays.stream;

import java.sql.Date;
import java.util.Arrays;
import java.util.function.Predicate;

import es.uvigo.esei.letta.entities.Event;

public class EventsDataset {
	private EventsDataset() {}
	
	public static Event[] events() {
		return new Event[] {
				new Event(5, "Title5", "Description5", new Date(1608422400000L), new Date(1608422400000L), "Vigo", "sports", 20, 15),
				new Event(6, "Title6", "Description6", new Date(1607990400000L), new Date(1607990400000L), "Salamanca", "cinema", 20, 15)
		};
	}
	
	public static Event[] eventsWithout(int ... ids) {
		Arrays.sort(ids);
		
		final Predicate<Event> hasValidId = event ->
			binarySearch(ids, event.getId()) < 0;
		
		return stream(events())
			.filter(hasValidId)
		.toArray(Event[]::new);
	}
	
	public static Event event(int id) {
		return stream(events())
			.filter(event -> event.getId() == id)
			.findAny()
		.orElseThrow(IllegalArgumentException::new);
	}
	
	public static int existentId() {
		return 5;
	}
	
	public static int nonExistentId() {
		return 1234;
	}

	public static Event existentEvent() {
		return event(existentId());
	}
	
	public static Event nonExistentEvent() {
		return new Event(nonExistentId(), "title", "description", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "ourense", "cinema", 20, 15);
	}
	
	public static String newTitle() {
		return "NewTitle";
	}
	
	public static String newDescription() {
		return "NewDescription";
	}
	
	public static Date newEvent_date() {
		return new Date(System.currentTimeMillis());
	}
	
	public static Date newCreation_date() {
		return new Date(System.currentTimeMillis());
	}
	
	public static String newLocation() {
		return "NewTitle";
	}
	
	public static String newCategory() {
		return "NewCategory";
	}
	
	public static int newCapacity() {
		return 10;
	}
	
	public static int newNum_participants() {
		return 4;
	}
	
	public static Event newEvent() {
		return new Event(events().length + 1, newTitle(), newDescription(), newEvent_date(), newCreation_date(), newLocation(), newCategory(), newCapacity(), newNum_participants());
	}
}
