package uo.ri.cws.application.service.contracttype.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.CommandExecutor;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.crud.commands.AddContractType;
import uo.ri.cws.application.service.contracttype.crud.commands.DeleteContractType;
import uo.ri.cws.application.service.contracttype.crud.commands.FindAllContractTypes;
import uo.ri.cws.application.service.contracttype.crud.commands.FindContractTypeByName;
import uo.ri.cws.application.service.contracttype.crud.commands.FindEmployeesWithContractType;
import uo.ri.cws.application.service.contracttype.crud.commands.UpdateContractType;
import uo.ri.util.exception.BusinessException;

public class ContractTypeCrudServiceImpl implements ContractTypeCrudService {

	private CommandExecutor cmdEx = new CommandExecutor();
	
	@Override
	public ContractTypeDto create(ContractTypeDto dto) throws BusinessException {
		AddContractType  ac = new AddContractType(dto);
		return cmdEx.execute(ac);
	}

	@Override
	public void delete(String name) throws BusinessException {
		DeleteContractType dc = new DeleteContractType(name);
		cmdEx.execute(dc);

	}

	@Override
	public void update(ContractTypeDto dto) throws BusinessException {
		UpdateContractType uc = new UpdateContractType(dto);
		cmdEx.execute(uc);

	}

	@Override
	public Optional<ContractTypeDto> findByName(String name) throws BusinessException {
		FindContractTypeByName fbn = new FindContractTypeByName(name);
		if(cmdEx.execute(fbn).isEmpty()) return Optional.empty();
		else return cmdEx.execute(fbn);
	}

	@Override
	public List<ContractTypeDto> findAll() throws BusinessException {
		FindAllContractTypes fall = new FindAllContractTypes();
		return cmdEx.execute(fall);
	}
	
	@Override
    public List<ContractTypeSummaryDto> findEmployeesWithContractType(String contractTypeId) throws BusinessException {
		FindEmployeesWithContractType femp = new FindEmployeesWithContractType(contractTypeId);
		return cmdEx.execute(femp);
				
    }

}
