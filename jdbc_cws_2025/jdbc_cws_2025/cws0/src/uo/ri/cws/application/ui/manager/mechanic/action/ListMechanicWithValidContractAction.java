package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListMechanicWithValidContractAction implements Action {

	@Override
	public void execute() throws Exception {
		
		
		MechanicCrudService mcs = Factories.service.forMechanicCrudService();
		List<MechanicDto> list = mcs.ListMechanicWithValidContract();

		if(!list.isEmpty()) {
			Console.println("\nMechanic information \n");
			for (MechanicDto mechanicDto : list) {
				System.out.println(mechanicDto.toString());
			}
        
        
		}
	}

}
