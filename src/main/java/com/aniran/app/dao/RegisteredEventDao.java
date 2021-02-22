package com.aniran.app.dao;

import com.aniran.app.entity.RegisteredEvent;
import com.aniran.app.db.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RegisteredEventDao {

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
            e.printStackTrace();
        }
    }
}
