package uo.ri.cws.application.persistence.workorder;

import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;

public interface WorkOrderGateway extends Gateway<WorkOrderRecord>{

	List<InvoicingWorkOrderRecord> findNotInvoicedWorkordersByClientNif(String nif);
	
	public class InvoicingWorkOrderRecord{
		
		public String id;
		public String description;
		public LocalDateTime date;
		public String state;
		public double amount;
		
	}
}
