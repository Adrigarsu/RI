package uo.ri.cws.application.service.contracttype.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contractType.ContractTypeGateway;
import uo.ri.cws.application.persistence.contractType.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.assertion.ArgumentChecks;

public class FindContractTypeByName implements Command<Optional<ContractTypeDto>> {
	private String name;
    private ContractTypeGateway gateway;
    
    public FindContractTypeByName(String name) {
        this.name = name;
        this.gateway = Factories.persistence.forContractType();
    }

    @Override
    public Optional<ContractTypeDto> execute() {
        ArgumentChecks.isNotBlank(name, "Contract type name cannot be empty");
        
        Optional<ContractTypeRecord> op = gateway.findByName(name);
        if (op.isPresent()) {
            return Optional.of(toDto(op.get()));
        }
        return Optional.empty();
    }
    
    private ContractTypeDto toDto(ContractTypeRecord record) {
        ContractTypeDto dto = new ContractTypeDto();
        dto.id = record.id;
        dto.name = record.name;
        dto.compensationDays = record.compensationDays;
        dto.version = record.version;
        return dto;
    }
	

}
