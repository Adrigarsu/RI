package uo.ri.cws.application.service.contracttype.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contractType.ContractTypeGateway;
import uo.ri.cws.application.persistence.contractType.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateContractType implements Command<Void> {

	private ContractTypeDto dto;
    private ContractTypeGateway gateway;
	
	 public UpdateContractType(ContractTypeDto dto) throws BusinessException {
		 ArgumentChecks.isNotBlank(dto.id, "Contract type id cannot be empty");
	        ArgumentChecks.isNotNull(dto.compensationDays, "Compensation days cannot be null");
	        BusinessChecks.isTrue(dto.compensationDays >= 0, "Compensation days must be >= 0");

	    }

	    @Override
	    public Void execute() throws BusinessException {
	        
	        
	        Optional<ContractTypeRecord> op = gateway.findById(dto.id);
	        BusinessChecks.exists(op, "Contract type does not exist");
	        
	        ContractTypeRecord record = op.get();
	        record.compensationDays = dto.compensationDays;
	        
	        gateway.update(record);
	        return null;
	    }
	    
	
}
