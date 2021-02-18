package com.aniran.app;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class ConnectDatabase {
    private static Connection con;
//    private static Formatter stringFmt = new Formatter(new StringBuilder());
    private static final String insertQry = "INSERT INTO log_event " +
            "(id, timestamp_start, timestamp_finish, duration, host, type) " +
            "values (%s)";

    static {
        try {
            // Starting HSQLDB instance
            HsqlProperties dbProperties = new HsqlProperties();
            dbProperties.setProperty("server.database.0","file:/opt/db/accounts");
            dbProperties.setProperty("server.dbname.0","an_alias");
            // set up the rest of properties

            // alternative to the above is
            Server server = new Server();
            server.setProperties(dbProperties);
            server.setLogWriter(null); // can use custom writer
            server.setErrWriter(null); // can use custom writer
            server.start();
            //Registering the HSQLDB JDBC driver
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            //Creating the connection with HSQLDB
            ConnectDatabase.con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9001/log_events", "SA", "");

            if (ConnectDatabase.con != null){
                System.out.println("Connection created successfully");
            }else{
                System.out.println("Problem with creating connection");
            }

        }  catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public static int insertLogEvent(LogEvent logEvent){
        int affectedRows = 0;

        try {
            Statement statement = ConnectDatabase.con.createStatement();
            String qryInsert = String.format(insertQry, logEvent.getStringSQLinsertValues());
            affectedRows = statement.executeUpdate(qryInsert);
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Event " + logEvent.getId() + " already exists in DB.");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return affectedRows;
    }
}