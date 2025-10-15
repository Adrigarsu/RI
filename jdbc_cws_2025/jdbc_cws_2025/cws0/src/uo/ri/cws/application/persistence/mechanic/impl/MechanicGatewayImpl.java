package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicRecordAssembler;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class MechanicGatewayImpl implements MechanicGateway {

	

	
	@Override
	public void add(MechanicRecord record) throws PersistenceException {
		try {
        	Connection c = Jdbc.getCurrentConnection();
		try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TMECHANICS_ADD"))) {
		    pst.setString(1, record.id);
		    pst.setString(2, record.nif);
		    pst.setString(3, record.name);
		    pst.setString(4, record.surname);
		    pst.setLong(5, record.version);
			    
		    //manage parameters
		    pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
		    pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
		    pst.setString(8, "ENABLED");               
			
		    pst.executeUpdate();
		}
		
		} catch (SQLException e) {
		   	throw new RuntimeException(e);
		}
		
       

	}

	@Override
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(MechanicRecord t) throws PersistenceException {
		// Process
        try {
        	Connection c = Jdbc.getCurrentConnection();
            try (PreparedStatement pst = c
                    .prepareStatement(Queries.getSQLSentence("TMEVHANICS_UPDATE"))) {
                pst.setString(1, t.name);
                pst.setString(2, t.surname);
                pst.setTimestamp(3, new Timestamp(
						System.currentTimeMillis()));
                pst.setString(4, t.id);

                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }

	}

	@Override
	public Optional<MechanicRecord> findById(String id) throws PersistenceException {
		Optional<MechanicRecord> om = Optional.empty();
		try  {
			Connection c = Jdbc.getCurrentConnection();
            try (PreparedStatement pst = c
                    .prepareStatement(Queries.getSQLSentence("TMECHANICS_FINDBYID"))) {
                pst.setString(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                    	return Optional.of(MechanicRecordAssembler.toRecord(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
		return Optional.empty();
	}

	@Override
	public List<MechanicRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<MechanicRecord> findByNif(String nif) {
		Optional<MechanicRecord> om = Optional.empty();
		try  {
			Connection c = Jdbc.getCurrentConnection();
            try (PreparedStatement pst = c
                    .prepareStatement(Queries.getSQLSentence("TMECHANICS_FINDBYNIF"))) {
                pst.setString(1, nif);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                    	om =  Optional.of(MechanicRecordAssembler.toRecord(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
		return om;
	}

}
