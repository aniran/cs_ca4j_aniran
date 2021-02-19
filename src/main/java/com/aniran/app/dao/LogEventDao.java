package com.aniran.app.dao;

import com.aniran.app.entity.LogEvent;
import com.aniran.app.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LogEventDao {
    public void saveLogEvent(LogEvent logEvent) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(logEvent);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<LogEvent> getLogEvents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from LogEvent", LogEvent.class).list();
        }
    }

    public LogEvent getLogEvent(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.getReference(LogEvent.class, id);
        }
    }
}
