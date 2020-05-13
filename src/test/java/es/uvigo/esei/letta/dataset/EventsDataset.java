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
				new Event(5, "Title5", "Description5", new Date(1608422400000L), new Date(1608422400000L), "Vigo", "sports", 20, 15, 5),
				new Event(6, "Title6", "Description6", new Date(1607990400000L), new Date(1607990400000L), "Salamanca", "cinema", 20, 15, 5)
		};
	}
	
	public static Event[] eventsSearch() {
		return new Event[] {
				new Event(3, "Title3", "Description3", new Date(1602331200000L), new Date(1602331200000L), "Ourense", "theater", 20, 20, 5),
				new Event(9, "Title9", "Description9", new Date(1602331200000L), new Date(1602331200000L), "Ourense", "theater", 20, 20, 5),
				new Event(13, "Title13", "Description13", new Date(1602331200000L), new Date(1602331200000L), "Ourense", "theater", 20, 20, 5),
				new Event(4, "Title4", "Description4", new Date(1607601600000L), new Date(1607601600000L), "Santander", "theater", 20, 20, 5),
				new Event(10, "Title10", "Description4", new Date(1607601600000L), new Date(1607601600000L), "Santander", "theater", 20, 20, 5),
<<<<<<< Updated upstream
				new Event(14, "Title14", "Description4", new Date(1607601600000L), new Date(1607601600000L), "Santander", "theater", 20, 20, 5)
		};
	}
	
	public static Event[] eventsRecent() {
		return new Event[] {
				new Event(5, "Title5", "Description5", new Date(1608422400000L), new Date(1608422400000L), "Vigo", "sports", 20, 15, 5),
				new Event(6, "Title6", "Description6", new Date(1607990400000L), new Date(1607990400000L), "Salamanca", "cinema", 20, 15, 5),
				new Event(7, "Title7", "Description7", new Date(1607990400000L), new Date(1607990400000L), "Salamanca", "cinema", 20, 15, 5),
				new Event(8, "Title8", "Description5", new Date(1608422400000L), new Date(1608422400000L), "Vigo", "sports", 20, 15, 5),
				new Event(11, "Title11", "Description10", new Date(1607990400000L), new Date(1607990400000L), "Salamanca", "cinema", 20, 15, 5),
				new Event(12, "Title12", "Description5", new Date(1608422400000L), new Date(1608422400000L), "Vigo", "sports", 20, 15, 5),
				new Event(15, "Title15", "Description15", new Date(1607990400000L), new Date(1607990400000L), "Salamanca", "cinema", 20, 15, 5),
				new Event(16, "Title16", "Description5", new Date(1608422400000L), new Date(1608422400000L), "Vigo", "sports", 20, 15, 5)
		};
	}
	
	public static Event[] eventsPagination() {
		return new Event[] {
				new Event(1, "Title1" ,"Description1" ,new Date(1570658400000L) ,new Date(1570658400000L) ,"Ourense" ,"cinema" ,20 ,15 ,5 ),
				new Event(2, "Title2" ,"Description2" ,new Date(1562536800000L) ,new Date(1562536800000L) ,"Pontevedra" ,"sports" ,15 ,8 ,5 ),
				new Event(3, "Title3", "Description3", new Date(1602331200000L), new Date(1602331200000L), "Ourense", "theater", 20, 20, 5),
				new Event(4, "Title4", "Description4", new Date(1607601600000L), new Date(1607601600000L), "Santander", "theater", 20, 20, 5),
				new Event(5, "Title5", "Description5", new Date(1608422400000L), new Date(1608422400000L), "Vigo", "sports", 20, 15, 5),
=======
				new Event(14, "Title14", "Description4", new Date(1607601600000L), new Date(1607601600000L), "Santander", "theater", 20, 20, 5),
>>>>>>> Stashed changes
				new Event(6, "Title6", "Description6", new Date(1607990400000L), new Date(1607990400000L), "Salamanca", "cinema", 20, 15, 5),
				new Event(7, "Title7", "Description7", new Date(1607990400000L), new Date(1607990400000L), "Salamanca", "cinema", 20, 15, 5),
				new Event(11, "Title11", "Description11", new Date(1607990400000L), new Date(1607990400000L), "Salamanca", "cinema", 20, 15, 5),
				new Event(15, "Title15", "Description15", new Date(1607990400000L), new Date(1607990400000L), "Salamanca", "cinema", 20, 15, 5),
				new Event(5, "Title5", "Description5", new Date(1608422400000L), new Date(1608422400000L), "Vigo", "sports", 20, 15, 5),
				new Event(8, "Title8", "Description5", new Date(1608422400000L), new Date(1608422400000L), "Vigo", "sports", 20, 15, 5),
				new Event(12, "Title12", "Description5", new Date(1608422400000L), new Date(1608422400000L), "Vigo", "sports", 20, 15, 5),
				new Event(16, "Title16", "Description5", new Date(1608422400000L), new Date(1608422400000L), "Vigo", "sports", 20, 15, 5)
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
		return new Event(nonExistentId(), "title", "description", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), "ourense", "cinema", 20, 15, 5);
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
	
	public static int newDuration() {
		return 5;
	}
	
	public static Event newEvent() {
		return new Event(events().length + 1, newTitle(), newDescription(), newEvent_date(), newCreation_date(), newLocation(), newCategory(), newCapacity(), newNum_participants(), newDuration());
	}
	

}
