package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {
	
	private String id;
    private MechanicGateway mg;
    private WorkOrderGateway wg;
    private InterventionGateway ig;
    private ContractGateway cg;
    
    public DeleteMechanic(String id) {
        ArgumentChecks.isNotBlank(id);
        this.id = id;
        mg = Factories.persistence.forMechanic();
        wg = Factories.persistence.forWorkOrder();
        ig = Factories.persistence.forIntervention();
        cg = Factories.persistence.forContract();
    }

    public Void execute() throws BusinessException {
        
        Optional<MechanicRecord> op = mg.findById(id);
        BusinessChecks.exists(op, "The mechanic does not exist");
        
        boolean hasWorkOrders = !wg.findByMechanicId(id).isEmpty();
        BusinessChecks.isFalse(hasWorkOrders, 
            "Mechanic cannot be deleted because it has work orders assigned");
        
        boolean hasInterventions = !ig.findByMechanicId(id).isEmpty();
        BusinessChecks.isFalse(hasInterventions, 
            "Mechanic cannot be deleted because it has interventions");
        
        boolean hasActiveContracts = !cg.findInForceContractsByMechanicId(id).isEmpty();
        BusinessChecks.isFalse(hasActiveContracts, 
            "Mechanic cannot be deleted because it has contracts in force");
        
        boolean hasAnyContracts = !cg.findByMechanicId(id).isEmpty();
        BusinessChecks.isFalse(hasAnyContracts, 
            "Mechanic cannot be deleted because it has contracts");
        
        mg.remove(id);
        Console.println("Mechanic deleted");
        return null;
    }
	
	
	
}
