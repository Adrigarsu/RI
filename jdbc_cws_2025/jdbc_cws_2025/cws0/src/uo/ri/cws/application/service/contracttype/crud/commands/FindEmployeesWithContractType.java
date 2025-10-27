package uo.ri.cws.application.service.contracttype.crud.commands;

import java.util.List;
import java.util.stream.Collectors;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contractType.ContractTypeGateway;
import uo.ri.cws.application.persistence.contractType.ContractTypeGateway.EmployeeContractSummaryRecord;
import uo.ri.cws.application.persistence.util.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeSummaryDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindEmployeesWithContractType implements Command<List<ContractTypeSummaryDto>> {
    
    private String contractTypeId;
    private ContractTypeGateway gateway;
    
    public FindEmployeesWithContractType(String contractTypeId) {
        this.contractTypeId = contractTypeId;
        this.gateway = Factories.persistence.forContractType();
    }

    @Override
    public List<ContractTypeSummaryDto> execute() throws BusinessException {
        ArgumentChecks.isNotBlank(contractTypeId, "Contract type id cannot be empty");
        
        // Check if contract type exists
        boolean exists = gateway.findById(contractTypeId).isPresent();
        BusinessChecks.isTrue(exists, "Contract type does not exist");
        
        List<EmployeeContractSummaryRecord> records = gateway.findEmployeesWithContractType(contractTypeId);
        
        // Calculate totals
        long totalEmployees = records.size();
        double totalAnnualBaseSalary = records.stream()
                .mapToDouble(r -> r.annualBaseSalary != null ? r.annualBaseSalary : 0.0)
                .sum();
        
        // Convert to DTOs and add summary
        List<ContractTypeSummaryDto> result = records.stream()
                .map(record -> toDto(record, totalEmployees, totalAnnualBaseSalary))
                .collect(Collectors.toList());
        
        // Add summary row if there are results
        if (!result.isEmpty()) {
            ContractTypeSummaryDto summary = new ContractTypeSummaryDto();
            summary.totalEmployees = totalEmployees;
            summary.totalAnnualBaseSalary = totalAnnualBaseSalary;
            result.add(summary);
        }
        
        return result;
    }
    
    private ContractTypeSummaryDto toDto(EmployeeContractSummaryRecord record, 
                                       long totalEmployees, double totalAnnualBaseSalary) {
        ContractTypeSummaryDto dto = new ContractTypeSummaryDto();
        dto.mechanicId = record.mechanicId;
        dto.mechanicName = record.mechanicName;
        dto.mechanicSurname = record.mechanicSurname;
        dto.mechanicNif = record.mechanicNif;
        dto.annualBaseSalary = record.annualBaseSalary;
        dto.totalEmployees = totalEmployees;
        dto.totalAnnualBaseSalary = totalAnnualBaseSalary;
        return dto;
    }
}