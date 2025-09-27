package com.edtech.util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {
    private static final Logger instance = new Logger();
    private static final String LOG_FILE = "src/resources/log.txt";

    private Logger() {}

    public static Logger getInstance() { return instance; }

    public void logInfo(String message) { log("INFO", message); }
    public void logWarn(String message) { log("WARN", message); }
    public void logError(String message) { log("ERROR", message); }

    private void log(String level, String message) {
        String logEntry = String.format("%s [%s]: %s", LocalDateTime.now(), level, message);
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(logEntry + "\n");
        } catch (IOException e) {
            System.out.println("Logging failed: " + e.getMessage());
        }
    }
}