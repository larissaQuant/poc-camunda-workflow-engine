package com.poc.workflow;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication("workflow")
public class CamundaWorkflowApplication {

  public static void main(String... args) {
    SpringApplication.run(CamundaWorkflowApplication.class, args);
  }

}