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
	public void add(WorkOrderRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(WorkOrderRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<WorkOrderRecord> findById(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<WorkOrderRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
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

	

}
