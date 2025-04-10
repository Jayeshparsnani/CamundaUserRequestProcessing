package com.camunda.UserRequest.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camunda.UserRequest.service.AppService;

import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.ZeebeClient;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppServiceImpl implements AppService{

    @Autowired
    ZeebeClient zeebeClient;

    @Override
    public ProcessInstanceEvent startProcessInstance(String processDefinitionKey){
        log.info("Starting process instance with definition key : {}", processDefinitionKey);
        return zeebeClient.newCreateInstanceCommand().bpmnProcessId(processDefinitionKey).latestVersion().send().join();
    }

    @Override
    public ProcessInstanceEvent startRequestProcess(String processId, Map<String, Object> variMap){
        final ProcessInstanceEvent instanceEvent = zeebeClient.newCreateInstanceCommand().bpmnProcessId(processId).latestVersion().variables(variMap).send().join();
        return instanceEvent;
    }
}
