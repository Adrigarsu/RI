package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto> {
	
	 
	
	 private MechanicDto dto;
	 private MechanicGateway mg = Factories.persistence.forMechanic();
	 
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
	 
	public MechanicDto execute () throws BusinessException {
		// Process
		
		Optional<MechanicRecord> op = mg.findByNif(dto.nif);
		BusinessChecks.doesNotExist(op, "Already exists a mechanic with trhe selected nif ");
		
		mg.add(MechanicDtoAssembler.toRecord(dto));
		return dto;
		
		
		

	}
}

