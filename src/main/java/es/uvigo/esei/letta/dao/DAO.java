package es.uvigo.esei.letta.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public abstract class DAO {
	private final static Logger LOG = Logger.getLogger(DAO.class.getName());
	private final static String JNDI_NAME = "java:/comp/env/jdbc/letta"; 
	
	private DataSource dataSource;
	
	public DAO() {
		try {
			this.dataSource = (DataSource) new InitialContext().lookup(JNDI_NAME);
		} catch (NamingException e) {
			LOG.log(Level.SEVERE, "Error initializing DAO", e);
			throw new RuntimeException(e);
		}
	}
	
	protected Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}
}

