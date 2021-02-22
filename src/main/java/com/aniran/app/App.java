package com.aniran.app;

import com.aniran.app.db.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static File evalArgumentLogFile(String[] args) {
        File entryFile = null;
        if (args.length > 0 && args[0] != null) {
            entryFile = new File(args[0]);

            if (!entryFile.exists()) {
                entryFile = new File(System.getProperty("user.dir") + args[0]);
            }
        }

        if (entryFile == null || !entryFile.exists()) {
            entryFile = new File(System.getProperty("user.dir") + "/logfile.txt");
        }
        return entryFile;
    }

    public static void main(String[] args) {
        logger.info("App Starting");
        try {
            File logFile = evalArgumentLogFile(args);
            new FileProcessor(logFile).process();
        } catch (Exception e) {
            logger.error(e.toString());
        } finally {
            HibernateUtil.getSessionFactory().close();
        }
    }
}
