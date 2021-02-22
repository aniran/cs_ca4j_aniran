package com.aniran.app.dao;

import com.aniran.app.db.HibernateUtil;
import com.aniran.app.entity.RegisteredEvent;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisteredEventDao {
    private static final Logger logger = LoggerFactory.getLogger(RegisteredEventDao.class);

    public void saveLogEvent(RegisteredEvent registeredEvent) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(registeredEvent);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(e.toString());
        }
    }
}
