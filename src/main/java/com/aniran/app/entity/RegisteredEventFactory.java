package com.aniran.app.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RegisteredEventFactory {
    private static final Logger logger = LoggerFactory.getLogger(RegisteredEventFactory.class);

    private RegisteredEventFactory(){}

    private static final int ALERT_THRESHOLD_IN_MS = 4;

    public static RegisteredEvent create(LogEvent firstEvent, LogEvent secondEvent){
        EventState firstState = firstEvent.getEventState();
        EventState secondState = secondEvent.getEventState();

        if (firstState == secondState){
            logger.warn("Two log events for id (" + firstEvent.getId() + ") have the same STATE = " + firstState.toString());
            return null;
        }

        String registeredEventId = firstEvent.getId();
        Long start = firstEvent.getEventState() == EventState.STARTED ? firstEvent.getTimestamp() : secondEvent.getTimestamp();
        Long finish = secondEvent.getEventState() == EventState.FINISHED ? secondEvent.getTimestamp() : firstEvent.getTimestamp();
        long duration = finish - start;

        if (duration < 0) {
            logger.warn("Event " + firstEvent.getId() + " has duration " + duration);
        }

        Boolean shouldAlert = (duration > ALERT_THRESHOLD_IN_MS);

        if (shouldAlert) {
            logger.warn("Event " + registeredEventId + " duration is over " + ALERT_THRESHOLD_IN_MS + "ms");
        }

        return new RegisteredEvent(
                registeredEventId,
                start,
                finish,
                duration,
                firstEvent.getHost(),
                firstEvent.getType(),
                shouldAlert
        );
    }
}
