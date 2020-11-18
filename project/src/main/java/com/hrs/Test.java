package com.hrs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;

public class Test {
    Logger log = LogManager.getFormatterLogger();

    public static void main(String[] args) throws InterruptedException {
        String readingsFile = args.length >= 1 ? args[0] : null;
        String rulesFiles = args.length >= 2 ? args[1] : "rules.json";
        new Test(rulesFiles, readingsFile);
        Thread.sleep(5);
    }

    public Test(String rulesFile, String readingsFile) {
        log.info("Hello world");
        // Read resource source file for rules
        // Read resource source file for values
        // See which values trigger the rules
        // Report the findings to another server
        //     (don't need to make the actual http request but code should lead up until that final moment)
        // Write findings as json file to /code/results.json
    }
}
