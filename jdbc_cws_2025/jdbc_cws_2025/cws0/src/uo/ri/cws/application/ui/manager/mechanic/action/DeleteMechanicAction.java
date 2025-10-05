package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicCrudServiceImpl;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class DeleteMechanicAction implements Action {

    

    @Override
    public void execute() throws BusinessException {

    	//1. create dto or info necesary to the realice the operation
    	
    	String id = Console.readString("Type mechanic id ");
    	
    	//2. create crud service (by factory)
    	
    	MechanicCrudService mcs = Factories.service.forMechanicCrudService();
    	
    	
    	//3. execute operation in crud and show result to user 
    	mcs.delete(id);
    	Console.println("Mechanic deleted: " + id);

        // Process
//        try (Connection c = Jdbc.createThreadConnection();) {
//            try (PreparedStatement pst = c
//                    .prepareStatement(TMECHANICS_DELETE)) {
//                pst.setString(1, idMechanic);
//                pst.executeUpdate();
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        Console.println("Mechanic deleted");
    }

}