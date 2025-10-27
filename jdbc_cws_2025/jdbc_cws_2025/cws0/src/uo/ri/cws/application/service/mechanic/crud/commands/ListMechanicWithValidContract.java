package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ListMechanicWithValidContract implements Command<List<MechanicDto>> {

	
	
	private MechanicGateway mg = Factories.persistence.forMechanic();
	
	public ListMechanicWithValidContract() {
		
	}

	@Override
	public List<MechanicDto> execute() throws BusinessException {
		List<MechanicRecord> records = mg.findMechanicsWithValidContract();
        
        List<MechanicDto> mechanics = new ArrayList<>();
        
        for (MechanicRecord record : records) {
            MechanicDto dto = MechanicDtoAssembler.toDto(record);
            mechanics.add(dto);
        }
        
        return mechanics;
	}

}
