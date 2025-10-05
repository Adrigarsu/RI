package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListMechanicAction implements Action {

    
    @Override
    public void execute() throws BusinessException {

        // Get info
        String nif = Console.readString("nif");
        
        MechanicCrudService mcs = Factories.service.forMechanicCrudService();

        Console.println("\nMechanic information \n");
        
        mcs.findByNif(nif);

        
    }
}