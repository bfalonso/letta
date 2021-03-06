package es.uvigo.esei.letta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.uvigo.esei.letta.dao.DAOException;
import es.uvigo.esei.letta.entities.Event;

public class EventsDAO extends DAO {

	private final static Logger LOG = Logger.getLogger(EventsDAO.class.getName());

	public Event get(int id) throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM event WHERE id=?";

			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, id);

				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return rowToEntity(result);
					} else {
						throw new IllegalArgumentException("Invalid id");
					}
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error getting an event", e);
			throw new DAOException(e);
		}
	}

	public List<Event> listRecent() throws DAOException {

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
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing recent events", e);
			throw new DAOException(e);
		}

	}

	public List<Event> search(String params) throws DAOException, IllegalArgumentException, IndexOutOfBoundsException, Exception {
		/**
		 * paginated_by = 6
		 * toret.lenght / paginated_by = numero paginas
		 * 
		 * base_limit = (numPagina - 1) * paginated_by
		 * upper_limit = (numPagina * paginated_by) - 1
		 * 
		 */
		if (!params.isEmpty() && params != null) {

			List<Event> toret = new LinkedList<>();
			Date now = new Date(System.currentTimeMillis());

			List<Event> events = new LinkedList<>();
			List<Event> copiaToret = toret;
			final String query = "SELECT * FROM event WHERE title LIKE ?";
			final String query2 = "SELECT * FROM event WHERE description LIKE ?";
			List<Event> events2 = new LinkedList<>();

			try (final ResultSet result = executeQuery(query, params)) {

				while (result.next()) {

					Event temp = rowToEntity(result);

					if (temp.getEvent_date().compareTo(now) > 0) {
						events.add(temp);
					}

				}

				toret.addAll(orderListbyDate(events));

			}

			catch (SQLException e) {

				LOG.log(Level.SEVERE, "Error listing recent events", e);
				throw new DAOException(e);
			}

			try (final ResultSet result = executeQuery(query2, params)) {

				while (result.next()) {
					Event temp = rowToEntity(result);

					if (!copiaToret.contains(temp)) {
						if (temp.getEvent_date().compareTo(now) > 0) {
							events2.add(temp);
						}
					}

				}

				toret.addAll(orderListbyDate(events2));
				
				return toret;

			} catch (SQLException e) {

				LOG.log(Level.SEVERE, "Error listing recent events", e);
				throw new DAOException(e);
			}

		} else {
			throw new IllegalArgumentException("Invalid argument in search");
		}
	}
	
	public boolean updateNumParticipants(int id, int numParticipants) throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "UPDATE event SET num_participants=? WHERE id=?";
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, numParticipants);
				statement.setInt(2, id);
				int result = statement.executeUpdate();
				if (result > 0) {
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
	
	private ResultSet executeQuery(String query, String params) throws SQLException {

		Connection conn = this.getConnection();

		PreparedStatement statement = conn.prepareStatement(query);

		statement.setString(1, "%" + params + "%");

		return statement.executeQuery();
	}

	private List<Event> orderListbyDate(List<Event> events) {
		Collections.sort(events, new Comparator<Event>() {

			@Override
			public int compare(Event e1, Event e2) {
				return e1.getEvent_date().compareTo((e2.getEvent_date()));
			}
		});

		return events;
	}

	private Event rowToEntity(ResultSet row) throws SQLException {
		return new Event(row.getInt("id"), row.getString("title"), row.getString("description"),
				row.getDate("event_date"), row.getDate("creation_date"), row.getString("location"),
				row.getString("category"), row.getInt("capacity"), row.getInt("num_participants"),
				row.getInt("duration"));
	}

}
