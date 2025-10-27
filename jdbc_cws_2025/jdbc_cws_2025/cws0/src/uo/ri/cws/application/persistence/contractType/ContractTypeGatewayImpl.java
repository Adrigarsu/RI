package uo.ri.cws.application.persistence.contractType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.util.jdbc.Jdbc;

public class ContractTypeGatewayImpl implements ContractTypeGateway {

	@Override
	public void add(ContractTypeRecord t) throws PersistenceException {
		try (Connection c = Jdbc.createThreadConnection();
	             PreparedStatement pst = c.prepareStatement(
	                 "INSERT INTO TCONTRACTTYPES (id, name, compensationDays, version, createdAt, updatedAt, entityState) " +
	                 "VALUES (?, ?, ?, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ENABLED')")) {
	            
	            pst.setString(1, t.id);
	            pst.setString(2, t.name);
	            pst.setDouble(3, t.compensationDays);
	            pst.executeUpdate();
	            
	        } catch (SQLException e) {
	            throw new PersistenceException(e);
	        }

	}

	@Override
	public void remove(String id) throws PersistenceException {
        try (Connection c = Jdbc.createThreadConnection();
                PreparedStatement pst = c.prepareStatement(
                    "DELETE FROM TCONTRACTTYPES WHERE id = ?")) {
               
               pst.setString(1, id);
               pst.executeUpdate();
               
           } catch (SQLException e) {
               throw new PersistenceException(e);
           }


	}

	@Override
	public void update(ContractTypeRecord t) throws PersistenceException {
		try (Connection c = Jdbc.createThreadConnection();
	             PreparedStatement pst = c.prepareStatement(
	                 "UPDATE TCONTRACTTYPES SET compensationDays = ?, updatedAt = CURRENT_TIMESTAMP " +
	                 "WHERE id = ?")) {
	            
	            pst.setDouble(1, t.compensationDays);
	            pst.setString(2, t.id);
	            pst.executeUpdate();
	            
	        } catch (SQLException e) {
	            throw new PersistenceException(e);
	        }

	}

	@Override
	public Optional<ContractTypeRecord> findById(String id) throws PersistenceException {
		try (Connection c = Jdbc.createThreadConnection();
	             PreparedStatement pst = c.prepareStatement(
	                 "SELECT * FROM TCONTRACTTYPES WHERE id = ?")) {
	            
	            pst.setString(1, id);
	            try (ResultSet rs = pst.executeQuery()) {
	                if (rs.next()) {
	                    return Optional.of(extractContractTypeFrom(rs));
	                }
	            }
	        } catch (SQLException e) {
	            throw new PersistenceException(e);
	        }
	        return Optional.empty();
	}

	@Override
	public List<ContractTypeRecord> findAll() throws PersistenceException {
		 List<ContractTypeRecord> result = new ArrayList<>();
	        try (Connection c = Jdbc.createThreadConnection();
	             PreparedStatement pst = c.prepareStatement(
	                 "SELECT * FROM TCONTRACTTYPES ORDER BY name")) {
	            
	            try (ResultSet rs = pst.executeQuery()) {
	                while (rs.next()) {
	                    result.add(extractContractTypeFrom(rs));
	                }
	            }
	        } catch (SQLException e) {
	            throw new PersistenceException(e);
	        }
	        return result;
	}

	@Override
	public Optional<ContractTypeRecord> findByName(String name) {
		 try (Connection c = Jdbc.createThreadConnection();
	             PreparedStatement pst = c.prepareStatement(
	                 "SELECT * FROM TCONTRACTTYPES WHERE name = ?")) {
	            
	            pst.setString(1, name);
	            try (ResultSet rs = pst.executeQuery()) {
	                if (rs.next()) {
	                    return Optional.of(extractContractTypeFrom(rs));
	                }
	            }
	        } catch (SQLException e) {
	            throw new PersistenceException(e);
	        }
	        return Optional.empty();
	}

	@Override
	public boolean hasContracts(String contractTypeId) {
		try (Connection c = Jdbc.createThreadConnection();
	             PreparedStatement pst = c.prepareStatement(
	                 "SELECT COUNT(*) FROM TCONTRACTS WHERE contractType_id = ?")) {
	            
	            pst.setString(1, contractTypeId);
	            try (ResultSet rs = pst.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt(1) > 0;
	                }
	            }
	        } catch (SQLException e) {
	            throw new PersistenceException(e);
	        }
	        return false;
	}

	@Override
	public List<EmployeeContractSummaryRecord> findEmployeesWithContractType(String contractTypeId) {
		List<EmployeeContractSummaryRecord> result = new ArrayList<>();
        try (Connection c = Jdbc.createThreadConnection();
             PreparedStatement pst = c.prepareStatement(
                 "SELECT m.id, m.name, m.surname, m.nif, c.annualBaseSalary " +
                 "FROM TMECHANICS m " +
                 "INNER JOIN TCONTRACTS c ON m.id = c.mechanic_id " +
                 "WHERE c.contractType_id = ? AND c.state = 'IN_FORCE' " +
                 "ORDER BY m.surname, m.name")) {
            
            pst.setString(1, contractTypeId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    EmployeeContractSummaryRecord record = new EmployeeContractSummaryRecord();
                    record.mechanicId = rs.getString("id");
                    record.mechanicName = rs.getString("name");
                    record.mechanicSurname = rs.getString("surname");
                    record.mechanicNif = rs.getString("nif");
                    record.annualBaseSalary = rs.getDouble("annualBaseSalary");
                    result.add(record);
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return result;
	}

	private ContractTypeRecord extractContractTypeFrom(ResultSet rs) throws SQLException {
        ContractTypeRecord record = new ContractTypeRecord();
        record.id = rs.getString("id");
        record.name = rs.getString("name");
        record.compensationDays = rs.getDouble("compensationDays");
        record.version = rs.getLong("version");
        return record;
    }
	
}
