package com.charter.jobScheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(fixedRate = 2000)
    public void scheduleTaskWithFixedRate() {
        logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
    }

    @Scheduled(fixedDelay = 2000)
    public void scheduleTaskWithFixedDelay() {
        logger.info("Fixed Delay Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            logger.error("Ran into an error {}", ex);
            throw new IllegalStateException(ex);
        }
    }

    @Scheduled(fixedRate = 2000, initialDelay = 5000)
    public void scheduleTaskWithInitialDelay() {
        logger.info("Fixed Rate Task with Initial Delay :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    }

    @Scheduled(cron = "0/15 * * * * ?")
    public void scheduleTaskWithCronExpression() {
        logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    }

    @Scheduled(cron = "0/15 * * * * ?")
    public void createFileWithTimestamp(){
        BufferedWriter  writer;
        String filename = "xyz";
        //get the time stamp
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        //remove the seconds part
        timestamp = timestamp.substring(0, timestamp.length()-6).replaceAll(":","").replaceAll(" ", "_");
        //timestamp = timestamp.replaceAll(":","");
        //append file name to the time stamp
        String filestamp = filename +"_"+ timestamp;

        try {
            writer = new BufferedWriter( new FileWriter(filestamp + ".txt"));
            writer.write("First line");
            writer.newLine();
            writer.write("Second line");
            writer.close();
            logger.info(filestamp + ".txt AA Letter is generated at - {}", dateTimeFormatter.format(LocalDateTime.now()));
        } catch (IOException e) {
             e.printStackTrace();
        }
    }
}
