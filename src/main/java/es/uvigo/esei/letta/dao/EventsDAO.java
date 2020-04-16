package es.uvigo.esei.letta.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import es.uvigo.esei.letta.entities.Event;

public class EventsDAO extends DAO{
	
	private final static Logger LOG = Logger.getLogger(EventsDAO.class.getName());
	
	
	public List<Event> listRecent() throws DAOException{
		
		try (final Connection conn = this.getConnection()) {
			
			final String query = "SELECT * FROM event";
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				try (final ResultSet result = statement.executeQuery()) {
					
					final List<Event> events = new LinkedList<>();
					
					while (result.next()) {
						
						Event eventAux = rowToEntity(result);
						
						Date now = new Date(System.currentTimeMillis());
						
						if (eventAux.getEvent_date().compareTo(now) > 0
							&& eventAux.getCapacity() > eventAux.getNum_participants()) {
							events.add(eventAux);
						}
					}
					
					Collections.sort(events);
					
					return events;
					
				}
			}
		}
		catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing recent events", e);
			throw new DAOException(e);
		}
		
	}
	
	private Event rowToEntity(ResultSet row) throws SQLException {
		return new Event(
			row.getInt("id"),
			row.getString("title"),
			row.getString("description"),
			row.getDate("event_date"),
			row.getDate("creation_date"),
			row.getString("location"),
			row.getString("category"),
			row.getInt("capacity"),
			row.getInt("num_participants")
		);
	}



}
