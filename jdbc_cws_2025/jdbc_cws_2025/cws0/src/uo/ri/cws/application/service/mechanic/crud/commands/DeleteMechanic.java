package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.console.Console;
import uo.ri.util.jdbc.Jdbc;

public class DeleteMechanic {

	private static final String TMECHANICS_DELETE = "DELETE FROM TMECHANICS "
            + "WHERE ID = ?";

	private String mechanicId;
	
	
	public DeleteMechanic(String mechanicId) {
		ArgumentChecks.isNotBlank(mechanicId);
		
		this.mechanicId = mechanicId;
	}


	public void execute() {
		
			
	      try (Connection c = Jdbc.createThreadConnection();) {
	      try (PreparedStatement pst = c
	              .prepareStatement(TMECHANICS_DELETE)) {
	          pst.setString(1, mechanicId);
	          pst.executeUpdate();
	      }
	
	  } catch (SQLException e) {
	      throw new RuntimeException(e);
	  }
	
	  Console.println("Mechanic deleted");
		
	}
	
	
	
}
