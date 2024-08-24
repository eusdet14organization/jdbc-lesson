package com.beck;

import com.beck.config.ConnectionManager;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String CREATE_SQL = """
   
            CREATE TABLE jdbc1
            ( 
            id int auto_increment primary key,
            info text
            )
            """;

    private static final String INSERT_SQL = """
            INSERT INTO jdbc1(info) 
            VALUES ('test1'),
                   ('test2'),
                   ('test3')
            """;

    private static final String UPDATE_SQL = """
            UPDATE jdbc1
            set info = 'test33'
            where id = 3;
            """;

    private static final String DELETE_SQL = """
            DELETE FROM jdbc1
            WHERE id = 3;
            """;

    private static final String SELECT_SQL = """
            SELECT id,
                   info
            FROM jdbc1;
            """;




    public static void main(String[] args) {

//        try (Connection connection = ConnectionManager.openConnection()) {
//            Statement statement = connection.createStatement();
//            boolean execute = statement.execute(CREATE_SQL);
//            System.out.println(execute);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        try (Connection connection = ConnectionManager.openConnection()) {
//            Statement statement = connection.createStatement();
//            boolean execute = statement.execute(CREATE_SQL);
//            System.out.println(execute);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        try (Connection connection = ConnectionManager.openConnection()) {
//            Statement statement = connection.createStatement();
//            int update = statement.executeUpdate(INSERT_SQL);
//            System.out.println(update);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        try (Connection connection = ConnectionManager.openConnection()) {
//            Statement statement = connection.createStatement();
//            int update = statement.executeUpdate(UPDATE_SQL);
//            System.out.println(update);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        try (Connection connection = ConnectionManager.openConnection()) {
//            Statement statement = connection.createStatement();
//            int update = statement.executeUpdate(DELETE_SQL);
//            System.out.println(update);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }



//        try (Connection connection = ConnectionManager.openConnection()) {
//            Statement statement = connection.createStatement();
//            DatabaseMetaData metaData = connection.getMetaData();
//
//            ResultSet resultSet =  statement.executeQuery(SELECT_SQL);
//            while (resultSet.next()){
//                int anInt = resultSet.getObject("id",Integer.class);
//                String string = resultSet.getObject("info", String.class);
//                System.out.println(anInt + " " + string );
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


        sqlInjectionMethod();



    }

    static void sqlInjectionMethod(){
        String sql_injection = """
                select invoice_date
                from invoices
                where invoice_date = '2019-03-09' or 1 = 1;
                """;

        try (Connection connection = ConnectionManager.openConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql_injection);

            while (resultSet.next()){
                LocalDate invoiceDate = resultSet.getDate("invoice_date").toLocalDate();
                System.out.println(invoiceDate);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}