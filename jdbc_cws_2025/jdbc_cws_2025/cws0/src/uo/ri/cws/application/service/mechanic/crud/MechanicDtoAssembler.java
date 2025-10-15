package uo.ri.cws.application.service.mechanic.crud;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class MechanicDtoAssembler {
	
	public static MechanicDto toDto(MechanicRecord record) {

		MechanicDto dto = new MechanicDto();
	    dto.id = record.id;
	    dto.version = record.version;
	    dto.nif = record.nif;
	    dto.name = record.name;
	    dto.surname = record.surname;
	    return dto;

	}

	public static MechanicRecord toRecord(MechanicDto dto) {

		MechanicRecord record = new MechanicRecord();
	    record.id = dto.id;
	    record.version = dto.version;
	    record.nif = dto.nif;
	    record.name = dto.name;
	    record.surname = dto.surname;
	    return record;

	}

	
}
