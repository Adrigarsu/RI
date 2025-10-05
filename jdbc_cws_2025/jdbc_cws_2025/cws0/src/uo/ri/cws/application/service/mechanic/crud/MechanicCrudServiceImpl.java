package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.commands.AddMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.ListAllMechanics;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void update(MechanicDto dto) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<MechanicDto> findById(String id) throws BusinessException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<MechanicDto> findByNif(String nif) throws BusinessException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<MechanicDto> findAll() throws BusinessException {
		ListAllMechanics lam = new ListAllMechanics();
		
		return lam.execute();
	}

}
