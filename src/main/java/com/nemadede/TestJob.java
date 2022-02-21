package com.nemadede;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestJob implements Job {
    ExecutorService testExecutor = Executors.newFixedThreadPool(2);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        for (int i = 0; i < 3; i++){
            testExecutor.execute(new TestThread());
            JobDetail jobDetail = jobExecutionContext.getJobDetail();
            System.out.println(jobDetail.getJobDataMap().get("test"));
        }
    }
}
