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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

		final Response response = target("events/recent/").request()
				.header("Authorization", "Basic " + userToken(normalLogin()))
			.get();

		final List<Event> events = response.readEntity(new GenericType<List<Event>>(){});
		
		assertThat(events, containsEventsInAnyOrder(events()));
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
		final Response response = target("events").queryParam("search", "Title6").request()
				.header("Authorization", "Basic " + userToken(normalLogin()))
		.get();
		assertThat(response, hasOkStatus());

		final List<Event> events = response.readEntity(new GenericType<List<Event>>(){});
		final Event event = EventsDataset.event(6);
		assertEquals(events.get(0), event);
	}
	
	@Test
	public void testListSearchDescription() throws IOException {
		final Response response = target("events").queryParam("search", "Description6").request()
				.header("Authorization", "Basic " + userToken(normalLogin()))
		.get();
		assertThat(response, hasOkStatus());

		final List<Event> events = response.readEntity(new GenericType<List<Event>>(){});
		final Event event = EventsDataset.event(6);
		assertEquals(events.get(0), event);
	}
	
	@Test
	public void testListSearchdescriptionOrdered() throws IOException {
		final Response response = target("events").queryParam("search", "Description").request()
				.header("Authorization", "Basic " + userToken(normalLogin()))
		.get();
		assertThat(response, hasOkStatus());

		final List<Event> events = response.readEntity(new GenericType<List<Event>>(){});
		final List<Event> eventOrdered = new LinkedList<Event>(Arrays.asList(EventsDataset.eventsSearch()));
		
		assertEquals(events, eventOrdered);
	}
	
	
}
