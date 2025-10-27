package uo.ri.cws.application.persistence.intervention;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.util.jdbc.Jdbc;

public class InterventionGatewayImpl implements InterventionGateway {

	@Override
	public void add(InterventionRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(InterventionRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<InterventionRecord> findById(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<InterventionRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<InterventionRecord> findByMechanicId(String mechanicId) {
	    // ✅ CAMBIA Jdbc.getCurrentConnection() por Jdbc.createThreadConnection()
	    try (Connection c = Jdbc.createThreadConnection();
	         PreparedStatement pst = c.prepareStatement("SELECT * FROM TINTERVENTIONS WHERE mechanic_id = ?")) {
	        
	        pst.setString(1, mechanicId);
	        
	        List<InterventionRecord> result = new ArrayList<>();
	        try (ResultSet rs = pst.executeQuery()) {
	            while (rs.next()) {
	                result.add(extractInterventionFrom(rs));
	            }
	        }
	        return result;
	        
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}
	private InterventionRecord extractInterventionFrom(ResultSet rs) throws SQLException {
	    InterventionRecord record = new InterventionRecord();
	    record.id = rs.getString("id");
	    record.createdAt = rs.getTimestamp("createdat");
	    record.date = rs.getTimestamp("date");
	    record.entityState = rs.getString("entitystate");
	    record.minutes = rs.getInt("minutes");
	    record.updatedAt = rs.getTimestamp("updatedat");
	    record.version = rs.getLong("version");
	    record.mechanic_id = rs.getString("mechanic_id");
	    record.workorder_id = rs.getString("workorder_id");
	    return record;
	}

}
