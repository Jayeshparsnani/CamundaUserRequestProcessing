package com.camunda.UserRequest.workers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.camunda.UserRequest.bo.requests.Product;
import com.camunda.UserRequest.bo.requests.UserRequestBody;
import com.camunda.UserRequest.constant.AppConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OutputWorker {
    @JobWorker(type="finalDataOutput", autoComplete = false)
    public void finalDataOutput(ActivatedJob job, JobClient client) throws JsonProcessingException{
        log.info("OutputWorker : finalDataOutput : Process Incoming Request as {}", job);
        
        ObjectMapper mapper = new ObjectMapper();
        
        UserRequestBody userRequestBody = mapper.convertValue(job.getVariable(AppConstant.USER_REQUEST), UserRequestBody.class);

        List<Product> updatedProductList = List.of(mapper.convertValue(job.getVariable("processedProducts"), Product[].class));

        log.info("OutputWorker : finalDataOutput : UpdatedProductList - {}", updatedProductList);

        userRequestBody.setProducts(updatedProductList);
        
        String json = mapper.writeValueAsString(userRequestBody);

        try{
            log.info("Final Data : {}", json);
            client.newCompleteCommand(job).send().join();
        }catch(Exception e){
            log.error("OutputWorker : finalDataOutput : Exception occured while processing request", e);
        }
    }
}
