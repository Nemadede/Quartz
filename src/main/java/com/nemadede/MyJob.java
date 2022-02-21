package com.nemadede;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job {
    private static int count;
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("---------------------------------------");
        System.out.println("MyJob start: "+ jobExecutionContext.getFireTime());
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        System.out.println("Example name is: " + jobDetail.getJobDataMap().getString("example"));
        System.out.println("MyJob end: " + jobExecutionContext.getJobRunTime() + ", key: " + jobDetail.getKey());
        System.out.println("MyJob next scheduled time: " + jobExecutionContext.getNextFireTime());
        System.out.println("--------------------------------------------------------------------");

        ILatch latch = (ILatch) jobDetail.getJobDataMap().get("latch");
        latch.countDown();
        count++;
        System.out.println("Job count " + count);
        if (count == 2) {
            throw new RuntimeException("Some RuntimeException!");
        }
        if (count == 4) {
            throw new JobExecutionException("Some JobExecutionException!");
        }
    }
}
