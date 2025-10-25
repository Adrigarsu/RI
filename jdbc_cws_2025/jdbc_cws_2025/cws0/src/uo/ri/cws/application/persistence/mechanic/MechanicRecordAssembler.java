package uo.ri.cws.application.persistence.mechanic;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public class MechanicRecordAssembler {

	 public static MechanicRecord toRecord(ResultSet rs) throws SQLException {
	        MechanicRecord dto = new MechanicRecord();
	        dto.id = rs.getString("id");
	        dto.name = rs.getString("name");
	        dto.surname = rs.getString("surname");
	        dto.nif = rs.getString("nif");
	        dto.version = rs.getLong("version");
	        
	        try {
	            java.sql.Timestamp createdAt = rs.getTimestamp("createdAt");
	            dto.createdAt = createdAt != null ? createdAt.toLocalDateTime() : null;
	        } catch (SQLException e) {
	            dto.createdAt = null;
	        }
	        
	        try {
	            java.sql.Timestamp updatedAt = rs.getTimestamp("updatedAt");
	            dto.updatedAt = updatedAt != null ? updatedAt.toLocalDateTime() : null;
	        } catch (SQLException e) {
	            dto.updatedAt = null;
	        }
	        
	        try {
	            dto.entityState = rs.getString("entityState");
	        } catch (SQLException e) {
	            dto.entityState = null;
	        }
	        
	        return dto;
	    }
	
}
