package org.camunda.bpm.unittest;


import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.camunda.bpm.model.xml.test.assertions.ModelAssertions.assertThat;

@ExtendWith(ProcessEngineExtension.class)
public class PerformTransactionProcessInstanceCamundaExampleTest {

    @RegisterExtension //Extension not working, since it's for Junit5 with camunda 7.20
    ProcessEngineExtension extension = ProcessEngineExtension.builder()
            .configurationResource("camunda.cfg.xml")
            .build();

    @Test
    @Deployment(resources = "PerformTransaction.bpmn")
    public void extensionUsageExample() {
        RuntimeService runtimeService = extension.getRuntimeService();
        runtimeService.startProcessInstanceByKey("performTransaction");

        TaskService taskService = extension.getTaskService();
        Task task = taskService.createTaskQuery().singleResult();
        assertThat(task.getName()).isEqualTo("Prepare Transaction");

        taskService.complete(task.getId());
        assertThat(runtimeService.createProcessInstanceQuery().count()).isEqualTo(0);
    }

}
