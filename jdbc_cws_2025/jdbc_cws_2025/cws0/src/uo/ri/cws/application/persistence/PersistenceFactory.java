package uo.ri.cws.application.persistence;

import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGatewayImpl;
import uo.ri.cws.application.persistence.contractType.ContractTypeGateway;
import uo.ri.cws.application.persistence.contractType.ContractTypeGatewayImpl;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.intervention.InterventionGatewayImpl;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGatewayImpl;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.impl.MechanicGatewayImpl;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGatewayImpl;

public class PersistenceFactory {

    public MechanicGateway forMechanic() {
        return new MechanicGatewayImpl();
    }

    public WorkOrderGateway forWorkOrder() {
        return new WorkOrderGatewayImpl();
    }

    public InvoiceGateway forInvoice() {
        return new InvoiceGatewayImpl();
    }

    public InterventionGateway forIntervention() {
        return new InterventionGatewayImpl();
    }
//
//    public SparePartGateway forSparePart() {
//        return new SparePartGatewayImpl();
//    }
//
//    public SupplyGateway forSupplyGateway() {
//        return new SupplyGatewayImpl();
//    }
//
//    public SubstitutionGateway forSubstitutionsGateway() {
//        return new SubstitutionGatewayImpl();
//    }
//
//    public VehicleGateway forVehicle() {
//        return new VehicleGatewayImpl();
//    }
//
    public ContractGateway forContract() {
        return new ContractGatewayImpl();
    }
//
//    public ProfessionalGroupGateway forProfessionalGroup() {
//        return new ProfessionalGroupGatewayImpl();
//
//    }
//
    public ContractTypeGateway forContractType() {
        return new ContractTypeGatewayImpl();
    }
//
//    public PayrollGateway forPayroll() {
//        return new PayrollGatewayImpl();
//    }

}
