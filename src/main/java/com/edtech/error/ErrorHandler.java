package com.edtech.error;

import com.edtech.util.Logger;

public class ErrorHandler {
    private final Logger logger = Logger.getInstance();
    private final TransientErrorStrategy strategy = new TransientErrorStrategy();

    public void handleTransientError(Exception e) {
        logger.logError("Exception: " + e.getMessage());
        strategy.handle(e);
    }
}