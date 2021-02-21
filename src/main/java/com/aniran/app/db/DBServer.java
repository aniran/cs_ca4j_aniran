package com.aniran.app.db;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class DBServer {
    private static Server server;

    public static void start() {
        // If already running, won't start again
        if (server != null && !server.isNotRunning() ) {
            return;
        }
        try {
            String propFileName = System.getProperty("user.dir")+"/server.properties";
            InputStream inputStream = new FileInputStream(propFileName);
            Properties prop = new Properties();

            prop.load(inputStream);

            // Configuring HSQLDB instance with server.properties
            HsqlProperties dbProperties = new HsqlProperties();

            for (Object key: prop.keySet()){
                String val = (String)prop.get(key);
                dbProperties.setProperty((String)key, val);
            }

            server = new Server();
            server.setProperties(dbProperties);
            server.setLogWriter(null); // can use custom writer
            server.setErrWriter(null); // can use custom writer
            server.start();

        }  catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public static void stop(){
        if (server == null || server.isNotRunning() ) {
            return;
        }
        server.stop();
    }
}