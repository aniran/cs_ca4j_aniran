package com.aniran.app.db;

import com.aniran.app.entity.RegisteredEvent;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public final class HibernateUtil {

    private HibernateUtil() {
    }

    private static SessionFactory sessionFactory;

    private static Properties getConnectionProperties() {
        Properties settings = new Properties();

        settings.put(Environment.DRIVER, "org.hsqldb.jdbc.JDBCDriver");
        settings.put(Environment.URL, "jdbc:hsqldb:file:logevents.db");
        settings.put(Environment.USER, "SA");
        settings.put(Environment.PASS, "SA");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.HSQLDialect");
        settings.put(Environment.SHOW_SQL, "false");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "create");
        return settings;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties settings = getConnectionProperties();

                Configuration configuration = new Configuration();

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(RegisteredEvent.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}