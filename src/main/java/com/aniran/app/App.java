package com.aniran.app;

import java.io.File;

import com.aniran.app.db.DBServer;

/**
 * Main Application
 *
 */
public class App {
//    private static final Logger logger = LogManager.getLogger(App.class);

    public static File evalArgumentLogFile(String[] args) {
        File logFile = null;
        if (args.length > 0 && args[0] != null) {
            logFile = new File(args[0]);

            // If file does not exist fallback to current dir
            if (! logFile.exists()){
                logFile = new File(System.getProperty("user.dir") + args[0]);
            }
        }

        // Try using default logfile.txt as last resort
        if (logFile == null || ! logFile.exists()){
            logFile = new File(System.getProperty("user.dir")+"/logfile.txt");
        }
        return logFile;
    }

    public static void main( String[] args ) {
        DBServer.start();

        File logFile = evalArgumentLogFile(args);
        FileProcessing.process(logFile);

        DBServer.stop();
    }
}
