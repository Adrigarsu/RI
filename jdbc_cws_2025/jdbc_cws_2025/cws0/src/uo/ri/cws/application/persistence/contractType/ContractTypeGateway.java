package uo.ri.cws.application.persistence.contractType;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.contractType.ContractTypeGateway.ContractTypeRecord;

public interface ContractTypeGateway extends Gateway<ContractTypeRecord> {

	
	public class ContractTypeRecord{
		public String id;
		public long version;
		
		public String name;
		public double compensationDays;
	}
	
	public static class EmployeeContractSummaryRecord {
        public String mechanicId;
        public String mechanicName;
        public String mechanicSurname;
        public String mechanicNif;
        public Double annualBaseSalary;
    }

	public Optional<ContractTypeRecord> findByName(String name);
	boolean hasContracts(String contractTypeId);
    List<EmployeeContractSummaryRecord> findEmployeesWithContractType(String contractTypeId);
}
