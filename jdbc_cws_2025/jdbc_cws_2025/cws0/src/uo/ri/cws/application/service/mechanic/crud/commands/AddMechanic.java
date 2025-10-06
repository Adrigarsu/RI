package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.jdbc.Jdbc;

public class AddMechanic {
	
	 private static final String TMECHANICS_ADD = "insert into TMechanics"
	            + "(id, nif, name, surname, version, "
	            + "createdAt, updatedAt, entityState) "
	            + "values (?, ?, ?, ?, ?, ?, ?, ?)";
	
	 private MechanicDto dto;
	 
	 public AddMechanic(MechanicDto dto) {
		 ArgumentChecks.isNotNull(dto);
		 ArgumentChecks.isNotBlank(dto.nif);
		 ArgumentChecks.isNotBlank(dto.name);
		 ArgumentChecks.isNotBlank(dto.surname);
		 
		 //Generation of the identity (could be done here or in the database)
		 dto.id = UUID.randomUUID().toString();
		 dto.version = 1L;
		 
		 this.dto = dto;
	 }
	 
	public MechanicDto execute () {
		
		// Process
        try (Connection c = Jdbc.getCurrentConnection();) {
            try (PreparedStatement pst = c.prepareStatement(TMECHANICS_ADD)) {
                pst.setString(1, dto.id);
                pst.setString(2, dto.nif);
                pst.setString(3, dto.name);
                pst.setString(4, dto.surname);
                pst.setLong(5, dto.version);
                
                //manage parameters
                pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                pst.setString(8, "ENABLED");               

                pst.executeUpdate();
                

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return dto;
	}
}
