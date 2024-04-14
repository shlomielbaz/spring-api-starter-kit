package com.shlomielbaz;

import com.shlomielbaz.crons.*;
import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.quartz.impl.StdSchedulerFactory;

import java.io.Closeable;
import java.io.IOException;

@SpringBootApplication
public class Main implements Closeable {
	private static Scheduler scheduler1;
	private static Scheduler scheduler2;
	private static Scheduler scheduler3;
	private static Scheduler scheduler4;
	private static Scheduler scheduler5;
	public static void main(String[] args) {
//		List<Integer> listOfNumbers = Arrays.asList(1, 2, 3, 4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
//		listOfNumbers.stream().forEach(number ->
//				System.out.println(number + " " + Thread.currentThread().getName())
//		);
//		listOfNumbers.parallelStream().forEach(number ->
//				System.out.println(number + " " + Thread.currentThread().getName())
//		);
		try {
			JobDetail job1 = JobBuilder.newJob(Job1.class)
					.withIdentity("job1", "group1").build();

			Trigger trigger1 = TriggerBuilder.newTrigger()
					.withIdentity("cronTrigger1", "group1")
					.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
					.build();

			scheduler1 = new StdSchedulerFactory().getScheduler();
			scheduler1.start();
			scheduler1.scheduleJob(job1, trigger1);

			JobDetail job2 = JobBuilder.newJob(Job2.class)
					.withIdentity("job2", "group2").build();

			Trigger trigger2 = TriggerBuilder.newTrigger()
					.withIdentity("cronTrigger2", "group2")
					.withSchedule(CronScheduleBuilder.cronSchedule(new CronExpression("0/7 * * * * ?")))
					.build();

			 scheduler2 = new StdSchedulerFactory().getScheduler();
			scheduler2.start();
			scheduler2.scheduleJob(job2, trigger2);
			JobDetail job3 = JobBuilder.newJob(Job3.class)
					.withIdentity("job3", "group3").build();

			Trigger trigger3 = TriggerBuilder.newTrigger()
					.withIdentity("cronTrigger3", "group3")
					.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(13, 46))
					.build();

			 scheduler3 = new StdSchedulerFactory().getScheduler();
			scheduler3.start();
			scheduler3.scheduleJob(job3, trigger3);
			JobDetail job4 = JobBuilder.newJob(Job4.class)
					.withIdentity("job4", "group4").build();

			Trigger trigger4 = TriggerBuilder.newTrigger()
					.withIdentity("cronTrigger4", "group4")
					.withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(3, 13, 46))
					.build();

			 scheduler4 = new StdSchedulerFactory().getScheduler();
			scheduler4.start();
			scheduler4.scheduleJob(job4, trigger4);
			JobDetail job5 = JobBuilder.newJob(Job5.class)
					.withIdentity("job5", "group5").build();

			Trigger trigger5 = TriggerBuilder
					.newTrigger().withIdentity("cronTrigger5", "group5")
					.withSchedule(CronScheduleBuilder.monthlyOnDayAndHourAndMinute(28, 13, 46))
					.build();

			 scheduler5 = new StdSchedulerFactory().getScheduler();
			scheduler5.start();
			scheduler5.scheduleJob(job5, trigger5);
			//Thread.sleep(100000);

		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void close() throws IOException {
        try {
			System.out.println("Closing resources");
            scheduler1.shutdown();
			scheduler2.shutdown();
			scheduler3.shutdown();
			scheduler4.shutdown();
			scheduler5.shutdown();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
	}
}
