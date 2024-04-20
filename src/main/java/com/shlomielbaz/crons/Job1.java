package com.shlomielbaz.crons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class Job1 implements Job {
    final static Gson gson = new GsonBuilder().create();
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Job1 --->>> Hello user! Time is " + new Date());
    }
}