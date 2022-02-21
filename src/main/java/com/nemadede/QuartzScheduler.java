package com.nemadede;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.CountDownLatch;

public class QuartzScheduler  implements ILatch{


    private int repeatCount = 3;
    private CountDownLatch latch = new CountDownLatch(repeatCount + 1);

    public void fireJob(int val) throws SchedulerException, InterruptedException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();

        //define the job and tie it to our MyJob class
        JobBuilder jobBuilder = JobBuilder.newJob(TestJob.class);

        JobDataMap data = new JobDataMap();
        data.put("test", "Job Data");

        JobDetail jobDetail = jobBuilder.usingJobData("example", "com.nemadede.QuartzScheduler")
                .usingJobData(data)
                .withIdentity("myJob","group1")
                .build();

        // Trigger the job to run now and then ever
        Trigger trigger =
                TriggerBuilder.newTrigger().withIdentity("myTrigger").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10))
                        .build();

        // Tell the quartz to schedule the job using trigger
        scheduler.scheduleJob(jobDetail,trigger);
        latch.await();
        System.out.println("All Triggers executed.  Shutdown scheduler");
        scheduler.shutdown();

    }

    public void countDown(){
        latch.countDown();
    }

}
