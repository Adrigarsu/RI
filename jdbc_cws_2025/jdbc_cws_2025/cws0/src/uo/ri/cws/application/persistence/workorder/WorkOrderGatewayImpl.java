package uo.ri.cws.application.persistence.workorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class WorkOrderGatewayImpl implements WorkOrderGateway {
	
	

	
	@Override
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<InvoicingWorkOrderRecord> findNotInvoicedWorkordersByClientNif(String nif) {
		
		if (nif == null || nif.trim().isEmpty()) {
	        return new ArrayList<>(); 
	    }
		
		try {
	        Connection c = Jdbc.getCurrentConnection();
	        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TWORKORDERS_FIND_NOT_INVOICED"))) {
	      
	            pst.setString(1, nif);
	            try (ResultSet rs = pst.executeQuery()) {
	                List<InvoicingWorkOrderRecord> results = new ArrayList<>();
	                
	                while (rs.next()) {
	                    InvoicingWorkOrderRecord record = new InvoicingWorkOrderRecord();
	                    record.id = rs.getString("id");
	                    record.description = rs.getString("description");
	                    record.date = rs.getTimestamp("date").toLocalDateTime();
	                    record.state = rs.getString("state");
	                    record.amount = rs.getDouble("amount");
	                    
	                    results.add(record);
	                }
	                
	                return results;
	            }
	        }
	        
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}

	@Override
	public List<WorkOrderRecord> findByMechanicId(String mechanicId) {
	    // ✅ CAMBIA Jdbc.getCurrentConnection() por Jdbc.createThreadConnection()
	    try (Connection c = Jdbc.createThreadConnection();
	         PreparedStatement pst = c.prepareStatement("SELECT * FROM TWORKORDERS WHERE mechanic_id = ?")) {
	        
	        pst.setString(1, mechanicId);
	        
	        List<WorkOrderRecord> result = new ArrayList<>();
	        try (ResultSet rs = pst.executeQuery()) {
	            while (rs.next()) {
	                result.add(extractWorkOrderFrom(rs));
	            }
	        }
	        return result;
	        
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}

	private WorkOrderRecord extractWorkOrderFrom(ResultSet rs) throws SQLException {
	    WorkOrderRecord record = new WorkOrderRecord();
	    record.id = rs.getString("id");
	    record.amount = rs.getDouble("amount");
	    record.createdAt = rs.getTimestamp("createdat");
	    record.date = rs.getTimestamp("date");
	    record.description = rs.getString("description");
	    record.entityState = rs.getString("entitystate");
	    record.state = rs.getString("state");
	    record.updatedAt = rs.getTimestamp("updatedat");
	    record.version = rs.getLong("version");
	    record.invoice_id = rs.getString("invoice_id");
	    record.mechanic_id = rs.getString("mechanic_id");
	    record.vehicle_id = rs.getString("vehicle_id");
	    return record;
	}

	@Override
	public void add(uo.ri.cws.application.persistence.workorder.WorkOrderRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(uo.ri.cws.application.persistence.workorder.WorkOrderRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}






	@Override
	public Optional<uo.ri.cws.application.persistence.workorder.WorkOrderRecord> findById(String id)
			throws PersistenceException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}






	@Override
	public List<uo.ri.cws.application.persistence.workorder.WorkOrderRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
