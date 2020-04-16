package es.uvigo.esei.letta.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.uvigo.esei.letta.dao.DAOException;
import es.uvigo.esei.letta.dao.EventsDAO;
import es.uvigo.esei.letta.entities.Event;

@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
public class EventsResource {
	private final static Logger LOG = Logger.getLogger(EventsResource.class.getName());
	
	private final EventsDAO dao;
	
	public EventsResource() {
		this(new EventsDAO());
	}
	
	EventsResource(EventsDAO dao){
		this.dao = dao;
	}
	
	@GET
	public Response listRecent() {
		try {
			return Response.ok(this.dao.listRecent()).build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error listing events", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
}
