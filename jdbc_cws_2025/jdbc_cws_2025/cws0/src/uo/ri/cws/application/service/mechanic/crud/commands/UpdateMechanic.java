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
        ArgumentChecks.isNotNull(dto.id);
        ArgumentChecks.isNotBlank(dto.id);
        ArgumentChecks.isNotNull(dto.name);
        ArgumentChecks.isNotBlank(dto.name);
        ArgumentChecks.isNotNull(dto.surname);
        ArgumentChecks.isNotBlank(dto.surname);
        ArgumentChecks.isNotNull(dto.nif);
        ArgumentChecks.isNotBlank(dto.nif);
        

		this.dto=dto;
	}


	public Void execute() throws BusinessException {
		
		 Optional<MechanicRecord> op = mg.findById(dto.id);
	        BusinessChecks.exists(op, "The mechanic does not exist");

	        MechanicRecord record = op.get();
	        BusinessChecks.isTrue(record.version == dto.version, "The mechanic has been updated by another user");

	        record.name = dto.name;
	        record.surname = dto.surname;
	        record.nif = dto.nif;
	        record.version = record.version + 1;

	        mg.update(record);
		return null;
		
	}

}
