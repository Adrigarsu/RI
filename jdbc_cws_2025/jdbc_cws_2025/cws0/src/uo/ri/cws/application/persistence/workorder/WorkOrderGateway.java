package uo.ri.cws.application.persistence.workorder;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;

public interface WorkOrderGateway extends Gateway<WorkOrderRecord>{

	List<InvoicingWorkOrderRecord> findNotInvoicedWorkordersByClientNif(String nif);
	
	List<WorkOrderRecord> findByMechanicId(String mechanicId);
	
	
	public class InvoicingWorkOrderRecord{
		
		public String id;
		public String description;
		public LocalDateTime date;
		public String state;
		public double amount;
		
	}
	
	public class WorkOrderRecord{
		 public String id;
		    public Double amount;
		    public Timestamp createdAt;
		    public Timestamp date;
		    public String description;
		    public String entityState;
		    public String state;
		    public Timestamp updatedAt;
		    public Long version;
		    public String invoice_id;
		    public String mechanic_id;
		    public String vehicle_id;
		    
		    @Override
		    public String toString() {
		        return "WorkOrderRecord [id=" + id + ", amount=" + amount + ", state=" + state 
		                + ", mechanic_id=" + mechanic_id + ", vehicle_id=" + vehicle_id + "]";
		    }
	}
}
