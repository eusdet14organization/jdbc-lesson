package com.beck.dao;

import com.beck.config.ConnectionManager;
import com.beck.entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClientDao implements Dao<Integer, Client> {

    private static final ClientDao CLIENT_DAO = new ClientDao();

    private static final String FIND_BY_ID = """
            SELECT * 
            FROM clients
            WHERE client_id = ?
            """;

    private ClientDao(){

    }
    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public void save(Client entity) {

    }

    @Override
    public void update(Client entity) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Optional<Client> findById(Integer id) {
        try (Connection connection = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Client client = null;
            while (resultSet.next()){
                client = new Client(
                        resultSet.getObject(1,Integer.class),
                        resultSet.getObject(2,String.class),
                        resultSet.getObject(3,String.class),
                        resultSet.getObject(4,String.class),
                        resultSet.getObject(5,String.class),
                        resultSet.getObject(6,String.class));
            }
            return Optional.ofNullable(client);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static ClientDao getClientDao() {
        return CLIENT_DAO;
    }
}
