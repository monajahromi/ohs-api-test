package com.vgcslabs.ohs.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/job")
public class JobLaunchController {


	private final JobLauncher jobLauncher;
	private final Job job;

	public JobLaunchController(JobLauncher jobLauncher,	@Qualifier("orderIntegrationJob") Job job) {
		this.jobLauncher = jobLauncher;
		this.job = job;
	}

	@GetMapping("/order")
	public void handle() throws Exception {
		System.out.println("hrerererere");
		JobParameters jobParameters = new JobParametersBuilder().addString("param", LocalDateTime.now().toString()).toJobParameters();
		jobLauncher.run(job, jobParameters);
		
	}

}
