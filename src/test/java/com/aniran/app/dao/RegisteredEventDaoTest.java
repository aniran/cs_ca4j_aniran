package com.aniran.app.dao;

import com.aniran.app.db.HibernateUtil;
import com.aniran.app.entity.RegisteredEventFactory;
import com.aniran.app.entity.RegisteredEvent;
//import com.aniran.app.db.DBServer;
import junit.framework.TestCase;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.aniran.app.entity.EventState.FINISHED;

public class RegisteredEventDaoTest extends TestCase {
    RegisteredEventDao registeredEventDao;

    public void setUp() {
//        DBServer.start();
        registeredEventDao = new RegisteredEventDao();
    }

    public void testSaveLogEvent() {
   /*     String id = "abcdefhj";
        RegisteredEvent registeredEvent = RegisteredEventFactory.createRegisteredEvent(id, FINISHED, 498820L, "machine-b", null);

        registeredEventDao.saveLogEvent(registeredEvent);
        RegisteredEvent expected = registeredEventDao.getLogEvent(id);

        Assert.assertEquals(expected.getId(), id);*/
    }

    public void tearDown() {
//        DBServer.stop();
    }
}