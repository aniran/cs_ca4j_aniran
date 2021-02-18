package com.aniran.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main( String[] args ) {
        Map<String, LogEvent> mapLogEvents = new HashMap<>();
        Connection con = ConnectDatabase.newConnection();

        try {
            File myObj = new File(System.getProperty("user.dir")+"/logfile.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                JSONObject obj = new JSONObject(data);
                String id = obj.getString("id");
                State state = State.valueOf(obj.getString("state"));
                long timestamp = obj.getLong("timestamp");
                String host = obj.has("host") ? obj.getString("host") : null;
                String type = obj.has("type") ? obj.getString("type") : null;

                if (mapLogEvents.containsKey(id)) {
                    mapLogEvents.get(id).updateTimestamp(state, timestamp);
                } else {
                    LogEvent logEvent = new LogEvent(id, state, timestamp, host, type);
                    mapLogEvents.put(id, logEvent);
                }
            }

            myReader.close();
//            List<LogEvent> listLogEvents = mapLogEvents.values().stream().collect(Collectors.toList());
            List<LogEvent> listLogEvents = new ArrayList<>(mapLogEvents.values());

            for (LogEvent event: listLogEvents) {
                ConnectDatabase.insertLogEvent(event);
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}