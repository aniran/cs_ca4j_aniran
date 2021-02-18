package com.aniran.app.entity;

import org.junit.*;

import static com.aniran.app.entity.EventState.*;

public class LogEventTest {
    LogEvent logEvent;

    @Test
    public void testUpdateTimestamp() {
        logEvent = new LogEvent("fosidjf", STARTED, 498320L, "machine-a", "APPLICATION");

        logEvent.updateTimestamp(FINISHED, 498333L);

        Assert.assertEquals(logEvent.getDuration().longValue(), 13L);
    }
}