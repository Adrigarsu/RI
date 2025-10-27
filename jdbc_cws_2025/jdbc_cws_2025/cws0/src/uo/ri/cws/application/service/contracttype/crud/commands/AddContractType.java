package uo.ri.cws.application.service.contracttype.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contractType.ContractTypeDtoAssembler;
import uo.ri.cws.application.persistence.contractType.ContractTypeGateway;
import uo.ri.cws.application.persistence.contractType.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.contractType.ContractTypeGatewayImpl;
import uo.ri.cws.application.persistence.util.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddContractType implements Command<ContractTypeDto> {

	private ContractTypeDto dto;
	private ContractTypeGateway ctGat = Factories.persistence.forContractType();
	
	public AddContractType(ContractTypeDto dto) throws BusinessException {
		ArgumentChecks.isNotNull(dto);
		 ArgumentChecks.isNotBlank(dto.name);
		 BusinessChecks.isTrue(dto.compensationDays >= 0,"Compensation days must be greatter than 0 ");
		this.dto = dto;
	}

	@Override
	public ContractTypeDto execute() throws BusinessException {
		
		Optional<ContractTypeRecord> op = ctGat.findByName(dto.name);
		BusinessChecks.doesNotExist(op, "Already exists a contract type with trhe selected name ");
		
		ctGat.add(ContractTypeDtoAssembler.toRecord(dto));
		return dto;
		
		
	}

}
