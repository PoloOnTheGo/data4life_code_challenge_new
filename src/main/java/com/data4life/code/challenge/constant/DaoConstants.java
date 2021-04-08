package com.data4life.code.challenge.constant;

public interface DaoConstants {
    //TODO Can be optimised by moving the below details to a properties file and also encrypt the password

    //JDBC driver name and database URL
    //   String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    String DB_URL ="jdbc:mysql://localhost:3306/D4L_APP";

    //  Database credentials
    static final String USER = "username";
    static final String PASS = "password";


    //SQLs
    // TODO CREATED SCHEMA via mySQL
    // CREATE DATABASE D4L_APP;
    // USE D4L_APP;
    
    // not using index/primary key, so that it is faster to insert into the table
    String CREATE_TOKEN_TABLE = "CREATE TABLE D4L_APP.TOKEN (token_value TINYTEXT NOT NULL);";

    /* LOAD DATA INFILE is the fastest way to insert huge data into DB
    *  REPLACE will handle the duplicate records
    *  LINES TERMINATED BY  handles the number of row by specifying the token delimiter
    *  uses file from secure location
    */
    String SAVE_TOKENS = "LOAD DATA INFILE '"+FileConstants.SECURE_LOCATION+"/"+FileConstants.FILE_NAME+"' " +
            "REPLACE INTO TABLE D4L_APP.TOKEN LINES TERMINATED BY '\\n';";
}
