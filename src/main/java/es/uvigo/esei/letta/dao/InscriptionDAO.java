package es.uvigo.esei.letta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.uvigo.esei.letta.entities.Event;
import es.uvigo.esei.letta.entities.Inscription;

public class InscriptionDAO extends DAO {
	
	private final static Logger LOG = Logger.getLogger(InscriptionDAO.class.getName());
	
	
	public boolean create(String login, int eventId) throws DAOException {
		if(increaseEventAssistant(eventId, login)) {
			try (final Connection conn = this.getConnection()) {
				final String query = "INSERT INTO inscription (login, event_id) VALUES (?, ?)";
				
				try (final PreparedStatement statement = conn.prepareStatement(query)) {
					statement.setString(1, login);
					statement.setInt(2, eventId);
					
					int result = statement.executeUpdate();
					if (result > 0) {
						EventsDAO eventsDAO = new EventsDAO();
						Event event = eventsDAO.get(eventId);
						int newNumParticipants = event.getNum_participants() + 1;
						eventsDAO.updateNumParticipants(eventId, newNumParticipants);
						return true; 
					} 
					else {
						return false;
					}
				}
			}
			catch (SQLException e) {
				LOG.log(Level.SEVERE, "Error creating an event", e);
				throw new DAOException(e);
			}
		}
		return false;
	}
	
	public boolean existsInscription(int eventId, String login) throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM inscription WHERE event_id=? AND login=?";

			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, eventId);
				statement.setString(2, login);

				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return true;
					} else {
						return false;
					}
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error getting an event", e);
			throw new DAOException(e);
		}
	}
	
	
	private boolean increaseEventAssistant(int eventId, String login) throws IllegalArgumentException, DAOException {
		// Check if it's already an assistant
		if(!existsInscription(eventId, login)) {
			EventsDAO eventsDAO = new EventsDAO();
			Event event = eventsDAO.get(eventId);
			if(!event.isFull()) {
				return true;
			}
		}
		return false;
	}
	
	
	private Inscription rowToEntity(ResultSet row) throws SQLException {
		return new Inscription(row.getInt("id"), row.getString("login"), row.getInt("event_id"));
	}
}
