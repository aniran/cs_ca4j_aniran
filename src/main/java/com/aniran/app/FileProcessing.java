package com.aniran.app;

import com.aniran.app.controller.RegisteredEventController;
import com.aniran.app.dao.RegisteredEventDao;
import com.aniran.app.entity.EventState;
import com.aniran.app.entity.RegisteredEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileProcessing {

    public static void process(File logFile) {
        RegisteredEventDao registeredEventDao = new RegisteredEventDao();
        Map<String, RegisteredEvent> mapLogEvents = new HashMap<>();

        try {
            Scanner myReader = new Scanner(logFile);

            System.out.println("Reading " + logFile.toString());

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                JSONObject obj = new JSONObject(data);

                String id = obj.getString("id");
                EventState eventState = EventState.valueOf(obj.getString("state"));
                long timestamp = obj.getLong("timestamp");
                String host = obj.has("host") ? obj.getString("host") : null;
                String type = obj.has("type") ? obj.getString("type") : null;

                //
                if (mapLogEvents.containsKey(id)) {
                    RegisteredEvent registeredEvent = mapLogEvents.get(id);
                    RegisteredEventController.updateTimestamp(registeredEvent, eventState, timestamp);

                    // If logEvent.duration is available it means we can persist and remove from mapLogEvents
                    if (registeredEvent.getDuration() != null){
                        registeredEventDao.saveLogEvent(registeredEvent);
                        mapLogEvents.remove(id);
                    }
                } else {
                    RegisteredEvent registeredEvent = RegisteredEventController.createRegisteredEvent(id, eventState, timestamp, host, type);
                    mapLogEvents.put(id, registeredEvent);
                }
            }
            myReader.close();
            List<RegisteredEvent> listRegisteredEvents = new ArrayList<>(mapLogEvents.values());

            for (RegisteredEvent registeredEvent : listRegisteredEvents) {
                registeredEventDao.saveLogEvent(registeredEvent);
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
