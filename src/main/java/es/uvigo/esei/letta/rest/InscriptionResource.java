package es.uvigo.esei.letta.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import es.uvigo.esei.letta.dao.DAOException;
import es.uvigo.esei.letta.dao.InscriptionDAO;
import es.uvigo.esei.letta.entities.Inscription;

@Path("/inscription")
@Produces(MediaType.APPLICATION_JSON)
public class InscriptionResource {
private final static Logger LOG = Logger.getLogger(EventsResource.class.getName());
	
	private final InscriptionDAO dao;
	
	private @Context SecurityContext security;
	
	public InscriptionResource() {
		this(new InscriptionDAO());
	}
	
	InscriptionResource(InscriptionDAO dao){
		this.dao = dao;
	}
	
	InscriptionResource(InscriptionDAO dao, SecurityContext security){
		this.dao = dao;
		this.security = security;
	}
	
	@POST
	public Response inscription(@FormParam("eventId") String eventId) {
		try {
			String login = this.security.getUserPrincipal().getName();
			int integerEventId = Integer.parseInt(eventId);
			boolean result = dao.create(login, integerEventId);
			return Response.ok(result).build();
		}
		catch (IllegalArgumentException iae) {
			LOG.log(Level.FINE, "Invalid person id in add method", iae);
			
			return Response.status(Response.Status.BAD_REQUEST)
				.entity(iae.getMessage())
			.build();
		}
		catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error adding a person", e);
			
			return Response.serverError()
				.entity(e.getMessage())
			.build();
		}
		
		
	}
}
