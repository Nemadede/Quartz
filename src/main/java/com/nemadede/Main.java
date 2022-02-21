package com.nemadede;

import org.quartz.*;

public class Main {
    public static void main(String[] args) {
        QuartzScheduler quartzScheduler = new QuartzScheduler();
        try {
            quartzScheduler.fireJob(1);
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
