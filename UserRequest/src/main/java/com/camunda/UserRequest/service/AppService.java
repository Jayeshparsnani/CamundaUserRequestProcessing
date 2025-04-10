package com.camunda.UserRequest.service;

import java.util.Map;

import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;

public interface AppService {

    public ProcessInstanceEvent startProcessInstance(String processDefinitionKey);
    
    public ProcessInstanceEvent startRequestProcess(String processId, Map<String, Object> variMap);

}