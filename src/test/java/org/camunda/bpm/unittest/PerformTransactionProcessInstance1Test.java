package org.camunda.bpm.unittest;


import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.processInstanceQuery;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;
import static org.camunda.bpm.model.xml.test.assertions.ModelAssertions.assertThat;

public class PerformTransactionProcessInstance1Test {

    @RegisterExtension
    ProcessEngineExtension extension = ProcessEngineExtension.builder()
            .build();

    @Test
    @Deployment(resources = {"PerformTransaction.bpmn"})
    public void shouldExecuteProcess() {
        // Given we create a new process instance
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("performTransaction");
        // Then it should be active
        BpmnAwareTests.assertThat(processInstance).isActive();
        // And it should be the only instance
        Assertions.assertThat(processInstanceQuery().count()).isEqualTo(1);
        // And there should exist just a single task within that process instance
        BpmnAwareTests.assertThat(task(processInstance)).isNotNull();

        // When we complete that task
        complete(task(processInstance));
        // Then the process instance should be ended
        BpmnAwareTests.assertThat(processInstance).isEnded();
    }

}
