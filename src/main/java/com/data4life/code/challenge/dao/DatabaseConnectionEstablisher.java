package com.data4life.code.challenge.dao;

import com.data4life.code.challenge.constant.DaoConstants;

import java.sql.*;
import java.util.Objects;

public class DatabaseConnectionEstablisher {

    private Connection connection = null;

    /**
     * register JDBC connection and create database connection
     * @return Connection
     */
    private Connection getConnection() {
        if (Objects.nonNull(connection)) {
            return connection;
        }
        try {
            //Register JDBC driver
//            Class.forName(DaoConstants.JDBC_DRIVER);

            //Open a connection
            System.out.println("Connecting to a selected database...");
            connection = DriverManager.getConnection(DaoConstants.DB_URL, DaoConstants.USER, DaoConstants.PASS);
            System.out.println("Connected database successfully...");

        } catch (//ClassNotFoundException |
            SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private Integer executeUpdateStatement(String query){
        Connection connection = getConnection();

        Statement stmt = null;
        int queryExecuted = 0;
        try {
            stmt = connection.createStatement();
            queryExecuted =  stmt.executeUpdate(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try{
                if (Objects.nonNull(stmt))
                    stmt.close();
                if (Objects.nonNull(connection))
                    connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return queryExecuted;
    }

    /**
     * create token table
     */
    public void createTokenTable(){
        System.out.println("Creating table in given database...");
        executeUpdateStatement(DaoConstants.CREATE_TOKEN_TABLE);
        System.out.println("Operation for table creation is completed");
    }


    public void saveTokens(){
        System.out.println("Inserting tokens in the table...");
        int rowsInserted = executeUpdateStatement(DaoConstants.SAVE_TOKENS);
        System.out.println("Operation for insertion of data is completed and " +rowsInserted + " rows inserted");
    }

}
