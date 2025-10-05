package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.commands.AddMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.DeleteMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.ListAllMechanics;
import uo.ri.cws.application.service.mechanic.crud.commands.ListMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.UpdateMechanic;
import uo.ri.util.exception.BusinessException;

public class MechanicCrudServiceImpl implements MechanicCrudService {

	@Override
	public MechanicDto create(MechanicDto dto) throws BusinessException {
		
		 //instance of the bussines object
        AddMechanic am  = new AddMechanic(dto);
		return am.execute();
	}

	@Override
	public void delete(String mechanicId) throws BusinessException {
		
		DeleteMechanic dm = new DeleteMechanic(mechanicId);
		dm.execute();

	}

	@Override
	public void update(MechanicDto dto) throws BusinessException {
		
		UpdateMechanic um = new UpdateMechanic(dto);
		um.execute();
		
	}

	@Override
	public Optional<MechanicDto> findById(String id) throws BusinessException {
		//TODO... ns como implementarlo para q con solo una clase pueda buscar
		// de dos maneras distintas sin modificar las declaraciones de los metodos
		return Optional.empty();
	}

	@Override
	public Optional<MechanicDto> findByNif(String nif) throws BusinessException {
		
		ListMechanic lm = new ListMechanic(nif);
		return lm.execute();
	}

	@Override
	public List<MechanicDto> findAll() throws BusinessException {
		ListAllMechanics lam = new ListAllMechanics();
		
		return lam.execute();
	}

}
