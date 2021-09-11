package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail job = newJob(Rabbit.class).build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(getInterval())
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
        }
    }

    public static int getInterval() {
        int result = 0;
        try(BufferedReader reader = new BufferedReader(
                new FileReader(
                        "C:\\projects\\job4j_grabber\\src\\main\\resources\\rabbit.properties"
                )
        )) {
            String str = reader.readLine();
            String[] subStr = str.split("=");
            result = Integer.parseInt(subStr[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result < 0) {
            throw new IllegalArgumentException("Некорректный интервал запуска");
        }
        return result;
    }
}