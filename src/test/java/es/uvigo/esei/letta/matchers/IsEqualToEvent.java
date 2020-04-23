package es.uvigo.esei.letta.matchers;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import es.uvigo.esei.letta.entities.Event;

public class IsEqualToEvent extends IsEqualToEntity<Event> {
	public IsEqualToEvent(Event entity) {
		super(entity);
	}

	@Override
	protected boolean matchesSafely(Event actual) {
		this.clearDescribeTo();
		
		if (actual == null) {
			this.addTemplatedDescription("actual", expected.toString());
			return false;
		} else {
			return checkAttribute("id", Event::getId, actual)
				&& checkAttribute("name", Event::getTitle, actual)
				&& checkAttribute("description", Event::getDescription, actual)
				&& checkAttribute("event_date", Event::getEvent_date, actual)
				&& checkAttribute("location", Event::getLocation, actual)
				&& checkAttribute("category", Event::getCategory, actual)
				&& checkAttribute("capacity", Event::getCapacity, actual)
				&& checkAttribute("num_participants", Event::getNum_participants, actual)
				&& checkAttribute("duration", Event::getDuration, actual);
		}
	}
	
	@Factory
	public static IsEqualToEvent equalsToEvent(Event Event) {
		return new IsEqualToEvent(Event);
	}
	
	@Factory
	public static Matcher<Iterable<? extends Event>> containsEventsInAnyOrder(Event ... Events) {
		return containsEntityInAnyOrder(IsEqualToEvent::equalsToEvent, Events);
	}

}
