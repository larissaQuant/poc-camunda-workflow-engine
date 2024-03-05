package org.camunda.bpm.unittest;

import com.poc.workflow.service.PrepareTransactionDelegate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PerformTransactionProcessTest {

//    @Rule
//    @ClassRule
//    public static TestCoverageProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create()
//            .assertClassCoverageAtLeast(0.5)
//            .build();
//
//    @RegisterExtension
//    ProcessEngineExtension extension = ProcessEngineExtension.builder()
//            .build();
//    @Mock
//    private ProcessScenario performTransaction;
//
//    // provide a field where the process engine gets injected
//    ProcessEngine processEngine;
//
//    //Mocks.register("myBean", new Bean());
//
//
//    @BeforeAll
//    public void setup(){
//        MockitoAnnotations.initMocks(this);
//
//        registerJavaDelegateMock(PrepareTransactionDelegate.class);
//    }
//
//    @Test
//    @Deployment
//    public void extensionUsageExample() {
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        runtimeService.startProcessInstanceByKey("performTransaction");
//
//        TaskService taskService = processEngine.getTaskService();
//        Task task = taskService.createTaskQuery().singleResult();
//        assertThat(task.getName()).isEqualTo("Prepare Transaction");
//
//        taskService.complete(task.getId());
//        assertThat(runtimeService.createProcessInstanceQuery().count()).isEqualTo(0);
//    }
//
//    @Test
//    @Deployment(resources = {"PerformTransaction.bpmn"})
//    public void executeProcessInstanceTest() {
//        // Given we create a new process instance
//        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("performTransaction");
//        // Then it should be active
//        assertThat(processInstance).isActive();
//        // And it should be the only instance
//        assertThat(processInstanceQuery().count()).isEqualTo(1);
//        // And there should exist just a single task within that process instance
//        assertThat(task(processInstance)).isNotNull();
//
//        // When we complete that task
//        complete(task(processInstance));
//        // Then the process instance should be ended
//        assertThat(processInstance).isEnded();
//    }
//
//
//    @Test
//    public void executeInstanceTest() {
//
//        when(performTransaction.waitsAtUserTask("Activity_1od10vd"))
//                .thenReturn(taskDelegate -> {
//                    taskDelegate.complete(withVariables("delegateExpression", "prepareTransactionDelegate", "preparedTransaction"));
//                });
//
//        ProcessInstance instance = Scenario.run(performTransaction)
//                .startByKey("performTransaction")
//                .execute()
//                .instance(performTransaction);
//        delegate = new PrepareTransactionDelegate(restTemplate);
//        execution = mock(DelegateExecution.class); delegate = new PrepareTransactionDelegate(restTemplate);
//        execution = mock(DelegateExecution.class);
//
//        assertThat(instance).isActive();
//
//    }

}
