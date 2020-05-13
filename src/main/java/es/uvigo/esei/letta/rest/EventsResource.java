package es.uvigo.esei.letta.rest;

import java.util.LinkedList;
import java.util.List;
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
import javax.ws.rs.QueryParam;
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
	
	
	/**
	 * Returns an event with the provided identifier.
	 * 
	 * @param id the identifier of the event to retrieve.
	 * @return a 200 OK response with an event that has the provided identifier.
	 * If the identifier does not corresponds with any event, a 400 Bad Request
	 * response with an error message will be returned. If an error happens
	 * while retrieving the information, a 500 Internal Server Error response with an
	 * error message will be returned.
	 */
	@GET
	@Path("/{id}")
	public Response get(
		@PathParam("id") int id
	) {
		try {
			final Event event = this.dao.get(id);
			
			return Response.ok(event).build();
		} catch (IllegalArgumentException iae) {
			LOG.log(Level.FINE, "Invalid event id in get method", iae);
			
			return Response.status(Response.Status.BAD_REQUEST)
				.entity(iae.getMessage())
			.build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error getting an event", e);
			
			return Response.serverError()
				.entity(e.getMessage())
			.build();
		}
	}
	
	@GET
	@Path("/recent")
	public Response listRecent() {
		try {
			return Response.ok(this.dao.listRecent()).build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error listing events", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	@GET
	// tiene que ser un query param
	public Response search(@QueryParam("search") String params, @QueryParam("page") int page) throws Exception{
	
		try {
			List<Event> totalEvents = this.dao.search(params);
			int totalPages =  (int) Math.ceil((double) totalEvents.size() / 6.0);
			List<Event> pageEvents =  pagination(totalEvents, page * 6);
			Object[] toret = {pageEvents, totalPages};
			return Response.ok(toret).build();
		}
		catch (IllegalArgumentException iae) {
			LOG.log(Level.FINE, "Invalid parameter in search method", iae);
			return Response.status(Response.Status.BAD_REQUEST).entity(iae.getMessage()).build();
		}
		catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error searching events " + params, e);
			return Response.serverError().entity(e.getMessage()).build();
		}catch (Exception e) {
			LOG.log(Level.SEVERE, "Error searching events " + params, e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	private List<Event> pagination(List<Event> events, int baseIndex) throws Exception{
		List<Event> toret = new LinkedList<>();
		
		for(int i = 0; i < 6; i++ ) {
			try {
				toret.add(events.get(baseIndex + i));
			}catch(IndexOutOfBoundsException e) {
				return toret;
			}catch(Exception e) {
				throw new Exception(e.getMessage());
			}
		
		}
		return toret;
	}
}
