package com.aniran.app;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ConnectDatabase {
    private Connection con;
    private Server server;

    public ConnectDatabase() {
        try {
            String propFileName = System.getProperty("user.dir")+"/hsql.server.properties";
            InputStream inputStream = new FileInputStream(propFileName);
            Properties prop = new Properties();

            prop.load(inputStream);

            // Starting HSQLDB instance
            HsqlProperties dbProperties = new HsqlProperties();
            for (Object key: prop.keySet()){
                String val = (String)prop.get(key);
                dbProperties.setProperty((String)key, val);
            }

            this.server = new Server();
            server.setProperties(dbProperties);
            server.setLogWriter(null); // can use custom writer
            server.setErrWriter(null); // can use custom writer
            server.start();

            //Creating the connection with HSQLDB
            this.con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9001/log_events", "SA", "");

            if (this.con != null){
                this.con.createStatement().executeUpdate(
                        "CREATE TABLE IF NOT EXISTS log_event  (\n" +
                        "id VARCHAR(100) NOT NULL,\n" +
                        "timestamp_start BIGINT NULL,\n" +
                        "timestamp_finish BIGINT NULL,\n" +
                        "duration INT NULL,\n" +
                        "host VARCHAR(100) NULL,\n" +
                        "type VARCHAR(100) NULL,\n" +
                        "PRIMARY KEY (id)\n" +
                        ")");
            }else{
                System.out.println("Problem with creating connection");
            }
        }  catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public int insertLogEvent(LogEvent logEvent){
        int affectedRows = 0;
        String insertQry = "INSERT INTO log_event " +
                "(id, timestamp_start, timestamp_finish, duration, host, type) " +
                "values (%s)";
        try {
            Statement statement = this.con.createStatement();
            String qryInsert = String.format(insertQry, logEvent.getStringSQLinsertValues());
            affectedRows = statement.executeUpdate(qryInsert);
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Event " + logEvent.getId() + " already exists in DB.");
        } catch (Exception e) {
//            e.printStackTrace(System.out);
            System.out.println("Other");
        }
        return affectedRows;
    }

    public void stop(){
        this.server.stop();
    }
}