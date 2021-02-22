package com.aniran.app;

import com.aniran.app.dao.RegisteredEventDao;
import com.aniran.app.entity.EventState;
import com.aniran.app.entity.LogEvent;
import com.aniran.app.entity.RegisteredEvent;
import com.aniran.app.entity.RegisteredEventFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileProcessor {
    private static final Logger logger = LoggerFactory.getLogger(FileProcessor.class);
    private File entryFile;
    private RegisteredEventDao registeredEventDao = new RegisteredEventDao();
    private Map<String, LogEvent> mapLogEvents = new HashMap<>();

    public FileProcessor(File entryFile){
        this.entryFile = entryFile;
    }
    
    public void process() {
        logger.debug("Reading file: " + entryFile.toString());

        try (Scanner myReader = new Scanner(entryFile)){
            readLogEntryAndRecord(myReader);
        } catch (FileNotFoundException e) {
            logger.error(e.toString());
        }
        warnSingleEventsLeft();
    }

    private void readLogEntryAndRecord(Scanner myReader) {
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();

            LogEvent logEvent = createLogEvent(data);
            String id = logEvent.getId();

            if (mapLogEvents.containsKey(id)) {
                LogEvent firstLogEvent = mapLogEvents.get(id);
                RegisteredEvent registeredEvent = RegisteredEventFactory.create(firstLogEvent, logEvent);
                mapLogEvents.remove(id);
                saveIfExists(registeredEvent);
            } else {
                 mapLogEvents.put(id, logEvent);
            }
        }
    }

    private void saveIfExists(RegisteredEvent registeredEvent) {
        if (registeredEvent != null){
            registeredEventDao.saveLogEvent(registeredEvent);
        }
    }

    private LogEvent createLogEvent(String logEntry){
        JSONObject obj = new JSONObject(logEntry);

        String id = obj.getString("id");
        EventState eventState = EventState.valueOf(obj.getString("state"));
        long timestamp = obj.getLong("timestamp");
        String host = obj.has("host") ? obj.getString("host") : null;
        String type = obj.has("type") ? obj.getString("type") : null;

        return new LogEvent(id, eventState, timestamp, host, type);
    }

    private void warnSingleEventsLeft() {
        for (LogEvent logEvent : mapLogEvents.values()) {
            logger.warn("No correspondent pair match found for Id: " + logEvent.getId());
        }
    }
}
