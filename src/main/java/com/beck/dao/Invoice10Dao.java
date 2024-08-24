package com.beck.dao;

import com.beck.config.ConnectionManager;
import com.beck.entity.Client;
import com.beck.entity.Invoice;
import com.beck.entity.Invoice10;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Invoice10Dao implements Dao<Integer, Invoice10> {

    private static final Invoice10Dao INSTANCE = new Invoice10Dao();
    private static final ClientDao clientDao = ClientDao.getClientDao();

    private static final String FIND_BY_ID = """
            SELECT * 
            FROM invoices
            WHERE invoice_id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT * 
            FROM invoices; 
            """;


    private Invoice10Dao(){

    }
    @Override
    public List<Invoice10> findAll() {
        try (Connection connection = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Invoice10 invoice = null;
            List<Invoice10> invoices = new ArrayList<>();
            while (resultSet.next()){
                invoice = new Invoice10(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        clientDao.findById(resultSet.getInt("client_id")).orElse(null),
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
    public void save(Invoice10 entity) {

    }

    @Override
    public void update(Invoice10 entity) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Optional<Invoice10> findById(Integer id) {
        try (Connection connection = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Invoice10 invoice10 = null;
            Client client = null;
            while (resultSet.next()){
                client = new Client(
                        resultSet.getObject(1,Integer.class),
                        resultSet.getObject(2,String.class),
                        resultSet.getObject(3,String.class),
                        resultSet.getObject(4,String.class),
                        resultSet.getObject(5,String.class),
                        resultSet.getObject(6,String.class)

                );
                invoice10 = new Invoice10(
                        resultSet.getObject(1,Integer.class),
                        resultSet.getObject(2,String.class),
                        clientDao.findById(resultSet.getInt("client_id")).orElse(null),
                        resultSet.getObject(4, BigDecimal.class),
                        resultSet.getObject(5, BigDecimal.class),
                        resultSet.getObject(6, LocalDate.class),
                        resultSet.getObject(7, LocalDate.class),
                        resultSet.getObject(8, LocalDate.class)

                );

            }
            return Optional.ofNullable(invoice10);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Invoice10Dao getInstance() {
        return INSTANCE;
    }
}
