package com.camunda.UserRequest.workers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.camunda.UserRequest.bo.requests.UserRequestBody;
import com.camunda.UserRequest.constant.AppConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ValidationWorker {

    @JobWorker(type="dataValidate", autoComplete = false)
    public void dataValidate(ActivatedJob job, JobClient client){
        log.info("ValidationWorker : dataValidate : ProProcess Incoming Request with Process Instance Key as {}", job.getProcessInstanceKey());
        
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> variMap = new HashMap<>();
        
        variMap.put(AppConstant.IS_VALIDATION_FAILED, false);

        UserRequestBody userRequestBody = mapper.convertValue(job.getVariable(AppConstant.USER_REQUEST), UserRequestBody.class);
        log.info("ValidationWorker : dataValidate : Variables : {}", job.getVariablesAsMap());

        if(job.getVariablesAsMap().containsKey("userId") && !StringUtils.isEmpty((String) job.getVariable("userId"))){
            log.info("ValidationWorker : dataValidate : Checking userId");
            userRequestBody.setUserId((String) job.getVariable("userId"));
        }
        if(job.getVariablesAsMap().containsKey("userName") && !StringUtils.isEmpty((String) job.getVariable("userName"))){
            log.info("ValidationWorker : dataValidate : Checking userName");
            userRequestBody.setUserName((String) job.getVariable("userName"));
        }

        variMap.put(AppConstant.USER_REQUEST, userRequestBody);

        if(StringUtils.isEmpty((String) userRequestBody.getUserId())){
            variMap.put(AppConstant.IS_VALIDATION_FAILED, true);
            variMap.put("userId", "");
        }    
        if(StringUtils.isEmpty((String) userRequestBody.getUserName())){
            variMap.put(AppConstant.IS_VALIDATION_FAILED, true);
            variMap.put("userName", "");
        }    

        try{
            log.info("ValidationWorker : dataValidate : Validation worker process completed");
            client.newCompleteCommand(job).variables(variMap).send().join();
        }catch(Exception e){
            log.error("ValidationWorker : dataValidate : Exception occured in Validation Worker", e);
        }
    }
}
