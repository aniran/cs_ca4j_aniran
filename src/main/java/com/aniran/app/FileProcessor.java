package com.aniran.app;

import com.aniran.app.dao.RegisteredEventDao;
import com.aniran.app.entity.EventState;
import com.aniran.app.entity.LogEvent;
import com.aniran.app.entity.RegisteredEvent;
import com.aniran.app.entity.RegisteredEventFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileProcessor {
    private static final Logger logger = LoggerFactory.getLogger(FileProcessor.class);
    private FileReader fileReader;
    private RegisteredEventDao registeredEventDao = new RegisteredEventDao();
    private Map<String, LogEvent> mapLogEvents = new HashMap<>();

    public FileProcessor(File file) {
        try {
            this.fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            logger.error(e.toString());
        }
    }
    
    public void process() {
        logger.debug("Reading file: " + fileReader.toString());

        try (BufferedReader myReader = new BufferedReader(fileReader)){
            readLogEntryAndRecord(myReader);
        } catch (FileNotFoundException e) {
            logger.error("File " + fileReader.toString() + " not found" + e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
        }
        warnSingleEventsLeft();
    }

    private void readLogEntryAndRecord(BufferedReader  myReader) {
        String currentLine;
        try{
            while ((currentLine=myReader.readLine()) != null) {
                LogEvent logEvent = createLogEvent(currentLine);
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
        } catch (IOException e) {
            logger.error(e.toString());
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
