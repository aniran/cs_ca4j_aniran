package com.aniran.app.entity;

import com.aniran.app.RegisteredEventFactory;
import org.junit.*;

import static com.aniran.app.entity.EventState.*;

public class RegisteredEventTest {
    RegisteredEvent registeredEvent;

    @Test
    public void testUpdateTimestamp() {
        registeredEvent = RegisteredEventFactory.createRegisteredEvent("fosidjf", STARTED, 498320L, "machine-a", "APPLICATION");

        RegisteredEventFactory.updateTimestamp(registeredEvent, FINISHED, 498333L);

        Assert.assertEquals(registeredEvent.getDuration().longValue(), 13L);
    }
}