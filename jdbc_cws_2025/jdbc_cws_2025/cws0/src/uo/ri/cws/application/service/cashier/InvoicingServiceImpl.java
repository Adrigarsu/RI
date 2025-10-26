package uo.ri.cws.application.service.cashier;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.CommandExecutor;
import uo.ri.cws.application.service.cashier.commands.FindNotInvoicedWorkOrdersByClinetNif;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.util.exception.BusinessException;

public class InvoicingServiceImpl implements InvoicingService {
	
	CommandExecutor cmdEx = new CommandExecutor();

	@Override
	public InvoiceDto create(List<String> workOrderIds) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InvoicingWorkOrderDto> findWorkOrdersByClientNif(String nif) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InvoicingWorkOrderDto> findNotInvoicedWorkOrdersByClientNif(String nif) throws BusinessException {
		FindNotInvoicedWorkOrdersByClinetNif find = new FindNotInvoicedWorkOrdersByClinetNif(nif);
		return cmdEx.execute(find);
	}

	@Override
	public List<InvoicingWorkOrderDto> findWorkOrdersByPlateNumber(String plate) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<InvoiceDto> findInvoiceByNumber(Long number) throws BusinessException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<PaymentMeanDto> findPayMeansByClientNif(String nif) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void settleInvoice(String invoiceId, Map<String, Double> charges) throws BusinessException {
		// TODO Auto-generated method stub

	}

}
