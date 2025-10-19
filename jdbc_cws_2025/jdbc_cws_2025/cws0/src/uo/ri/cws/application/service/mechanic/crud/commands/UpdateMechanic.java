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

public class UpdateMechanic implements Command<Void> {

	
	
	private MechanicDto dto;
	private MechanicGateway mg = Factories.persistence.forMechanic();
	
	public UpdateMechanic(MechanicDto dto) {
		 ArgumentChecks.isNotNull(dto);
		 ArgumentChecks.isNotBlank(dto.nif);
		 ArgumentChecks.isNotBlank(dto.name);
		 ArgumentChecks.isNotBlank(dto.surname);
		 
		 //Generation of the identity (could be done here or in the database)
		 dto.id = UUID.randomUUID().toString();
		 dto.version = 1L;
		this.dto=dto;
	}


	public Void execute() throws BusinessException {
		
        //checkMechanicExists(dto.id); transform into ...
		Optional<MechanicRecord> om = mg.findById(dto.id);
		BusinessChecks.exists(om, "The mechanic does not exist");
		//check that the version is updated
		BusinessChecks.hasVersion(dto.version, om.get().version);
		
		//in case that the exception is not thrown, the update is execute
        //updateMechanic(dto.id, dto.name, dto.surname);
		mg.update(MechanicDtoAssembler.toRecord(dto));
		return null;
		
	}

}
