package com.shlomielbaz;

import com.shlomielbaz.crons.*;
import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@ComponentScan("com.shlomielbaz")
//@ComponentScan({"com.shlomielbaz.controller"})
//@EntityScan("com.shlomielbaz.entity")
//@EnableJpaRepositories("com.shlomielbaz.repository")
//extends SpringBootServletInitializer
public class Main  implements Closeable {
//	private static Scheduler scheduler1;

	public static void main(String[] args) {

//		List<Integer> list = Arrays.asList(1, 2, 3, 4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
//		list.stream().forEach(number ->
//				System.out.println(number + " " + Thread.currentThread().getName())
//		);
//		System.out.println("------------------------------------------------------------------");
//
//		list.parallelStream().forEach(number ->
//				System.out.println(number + " " + Thread.currentThread().getName())
//		);

//		try {
//			JobDetail job1 = JobBuilder.newJob(Job1.class)
//					.withIdentity("job1", "group1").build();
//
//			Trigger trigger1 = TriggerBuilder.newTrigger()
//					.withIdentity("cronTrigger1", "group1")
//					.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
//					.build();
//
//			scheduler1 = new StdSchedulerFactory().getScheduler();
//			scheduler1.start();
//			scheduler1.scheduleJob(job1, trigger1);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void close() throws IOException {
//        try {
//			System.out.println("Closing resources");
////            scheduler1.shutdown();
//        } catch (SchedulerException e) {
//            throw new RuntimeException(e);
//        }
	}
}
