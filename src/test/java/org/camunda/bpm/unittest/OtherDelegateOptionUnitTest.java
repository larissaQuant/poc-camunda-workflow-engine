//package org.camunda.bpm.unittest;
//
//import com.poc.workflow.CamundaWorkflowApplication;
//import com.poc.workflow.service.PrepareTransactionDelegate;
//import org.camunda.bpm.engine.ProcessEngine;
//import org.camunda.bpm.engine.runtime.ProcessInstance;
//import org.camunda.bpm.spring.boot.starter.rest.CamundaBpmRestInitializer;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.web.client.RestTemplate;
//
//import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.init;
//import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
//import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
//import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;
//import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;
//import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;
//
//
//@SpringBootTest(classes = CamundaWorkflowApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@RunWith(SpringRunner.class)
//@Import({PrepareTransactionDelegate.class, RestTemplate.class})
//public class OtherDelegateOptionUnitTest {
//
//    @Autowired
//    ProcessEngine processEngine;
//    @MockBean
//    CamundaBpmRestInitializer restInitlzr;
//
//    @Before
//    public void setUp() {
//        init(processEngine);
//    }
//
//    @Test
//    public void testHappyPath() {
//
//        ProcessInstance pi = runtimeService().startProcessInstanceByKey("performTransaction");
//        assertThat(pi).isStarted()
//                .hasPassed("StartEvent_1")
//                .isWaitingAt("Activity_1od10vd");
//
//
////        complete(task(), withVariables("prepareTransactionResponse", "prepareTransactionResponse"));
////        assertThat(pi).isWaitingAt("Activity_18fnjhw")
////                .hasPassed("CallRESTServiceTask")
////                .hasVariables("email", "Ad");
//    }
//
//   // @Test
////    public void executeInstanceTest() {
////
////        when(performTransaction.waitsAtUserTask("Activity_1od10vd"))
////                .thenReturn(taskDelegate -> {
////                    taskDelegate.complete(withVariables("delegateExpression", "prepareTransactionDelegate", "preparedTransaction"));
////                });
////
////        ProcessInstance instance = Scenario.run(performTransaction)
////                .startByKey("performTransaction")
////                .execute()
////                .instance(performTransaction);
////        delegate = new PrepareTransactionDelegate(restTemplate);
////        execution = mock(DelegateExecution.class); delegate = new PrepareTransactionDelegate(restTemplate);
////        execution = mock(DelegateExecution.class);
////
////        assertThat(instance).isActive();
////
////    }
//}
