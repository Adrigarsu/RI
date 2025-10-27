package uo.ri.cws.application.persistence.contractType;

import uo.ri.cws.application.persistence.contractType.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;

public class ContractTypeDtoAssembler {

	public static ContractTypeRecord toRecord(ContractTypeDto dto) {
	    if (dto == null) {
	        return null;
	    }
	    
	    ContractTypeRecord record = new ContractTypeRecord();
	    record.id = dto.id;
	    record.version = dto.version;
	    record.name = dto.name;
	    record.compensationDays = dto.compensationDays;
	    
	    return record;
	}

	public static ContractTypeDto toRecor(ContractTypeRecord record) {
	    if (record == null) {
	        return null;
	    }
	    
	    ContractTypeDto dto = new ContractTypeDto();
	    dto.id = record.id;
	    dto.version = record.version;
	    dto.name = record.name;
	    dto.compensationDays = record.compensationDays;
	    
	    return dto;
	}

}
