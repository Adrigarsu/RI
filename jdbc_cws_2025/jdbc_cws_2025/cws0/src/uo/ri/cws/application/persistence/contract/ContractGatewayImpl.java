package uo.ri.cws.application.persistence.contract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.util.jdbc.Jdbc;

public class ContractGatewayImpl implements ContractGateway {

	@Override
	public void add(ContractRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ContractRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<ContractRecord> findById(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<ContractRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ContractRecord> findInForceContractsByMechanicId(String mechanicId) {
	    // ✅ CAMBIA Jdbc.getCurrentConnection() por Jdbc.createThreadConnection()
	    try (Connection con = Jdbc.createThreadConnection();
	         PreparedStatement pst = con.prepareStatement(
	             "SELECT * FROM TCONTRACTS WHERE MECHANIC_ID = ? AND STATE = 'IN_FORCE'")) {
	        
	        pst.setString(1, mechanicId);
	        
	        List<ContractRecord> records = new ArrayList<>();
	        try (ResultSet rs = pst.executeQuery()) {
	            while (rs.next()) {
	                records.add(resultSetToContractRecord(rs));
	            }
	        }
	        return records;
	        
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}

	// ✅ APLICA EL MISMO CAMBIO en findByMechanicId:
	@Override
	public List<ContractRecord> findByMechanicId(String mechanicId) {
	    try (Connection c = Jdbc.createThreadConnection(); // ✅ CAMBIADO
	         PreparedStatement pst = c.prepareStatement("SELECT * FROM TCONTRACTS WHERE mechanic_id = ?")) {
	        
	        pst.setString(1, mechanicId);
	        
	        List<ContractRecord> result = new ArrayList<>();
	        try (ResultSet rs = pst.executeQuery()) {
	            while (rs.next()) {
	                result.add(extractContractFrom(rs));
	            }
	        }
	        return result;
	        
	    } catch (SQLException e) {
	        throw new PersistenceException(e);
	    }
	}


    /**
     * Método "mapper" que convierte una fila de ResultSet 
     * al ContractRecord que definiste.
     */
    private ContractRecord resultSetToContractRecord(ResultSet rs) throws SQLException {
        ContractRecord record = new ContractRecord();
        
        // Mapeo de todos los campos de tu ContractRecord
        record.id = rs.getString("ID");
        record.mechanic_id = rs.getString("MECHANIC_ID");
        record.contractType_id = rs.getString("CONTRACTTYPE_ID");
        record.professionalGroup_id = rs.getString("PROFESSIONALGROUP_ID");
        record.entityState = rs.getString("ENTITYSTATE");
        record.state = rs.getString("STATE");
        
        // Mapeo de Timestamps
        record.createdAt = rs.getTimestamp("CREATEDAT");
        record.updatedAt = rs.getTimestamp("UPDATEDAT");
        
        // Mapeo de Dates (convirtiendo java.sql.Date a java.util.Date)
        java.sql.Date sqlStartDate = rs.getDate("STARTDATE");
        if (sqlStartDate != null) {
            record.startDate = new java.util.Date(sqlStartDate.getTime());
        } else {
            record.startDate = null; //
        }
        
        java.sql.Date sqlEndDate = rs.getDate("ENDDATE");
        if (sqlEndDate != null) {
            record.endDate = new java.util.Date(sqlEndDate.getTime());
        } else {
            record.endDate = null; //
        }

        // Mapeo de Doubles (manejando posibles NULLs)
        // rs.getDouble() devuelve 0.0 si es NULL, por eso comprobamos con rs.wasNull()
        
        record.annualBaseSalary = rs.getDouble("ANNUALBASESALARY");
        if (rs.wasNull()) {
            record.annualBaseSalary = null; //
        }
        
        record.settlement = rs.getDouble("SETTLEMENT");
        if (rs.wasNull()) {
            record.settlement = null; //
        }
        
        record.taxRate = rs.getDouble("TAXRATE");
        if (rs.wasNull()) {
            record.taxRate = null; //
        }

        // Mapeo de Long (manejando posibles NULLs)
        record.version = rs.getLong("VERSION");
        if (rs.wasNull()) {
            record.version = null; //
        }

        return record;
    }

	private ContractRecord extractContractFrom(ResultSet rs) throws SQLException {
	    ContractRecord record = new ContractRecord();
	    record.id = rs.getString("id");
	    record.annualBaseSalary = rs.getDouble("annualbasesalary");
	    record.createdAt = rs.getTimestamp("createdat");
	    record.endDate = rs.getDate("enddate");
	    record.entityState = rs.getString("entitystate");
	    record.settlement = rs.getDouble("settlement");
	    record.startDate = rs.getDate("startdate");
	    record.state = rs.getString("state");
	    record.taxRate = rs.getDouble("taxrate");
	    record.updatedAt = rs.getTimestamp("updatedat");
	    record.version = rs.getLong("version");
	    record.contractType_id = rs.getString("contracttype_id");
	    record.mechanic_id = rs.getString("mechanic_id");
	    record.professionalGroup_id = rs.getString("professionalgroup_id");
	    return record;
	}

}
