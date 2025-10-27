package uo.ri.cws.application.persistence.intervention;

import java.sql.Timestamp;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public interface InterventionGateway extends Gateway<InterventionRecord> {

	List<InterventionRecord> findByMechanicId(String mechanicId);
	
	public class InterventionRecord{
		
		public String id;
	    public Timestamp createdAt;
	    public Timestamp date;
	    public String entityState;
	    public Integer minutes;
	    public Timestamp updatedAt;
	    public Long version;
	    public String mechanic_id;
	    public String workorder_id;
	    
	    @Override
	    public String toString() {
	        return "InterventionRecord [id=" + id + ", minutes=" + minutes 
	                + ", mechanic_id=" + mechanic_id + ", workorder_id=" + workorder_id + "]";
	    }
		
	}
}
