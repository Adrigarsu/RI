package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.hsqldb.lib.List;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.jdbc.Jdbc;

public class ListAllMechanics {
	// no need to get any info from the ui 
	
	private static final String TMECHANICS_FINDALL = "SELECT ID, NAME, "
            + "SURNAME, NIF, VERSION FROM TMECHANICS";
	
	public List<MechanicDto> execute() {
		List<MechanicDto> mechanics = new ArrayList<MechanicDto>();
		
		 // Process
        try (Connection c = Jdbc.createThreadConnection()) {
            try (PreparedStatement pst = c
                    .prepareStatement(TMECHANICS_FINDALL)) {
                try (ResultSet rs = pst.executeQuery();) {
                    while (rs.next()) {
                        MechanicDto m = new MechanicDto();
                        m.id = rs.getString("id");
                        m.name = rs.getString("name");
                        m.surname = rs.getString("surname");
                        m.nif = rs.getString("nif");
                        
                        mechanics.add(m);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		return mechanics;
	}

}
