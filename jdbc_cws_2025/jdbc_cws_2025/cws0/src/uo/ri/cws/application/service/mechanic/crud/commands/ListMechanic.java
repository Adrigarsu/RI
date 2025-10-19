package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class ListMechanic implements Command<MechanicDto> {

	private String nif; 
	 private MechanicGateway mg = Factories.persistence.forMechanic();


	public ListMechanic(String nif) {
		ArgumentChecks.isNotNull(nif);
		 ArgumentChecks.isNotBlank(nif);
	}

	public MechanicDto execute() throws BusinessException {
		
		
		Optional<MechanicRecord> op = mg.findByNif(nif);
		BusinessChecks.exists(op, "Does not exists any mecganic with the given nif");
		
		return MechanicDtoAssembler.toDto(op.get());
	}

}
