package uo.ri.cws.application.service.contracttype.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contractType.ContractTypeGateway;
import uo.ri.cws.application.persistence.util.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteContractType implements Command<Void> {

	 private String id;
	    private ContractTypeGateway gateway;
	    
	    public DeleteContractType(String id) {
	        this.id = id;
	        this.gateway = Factories.persistence.forContractType();
	    }

	    @Override
	    public Void execute() throws BusinessException {
	        ArgumentChecks.isNotBlank(id, "Contract type id cannot be empty");
	        
	        // Check if contract type exists
	        boolean exists = gateway.findById(id).isPresent();
	        BusinessChecks.isTrue(exists, "Contract type does not exist");
	        
	        // Check if has associated contracts
	        boolean hasContracts = gateway.hasContracts(id);
	        BusinessChecks.isFalse(hasContracts, 
	            "Cannot delete contract type because it has associated contracts");
	        
	        gateway.remove(id);
	        return null;
	    }

}
