package uo.ri.cws.application.persistence.mechanic;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public class MechanicRecordAssembler {

	public static MechanicRecord toRecord(ResultSet rs) throws SQLException{
		MechanicRecord dto = new MechanicRecord();
		dto.id = rs.getString(1);
		dto.name = rs.getString(2);
		dto.surname = rs.getString(3);
		dto.nif = rs.getString(4);
		dto.version = rs.getLong(5);
		dto.createdAt = rs.getTimestamp(6).toLocalDateTime();
		dto.updatedAt = rs.getTimestamp(7).toLocalDateTime();
		dto.entityState = rs.getString(8);
		
		return dto;
		
	}
	
}
