package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.CommandExecutor;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.commands.AddMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.DeleteMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.ListAllMechanics;
import uo.ri.cws.application.service.mechanic.crud.commands.ListMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.ListMechanicByNif;
import uo.ri.cws.application.service.mechanic.crud.commands.UpdateMechanic;
import uo.ri.util.exception.BusinessException;

public class MechanicCrudServiceImpl implements MechanicCrudService {

	CommandExecutor cmdEx = new CommandExecutor();

	@Override
	public MechanicDto create(MechanicDto dto) throws BusinessException {
		
		 //instance of the bussines object
        AddMechanic am  = new AddMechanic(dto);
		return cmdEx.execute(am);
	}

	@Override
	public void delete(String id ) throws BusinessException {
		
		DeleteMechanic dm = new DeleteMechanic(id);
		cmdEx.execute(dm);

	}

	@Override
	public void update(MechanicDto dto) throws BusinessException {
		
		UpdateMechanic um = new UpdateMechanic(dto);
		cmdEx.execute(um);
		
	}


	@Override
	public Optional<MechanicDto> findById(String id) throws BusinessException {
		
		ListMechanic lm = new ListMechanic(id);
		
		return cmdEx.execute(lm);
	}
	
	@Override
	public List<MechanicDto> findAll() throws BusinessException {
		ListAllMechanics lam = new ListAllMechanics();

		return cmdEx.execute(lam);
	}

	@Override
	public Optional<MechanicDto> findByNif(String nif) throws BusinessException {
		
		ListMechanicByNif lnif = new ListMechanicByNif(nif);
		return cmdEx.execute(lnif);
	}



}
