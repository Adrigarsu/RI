package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {
	
	private String id;
	private MechanicGateway mg;
	
	public DeleteMechanic(String id) {
		ArgumentChecks.isNotBlank(id);
		this.id = id;
	}


	public Void execute() throws BusinessException {
		
		Optional<MechanicRecord> op = mg.findById(id);
		BusinessChecks.exists(op, "The selected id does not belomg to any mechanic ");
		
	   mg.remove(id);
	   Console.println("Mechanic deleted");
	   return null;
	   
	}
	
	
	
}
