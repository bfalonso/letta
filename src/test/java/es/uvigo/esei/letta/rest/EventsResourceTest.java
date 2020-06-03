package es.uvigo.esei.letta.rest;

import static es.uvigo.esei.letta.dataset.EventsDataset.existentId;
import static es.uvigo.esei.letta.dataset.UsersDataset.normalLogin;
import static es.uvigo.esei.letta.dataset.UsersDataset.userToken;
import static es.uvigo.esei.letta.dataset.EventsDataset.existentEvent;
import static es.uvigo.esei.letta.dataset.EventsDataset.newTitle;
import static es.uvigo.esei.letta.dataset.EventsDataset.newDescription;
import static es.uvigo.esei.letta.dataset.EventsDataset.newEvent_date;
import static es.uvigo.esei.letta.dataset.EventsDataset.newLocation;
import static es.uvigo.esei.letta.dataset.EventsDataset.newCategory;
import static es.uvigo.esei.letta.dataset.EventsDataset.newCapacity;
import static es.uvigo.esei.letta.dataset.EventsDataset.newNum_participants;
import static es.uvigo.esei.letta.dataset.EventsDataset.nonExistentId;
import static es.uvigo.esei.letta.dataset.EventsDataset.events;
import static es.uvigo.esei.letta.dataset.EventsDataset.eventsRecent;
import static es.uvigo.esei.letta.matchers.HasHttpStatus.hasBadRequestStatus;
import static es.uvigo.esei.letta.matchers.HasHttpStatus.hasOkStatus;
import static es.uvigo.esei.letta.matchers.HasHttpStatus.hasUnauthorized;
import static es.uvigo.esei.letta.matchers.IsEqualToEvent.containsEventsInAnyOrder;
import static es.uvigo.esei.letta.matchers.IsEqualToEvent.equalsToEvent;
import static javax.ws.rs.client.Entity.entity;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

import es.uvigo.esei.letta.LettaTestApplication;
import es.uvigo.esei.letta.dataset.EventsDataset;
import es.uvigo.esei.letta.entities.Event;
import es.uvigo.esei.letta.listeners.ApplicationContextBinding;
import es.uvigo.esei.letta.listeners.ApplicationContextJndiBindingTestExecutionListener;
import es.uvigo.esei.letta.listeners.DbManagement;
import es.uvigo.esei.letta.listeners.DbManagementTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:contexts/mem-context.xml")
@TestExecutionListeners({
	DbUnitTestExecutionListener.class,
	DbManagementTestExecutionListener.class,
	ApplicationContextJndiBindingTestExecutionListener.class
})
@ApplicationContextBinding(
	jndiUrl = "java:/comp/env/jdbc/letta",
	type = DataSource.class
)
@DbManagement(
	create = "classpath:db/hsqldb.sql",
	drop = "classpath:db/hsqldb-drop.sql"
)
@DatabaseSetup("/datasets/dataset.xml")
@ExpectedDatabase("/datasets/dataset.xml")

public class EventsResourceTest extends JerseyTest{
	@Override
	protected Application configure() {
		return new LettaTestApplication();
	}

	@Override
	protected void configureClient(ClientConfig config) {
		super.configureClient(config);
		
		// Enables JSON transformation in client
		config.register(JacksonJsonProvider.class);
		config.property("com.sun.jersey.api.json.POJOMappingFeature", Boolean.TRUE);
	}
	
	@Test
	public void testList() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		int page = 0;
		boolean morePages = true;
		List<Event> events = new ArrayList<Event>();
		
		while(morePages) {
			final Response response = target("events/recent").queryParam("page", page).request()
					.header("Authorization", "Basic " + userToken(normalLogin()))
				.get();
			
			final Object[] eventsPage = response.readEntity(new GenericType<Object[]>(){});
			List<Event> arrayEvents = (List<Event>)eventsPage[0];
					
			if (arrayEvents.size() == 0) {
				morePages = false;
			}
			else {
				page++;
				for(int i = 0; i < arrayEvents.size(); i++) {
					events.add(mapper.convertValue(
							arrayEvents.get(i), 
						    Event.class
						));
				}
			}
			
		}
		
		assertThat(events, containsEventsInAnyOrder(eventsRecent()));
	}

	// Hace falta implementar los tests que soporten el sistema de login.
	// Test: Un usuario no registrado solo podrá ver cierta información [category title description capacity]
	@Test
	public void testGetValidId() throws IOException {
		final Response response = target("events/" + existentId()).request()
				.header("Authorization", "Basic " + userToken(normalLogin()))
				.get();
		
		assertThat(response, hasOkStatus());
		final Event event = response.readEntity(Event.class);
		assertThat(event, is(equalsToEvent(existentEvent())));
	}
	
	@Test
	public void testGetInvalidId() throws IOException {
		final Response response = target("events/" + nonExistentId()).request()
				.header("Authorization", "Basic " + userToken(normalLogin()))
				.get();

		assertThat(response, hasBadRequestStatus());
	}
	
	
	
	@Test
	public void testListSearchTitle() throws IOException {
		final Response response = target("events").queryParam("search", "Title6").queryParam("page", 0).request()
				.header("Authorization", "Basic " + userToken(normalLogin()))
		.get();
		assertThat(response, hasOkStatus());
		ObjectMapper mapper = new ObjectMapper();

		final Object[] objectArray = response.readEntity(new GenericType<Object[]>(){});
		final List<Event> events = (List<Event>) objectArray[0];
		final Event event = EventsDataset.event(6);
		final Event expectedEvent =  mapper.convertValue(
				events.get(0), 
			    Event.class
			); 
		assertEquals(expectedEvent, event);
	}
	
	@Test
	public void testListSearchDescription() throws IOException {
		final Response response = target("events").queryParam("search", "Description6").request()
				.header("Authorization", "Basic " + userToken(normalLogin()))
		.get();
		assertThat(response, hasOkStatus());
		
		ObjectMapper mapper = new ObjectMapper();
		final Object[] objectArray = response.readEntity(new GenericType<Object[]>(){});
		final List<Event> events = (List<Event>) objectArray[0];
		final Event expectedEvent =  mapper.convertValue(
				events.get(0), 
			    Event.class
			);
		final Event event = EventsDataset.event(6);
		assertEquals(expectedEvent, event);
	}
	
	@Test
	public void testListSearchdescriptionOrdered() throws IOException {
		final Response response = target("events").queryParam("search", "Description").queryParam("page", "0").request()
				.header("Authorization", "Basic " + userToken(normalLogin()))
		.get();
		assertThat(response, hasOkStatus());
		ObjectMapper mapper = new ObjectMapper();
		final Object[] objectArray = response.readEntity(new GenericType<Object[]>(){});
		final List<Event> events = (List<Event>) objectArray[0];
		Iterator<Event> it = events.iterator();
		final List<Event> finalEvents = new LinkedList<Event>();
		while(it.hasNext()) {
			 
			final Event parsedEvent =  mapper.convertValue(
				it.next(), 
				Event.class
			);
			finalEvents.add(parsedEvent);
		}
		
		final List<Event> eventOrdered = new LinkedList<Event>(Arrays.asList(EventsDataset.eventsSearch()));
		
		assertEquals(finalEvents, eventOrdered.subList(0, 6));
	}
	/*
	@Test 
	public void testListSearchdescription2() throws IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		
		final Response response2 = target("events").queryParam("search", "Description").queryParam("page", "1").request()
				.header("Authorization", "Basic " + userToken(normalLogin()))
		.get();
		assertThat(response2, hasOkStatus());
		final Object[] objectArray2 = response2.readEntity(new GenericType<Object[]>(){});
		final List<Event> events2 = (List<Event>) objectArray2[0];
		Iterator<Event> it2 = events2.iterator();
		final List<Event> finalEvents2 = new LinkedList<Event>();
		while(it2.hasNext()) {
			 
			final Event parsedEvent =  mapper.convertValue(
				it2.next(), 
				Event.class
			);
			finalEvents2.add(parsedEvent);
		}
		
		final List<Event> eventOrdered2 = new LinkedList<Event>(Arrays.asList(EventsDataset.eventsSearch()));
		assertEquals(finalEvents2, eventOrdered2.subList(6, 12));
	}*/

}
