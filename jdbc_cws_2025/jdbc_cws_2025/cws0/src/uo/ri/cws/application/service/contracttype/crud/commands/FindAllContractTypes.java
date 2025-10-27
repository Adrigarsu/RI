package uo.ri.cws.application.service.contracttype.crud.commands;

import java.util.List;
import java.util.stream.Collectors;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contractType.ContractTypeGateway;
import uo.ri.cws.application.persistence.contractType.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;

public class FindAllContractTypes implements Command<List<ContractTypeDto>> {
	 private ContractTypeGateway gateway;
	    
	    public FindAllContractTypes() {
	        this.gateway = Factories.persistence.forContractType();
	    }

	    @Override
	    public List<ContractTypeDto> execute() {
	        List<ContractTypeRecord> records = gateway.findAll();
	        return records.stream()
	                .map(this::toDto)
	                .collect(Collectors.toList());
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
