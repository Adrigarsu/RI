package uo.ri.cws.application.persistence.invoice;

import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.InvoicingWorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;

public class InvoiceRecordAssembler {

		public static InvoicingWorkOrderDto toDto(InvoicingWorkOrderRecord invoicingWorkOrderRecord) {
	        InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();
	        dto.id = invoicingWorkOrderRecord.id;
	        dto.description = invoicingWorkOrderRecord.description;
	        dto.date = invoicingWorkOrderRecord.date;
	        dto.state = invoicingWorkOrderRecord.state;
	        dto.amount = invoicingWorkOrderRecord.amount;
	        
	        return dto;
	    }
		
	public static InvoicingWorkOrderRecord toRecord(InvoicingWorkOrderDto invoicingWorkOrderDto) {
			InvoicingWorkOrderRecord record = new InvoicingWorkOrderRecord();
			record.id = invoicingWorkOrderDto.id;
			record.description = invoicingWorkOrderDto.description;
			record.date = invoicingWorkOrderDto.date;
			record.state = invoicingWorkOrderDto.state;
			record.amount = invoicingWorkOrderDto.amount;
		        
	 	   return record;
		    }
		
		
}
