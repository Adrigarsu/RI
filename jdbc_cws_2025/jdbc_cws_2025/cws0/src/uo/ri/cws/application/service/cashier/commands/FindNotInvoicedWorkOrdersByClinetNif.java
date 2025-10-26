package uo.ri.cws.application.service.cashier.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceRecordAssembler;
import uo.ri.cws.application.persistence.util.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.InvoicingWorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindNotInvoicedWorkOrdersByClinetNif implements Command<List<InvoicingWorkOrderDto>> {

	private String nif ;
	private WorkOrderGateway wg = Factories.persistence.forWorkOrder();
	
	public FindNotInvoicedWorkOrdersByClinetNif(String nif) {
		ArgumentChecks.isNotNull(nif);
		
		ArgumentChecks.isNotEmpty(nif);
		this.nif= nif;
	}
	
	
	@Override
	public List<InvoicingWorkOrderDto> execute() throws BusinessException {
		List<InvoicingWorkOrderDto> result = new ArrayList<InvoicingWorkOrderDto>();
		
		List<InvoicingWorkOrderRecord> records = wg.findNotInvoicedWorkordersByClientNif(nif);
		for (InvoicingWorkOrderRecord invoicingWorkOrderRecord : records) {
			result.add(InvoiceRecordAssembler.toDto(invoicingWorkOrderRecord));
		}
		
		
		return result;
	}

}
