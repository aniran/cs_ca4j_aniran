package com.aniran.app.util;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class HSQLDB {
    private Server server;

    public HSQLDB() {
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

            this.server = new Server();
            server.setProperties(dbProperties);
            server.setLogWriter(null); // can use custom writer
            server.setErrWriter(null); // can use custom writer
            server.start();

        }  catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void stop(){
        this.server.stop();
    }
}