package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;

public class ListAllMechanics implements Command<List<MechanicDto>> {
	// no need to get any info from the ui 

	  private MechanicGateway mg = Factories.persistence.forMechanic();

	  public List<MechanicDto> execute() {
	        List<MechanicRecord> records = mg.findAll();
	        
	        List<MechanicDto> mechanics = new ArrayList<>();
	        
	        for (MechanicRecord record : records) {
	            MechanicDto dto = MechanicDtoAssembler.toDto(record);
	            mechanics.add(dto);
	        }
	        
	        return mechanics;
	    }
}

