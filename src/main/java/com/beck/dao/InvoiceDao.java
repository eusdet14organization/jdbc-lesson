package com.beck.dao;

import com.beck.config.ConnectionManager;
import com.beck.entity.Invoice;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InvoiceDao implements Dao<Integer, Invoice>{

    private static final InvoiceDao INSTANCE = new InvoiceDao();

    private static final String FIND_ALL_SQL = """
            SELECT * 
            FROM invoices; 
            """;
    

    private static final String SAVE_SQL = """
            INSERT INTO invoices(INVOICE_ID, NUMBER, CLIENT_ID, INVOICE_TOTAL, PAYMENT_TOTAL, INVOICE_DATE, DUE_DATE, PAYMENT_DATE) 
            VALUES(?,?,?,?,?,?,?,?) 
            """;

    private static final String JOIN_SQL = """
           SELECT invoice_id,
                  number,
                  c.name,
                  c.client_id
           from invoices
           join invoicing.clients c on c.client_id = invoices.client_id
           where invoice_id = ?
       
            """;

    private static final String SUBSELECT_SQL = """
            SELECT *
            FROM invoices
            WHERE invoice_total > (
                select invoice_total
                from invoices
                where invoice_id = ?
            )
            order by invoice_date
            """;


    private InvoiceDao(){

    }
    @Override
    public List<Invoice> findAll() {
        try (Connection connection = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Invoice invoice = null;
            List<Invoice> invoices = new ArrayList<>();
            while (resultSet.next()){
                invoice = new Invoice(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getBigDecimal(4),
                        resultSet.getBigDecimal(5),
                        resultSet.getObject(6, LocalDate.class),
                        resultSet.getObject(7, LocalDate.class),
                        resultSet.getObject(8,LocalDate.class)
                );
                invoices.add(invoice);

            }
            return invoices;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void save(Invoice entity) {
        try (Connection connection = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setInt(1,entity.getInvoiceId());
            preparedStatement.setString(2,entity.getNumber());
            preparedStatement.setInt(3,entity.getClientId());
            preparedStatement.setBigDecimal(4,entity.getInvoiceTotal());
            preparedStatement.setBigDecimal(5,entity.getPaymentTotal());
            preparedStatement.setDate(6, Date.valueOf(entity.getInvoiceDate()));
            preparedStatement.setDate(7,Date.valueOf(entity.getDueDate()));
            preparedStatement.setDate(8,Date.valueOf(entity.getPaymentDate()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Invoice entity) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Optional<Invoice> findById(Integer id) {

        return Optional.empty();
    }

    public void joinMethod(int id){
        try (Connection connection = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(JOIN_SQL)) {
            System.out.println(connection.getTransactionIsolation());
            preparedStatement.setInt(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String invoiceId = resultSet.getString("invoice_id");
                String number = resultSet.getString("number");
                String name = resultSet.getString("name");
                int clientId = resultSet.getInt("client_id");
                System.out.println(invoiceId +  "  " + number + " " + name + " " + clientId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void subqueryMethod(int id){
        try (Connection connection = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SUBSELECT_SQL)) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <columnCount ; i++) {
                System.out.printf("%-15s",metaData.getColumnName(i));
            }
            System.out.println();
            while (resultSet.next()){
                for (int i = 1; i <columnCount ; i++) {
                    System.out.printf("%-15s",resultSet.getObject(i));
                }
                System.out.println();
            }
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static InvoiceDao getInstance() {
        return INSTANCE;
    }
}
