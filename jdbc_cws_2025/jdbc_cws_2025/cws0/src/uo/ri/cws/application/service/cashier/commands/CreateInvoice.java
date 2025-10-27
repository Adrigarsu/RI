package uo.ri.cws.application.service.cashier.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uo.ri.cws.application.persistence.util.Command;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;
import uo.ri.util.math.Rounds;

public class CreateInvoice implements Command<InvoiceDto> {

	private List<String> workOrderIds;
	
	public CreateInvoice(List<String> workOrderIds) {
		
        ArgumentChecks.isNotNull(workOrderIds, "Work order ids cannot be null");
        ArgumentChecks.isTrue(!workOrderIds.isEmpty(), "Work order ids cannot be empty");
        
        this.workOrderIds = new ArrayList<String>();
        for (String workOrderId : workOrderIds) {
        	ArgumentChecks.isNotNull(workOrderId, "Work order id cannot be null");
            ArgumentChecks.isNotBlank(workOrderId, "Work order id cannot be blank");
            this.workOrderIds.add(workOrderId);
        }
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
	    InvoiceDto dto = new InvoiceDto();
	    
	    try (Connection ignored = Jdbc.createThreadConnection()) {
	        
	        if (!checkWorkOrdersExist(workOrderIds))
	            throw new BusinessException("Workorder does not exist");
	        if (!checkWorkOrdersFinished(workOrderIds))
	            throw new BusinessException("Workorder is not finished yet");

	        long numberInvoice = generateInvoiceNumber();
	        LocalDate dateInvoice = LocalDate.now();
	        double amount = calculateTotalInvoice(workOrderIds); 
	        double vatPercentage = 0.21;                        
	        double vatAmount = amount * vatPercentage;          
	        double total = amount + vatAmount;                  
	        total = Rounds.toCents(total);
	        vatAmount = Rounds.toCents(vatAmount);             

	       
	        String idInvoice = createInvoice(numberInvoice, dateInvoice, vatAmount, total);
	        
	        linkWorkordersToInvoice(idInvoice, workOrderIds);
	        markWorkOrderAsInvoiced(workOrderIds);
	        updateVersion(workOrderIds);
	        updateTimeVersion(workOrderIds);
	        
	        
	        dto.id = idInvoice;
	        dto.amount = total;           
	        dto.date = dateInvoice;
	        dto.vat = vatAmount;          
	        dto.number = numberInvoice;
	        dto.state = "NOT_YET_PAID";
	        dto.version = 1L;

	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	    return dto;
	}
	
	
	private void updateTimeVersion(List<String> workOrderIds) throws SQLException {
      Connection c = Jdbc.getCurrentConnection();

      try (PreparedStatement pst = c
              .prepareStatement(Queries.getSQLSentence("TWORKORDERS_UPDATE_TIMESTAMP"))) {
          for (String workOrderID : workOrderIds) {
              pst.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
              pst.setString(2, workOrderID);
              pst.executeUpdate();
          }
      }
		
	}

	private void updateVersion(List<String> workOrderIds) throws SQLException {
      Connection c = Jdbc.getCurrentConnection();

      try (PreparedStatement pst = c
              .prepareStatement(Queries.getSQLSentence("TWORKORDERS_UPDATE_VERSION"))) {
          for (String workOrderID : workOrderIds) {
              pst.setString(1, workOrderID);
              pst.executeUpdate();
          }
      }
  }

  

  /*
   * checks whether every work order exist
   */
  private boolean checkWorkOrdersExist(List<String> workOrderIDS)
          throws SQLException {

      Connection c = Jdbc.getCurrentConnection();

      try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TWORKORDERS_FIND_ID_BY_ID"))) {
          for (String workOrderID : workOrderIDS) {
              pst.setString(1, workOrderID);
              try (ResultSet rs = pst.executeQuery()) {
                  if (!rs.next())
                      return false; // Si no encuentra la orden de trabajo
              }
          }
      }
      return true;
  }

  /*
   * checks whether every work order id is FINISHED
   */
  private boolean checkWorkOrdersFinished(List<String> workOrderIDs)
          throws SQLException {
      Connection connection = Jdbc.getCurrentConnection();

      try (PreparedStatement pst = connection
              .prepareStatement(Queries.getSQLSentence("TWORKORDERS_FIND_STATUS_BY_ID"))) {
          for (String id : workOrderIDs) {
              pst.setString(1, id);
              try (ResultSet rs = pst.executeQuery()) {
                  if (rs.next()) {
                      String status = rs.getString("state");
                      if (!"FINISHED".equalsIgnoreCase(status)) {
                          return false;
                      }
                  }
              }
          }
      }
      return true;
  }

  /*
   * Generates next invoice number (not to be confused with the inner id)
   */
  private long generateInvoiceNumber() throws SQLException {
      Connection connection = Jdbc.getCurrentConnection();

      try (PreparedStatement pst = connection
              .prepareStatement(Queries.getSQLSentence("TINVOICES_SELECT_LAST_NUMBER"))) {
          try (ResultSet rs = pst.executeQuery()) {
              if (rs.next()) {
                  return rs.getLong(1) + 1;
              }
          }
      }
      return 1L; // Si no hay facturas previas, empezamos desde 1
  }

  /*
   * Compute total amount of the invoice (as the total of individual work
   * orders' amount
   */
  private double calculateTotalInvoice(List<String> workOrderIDs)
          throws SQLException {
      Connection connection = Jdbc.getCurrentConnection();

      double total = 0.0;
      try (PreparedStatement pst = connection
              .prepareStatement(Queries.getSQLSentence("TWORKORDERS_FIND_AMOUNT_BY_ID"))) {
          for (String id : workOrderIDs) {
              pst.setString(1, id);
              try (ResultSet rs = pst.executeQuery()) {
                  if (rs.next()) {
                      total += rs.getDouble("amount");
                  }
              }
          }
      }
      return total;
  }

  /*
   * returns vat percentage
   */
  private double vatPercentage(LocalDate d) {
      return LocalDate.parse("2012-07-01").isBefore(d) ? 21.0 : 18.0;

  }

  /*
   * Creates the invoice in the database; returns the id
   */
  private String createInvoice(long numberInvoice, LocalDate dateInvoice, 
	        double vatAmount, double total) throws SQLException {
	    Connection connection = Jdbc.getCurrentConnection();
	    String idInvoice = UUID.randomUUID().toString();
	    LocalDateTime now = LocalDateTime.now();
	    
	    try (PreparedStatement pst = connection.prepareStatement(Queries.getSQLSentence("TINVOICES_INSERT"))) {
	        pst.setString(1, idInvoice);                    // id
	        pst.setLong(2, numberInvoice);                  // number
	        pst.setDate(3, java.sql.Date.valueOf(dateInvoice)); // date
	        pst.setDouble(4, vatAmount);                    // vat = monto del IVA
	        pst.setDouble(5, total);                        // amount = total con IVA
	        pst.setString(6, "NOT_YET_PAID");               // state
	        pst.setLong(7, 1L);                             // version
	        pst.setTimestamp(8, Timestamp.valueOf(now));    // createdAt
	        pst.setTimestamp(9, Timestamp.valueOf(now));    // updatedAt
	        pst.setString(10, "ACTIVE");                    // entityState
	        
	        pst.executeUpdate();
	    }
	    return idInvoice;
	}

  /*
   * Set the invoice number field in work order table to the invoice number
   * generated
   */
  private void linkWorkordersToInvoice(String invoiceId,
          List<String> workOrderIDs) throws SQLException {
      Connection connection = Jdbc.getCurrentConnection();

      try (PreparedStatement pst = connection
              .prepareStatement(Queries.getSQLSentence("TWORKORDERS_UPDATE_INVOICEID"))) {
          for (String id : workOrderIDs) {
              pst.setString(1, invoiceId);
              pst.setString(2, id);
              pst.executeUpdate();
          }
      }
  }

  /*
   * Sets status to INVOICED for every workorder
   */
  private void markWorkOrderAsInvoiced(List<String> ids) throws SQLException {
      Connection connection = Jdbc.getCurrentConnection();

      try (PreparedStatement pst = connection
              .prepareStatement(Queries.getSQLSentence("TWORKORDERS_UPDATE_STATE"))) {
          for (String id : ids) {
              pst.setString(1, id);
              pst.executeUpdate();
          }
          
      }
  }

  

}
