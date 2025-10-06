package uo.ri.cws.application.ui.manager.mechanic.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.menu.Action;

public class UpdateMechanicAction implements Action {

    
    @Override
    public void execute() throws BusinessException {

        // Get info
        String id = Console.readString("Type mechahic id to update");

        MechanicDto dto = new MechanicDto();
        dto.id = Console.readString("Type mechahic id to update");
        dto.name = Console.readString("Name");
        dto.surname = Console.readString("Surname");
        
        MechanicCrudService mcs = Factories.service.forMechanicCrudService();
        mcs.update(dto);

        // Print result
        Console.println("Mechanic updated");
    }

    private void updateMechanic(String id, String name, String surname) {

        
    }

    private void checkMechanicExists(String id) throws BusinessException {
        
    }
}