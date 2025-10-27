package uo.ri.cws.application.persistence.contract;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

public interface ContractGateway extends Gateway<ContractRecord> {

	
	List<ContractRecord> findByMechanicId(String mechanicId);
	
	List<ContractRecord> findInForceContractsByMechanicId(String mechanicId);
	
	public class ContractRecord{
		
		public String id;
	    public Double annualBaseSalary;
	    public Timestamp createdAt;
	    public Date endDate;
	    public String entityState;
	    public Double settlement;
	    public Date startDate;
	    public String state;
	    public Double taxRate;
	    public Timestamp updatedAt;
	    public Long version;
	    public String contractType_id;
	    public String mechanic_id;
	    public String professionalGroup_id;
	    
	    @Override
	    public String toString() {
	        return "ContractRecord [id=" + id + ", annualBaseSalary=" + annualBaseSalary 
	                + ", state=" + state + ", startDate=" + startDate + ", endDate=" + endDate 
	                + ", mechanic_id=" + mechanic_id + "]";
	    }
		
	}
	
}
