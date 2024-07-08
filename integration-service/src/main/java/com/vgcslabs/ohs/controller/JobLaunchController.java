package com.vgcslabs.ohs.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class JobLaunchController {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	@Qualifier("orderIntegrationJob")
	private Job job;
	
	@GetMapping("/launchJob/{id}")
	public void handle(@PathVariable("id") String id) throws Exception {
		
		JobParameters jobParameters = new JobParametersBuilder().addString("param", LocalDateTime.now().toString()).toJobParameters();
		jobLauncher.run(job, jobParameters);
		
	}
}
