package com.aniran.app.db;

import com.aniran.app.entity.RegisteredEvent;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        try {
            Properties settings = new Properties();

            settings.put(Environment.DRIVER, "org.hsqldb.jdbc.JDBCDriver");
            settings.put(Environment.URL, "jdbc:hsqldb:hsql://localhost:9001/log_events");
            settings.put(Environment.USER, "SA");
            settings.put(Environment.PASS, "");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.HSQLDialect");
            settings.put(Environment.SHOW_SQL, "false");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(Environment.HBM2DDL_AUTO, "create");

            Configuration configuration = new Configuration();

            configuration.setProperties(settings);
            configuration.addAnnotatedClass(RegisteredEvent.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
}