package org.camunda.bpm.unittest;

import com.poc.workflow.CamundaWorkflowApplication;
import com.poc.workflow.service.PrepareTransactionDelegate;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.spring.boot.starter.rest.CamundaBpmRestInitializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.init;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;


@SpringBootTest(classes = CamundaWorkflowApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@Import({PrepareTransactionDelegate.class, RestTemplate.class})
public class OtherDelegateOptionUnitTest {

    @Autowired
    ProcessEngine processEngine;
    @MockBean
    CamundaBpmRestInitializer restInitlzr;

    @Before
    public void setUp() {
        init(processEngine);
    }

    @Test
    public void testHappyPath() {

        ProcessInstance pi = runtimeService().startProcessInstanceByKey("performTransaction");
        assertThat(pi).isStarted()
                .hasPassed("StartEvent_1");

//        complete(task(), withVariables("userId", 2));
//        assertThat(pi).isWaitingAt("CheckResultTask")
//                .hasPassed("CallRESTServiceTask")
//                .hasVariables("email", "Ad");
    }
}