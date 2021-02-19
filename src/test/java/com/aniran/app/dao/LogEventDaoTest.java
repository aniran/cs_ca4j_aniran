package com.aniran.app.dao;

import com.aniran.app.entity.LogEvent;
import com.aniran.app.util.HSQLDB;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import static com.aniran.app.entity.EventState.FINISHED;

public class LogEventDaoTest extends TestCase {
    LogEvent logEvent;
    HSQLDB conDB;

    public void setUp() {
         conDB = new HSQLDB();
    }

    public void testSaveLogEvent() {
        LogEventDao logEventDao = new LogEventDao();
        String id = "abcdefhj";
        logEvent = new LogEvent(id, FINISHED, 498820L, "machine-b", null);

        logEventDao.saveLogEvent(logEvent);
        LogEvent expected = logEventDao.getLogEvent(id);

        Assert.assertEquals(expected.getId(), id);
    }

    public void tearDown() {
        conDB.stop();
    }
}