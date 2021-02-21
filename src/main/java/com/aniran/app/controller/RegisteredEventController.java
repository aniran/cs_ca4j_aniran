package com.aniran.app.controller;

import com.aniran.app.entity.EventState;
import com.aniran.app.entity.RegisteredEvent;

public class RegisteredEventController {
    private static final int ALERT_THRESHOLD = 4;

    public static RegisteredEvent updateTimestamp(RegisteredEvent registeredEvent, EventState newEventState, long newTimestamp){
        switch (newEventState) {
            case STARTED:
                registeredEvent.setTimestampStart(newTimestamp);
                break;
            case FINISHED:
                registeredEvent.setTimestampFinish(newTimestamp);
                break;
        }
        Long start = registeredEvent.getTimestampStart();
        Long finish= registeredEvent.getTimestampFinish();
        Long duration = registeredEvent.getDuration();

        // If we have timestamps for start and finish we can update duration and alert
        if ( start != null && finish != null){
            registeredEvent.setDuration(finish - start);
            registeredEvent.setAlert(duration > ALERT_THRESHOLD);
        }

        return registeredEvent;
    }

    public static RegisteredEvent createRegisteredEvent(String id, EventState eventState, Long timestamp, String host, String type){
        RegisteredEvent registeredEvent = new RegisteredEvent();
        registeredEvent.setId(id);
        registeredEvent.setHost(host);
        registeredEvent.setType(type);

        return updateTimestamp(registeredEvent, eventState, timestamp);
    }
}
