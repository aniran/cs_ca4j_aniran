package com.aniran.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
import com.aniran.app.dao.LogEventDao;
import com.aniran.app.entity.LogEvent;
import com.aniran.app.entity.EventState;
import com.aniran.app.util.HSQLDB;
import org.json.JSONObject;

/**
 * Main Application
 *
 */
public class App {
//    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main( String[] args ) {
        HSQLDB conDB = new HSQLDB();
        LogEventDao logEventDao = new LogEventDao();
        Map<String, LogEvent> mapLogEvents = new HashMap<>();

        try {
            File myObj = new File(System.getProperty("user.dir")+"/logfile.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                JSONObject obj = new JSONObject(data);
                String id = obj.getString("id");
                EventState eventState = EventState.valueOf(obj.getString("state"));
                long timestamp = obj.getLong("timestamp");
                String host = obj.has("host") ? obj.getString("host") : null;
                String type = obj.has("type") ? obj.getString("type") : null;

                if (mapLogEvents.containsKey(id)) {
                    LogEvent logEvent = mapLogEvents.get(id);
                    logEvent.updateTimestamp(eventState, timestamp);

                    if (logEvent.getDuration() != null){
                        logEventDao.saveLogEvent(logEvent);
                        mapLogEvents.remove(id);
                    }
                } else {
                    LogEvent logEvent = new LogEvent(id, eventState, timestamp, host, type);
                    mapLogEvents.put(id, logEvent);
                }
            }

            myReader.close();
            List<LogEvent> listLogEvents = new ArrayList<>(mapLogEvents.values());

            for (LogEvent logEvent: listLogEvents) {
                logEventDao.saveLogEvent(logEvent);
            }
            conDB.stop();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
