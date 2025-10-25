package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.Assert;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class ListMechanicByNif implements Command<Optional<MechanicDto>> {
	
	private String nif ;
	private MechanicGateway mg = Factories.persistence.forMechanic();

	public ListMechanicByNif(String nif) {
		 ArgumentChecks.isNotNull(nif);
		 ArgumentChecks.isNotBlank(nif);
		this.nif = nif;
	}

	@Override
	public Optional<MechanicDto> execute() throws BusinessException {
		
		Optional<MechanicRecord> op = mg.findByNif(nif);
		  if (op.isPresent()) {
	            return Optional.of(MechanicDtoAssembler.toDto(op.get()));
	        } else {
	            return Optional.empty();
	        }
	}

}
