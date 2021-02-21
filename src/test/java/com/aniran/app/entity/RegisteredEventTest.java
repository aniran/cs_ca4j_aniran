package com.aniran.app.entity;

import com.aniran.app.controller.RegisteredEventController;
import org.junit.*;

import static com.aniran.app.entity.EventState.*;

public class RegisteredEventTest {
    RegisteredEvent registeredEvent;

    @Test
    public void testUpdateTimestamp() {
        registeredEvent = RegisteredEventController.createRegisteredEvent("fosidjf", STARTED, 498320L, "machine-a", "APPLICATION");

        RegisteredEventController.updateTimestamp(registeredEvent, FINISHED, 498333L);

        Assert.assertEquals(registeredEvent.getDuration().longValue(), 13L);
    }
}