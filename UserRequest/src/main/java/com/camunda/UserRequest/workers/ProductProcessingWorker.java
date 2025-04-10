package com.camunda.UserRequest.workers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.camunda.UserRequest.bo.requests.Product;
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
public class ProductProcessingWorker {

    @JobWorker(type="processProducts", autoComplete = false)
    public void processProducts(ActivatedJob job, JobClient client){
        log.info("ProductProcessingWorker : processProducts : Process Incoming Request {}", job);
        
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> variMap = new HashMap<>();

        Product product = mapper.convertValue(job.getVariable("products"), Product.class);

        UserRequestBody userRequestBody = mapper.convertValue(job.getVariable(AppConstant.USER_REQUEST), UserRequestBody.class);
        // log.info("ProductProcessingWorker : processProducts : Variables : {}", job.getVariablesAsMap());

        if(StringUtils.isEmpty((String) product.getProductName())){
            product.setProductName("Default-Product-Name");
        }    
        product.setTax((String) job.getVariable("taxOnProduct")); 
        log.info("ProductProcessingWorker : processProducts : Product {} has tasx {}", product.getProductName(), product.getTax());

        try{
            variMap.put(AppConstant.ENRICHED_PRODUCTS, product);
            log.info("ProductProcessingWorker : processProducts : Product Processing worker completed");
            client.newCompleteCommand(job).variables(variMap).send().join();
        }catch(Exception e){
            log.error("ProductProcessingWorker : processProducts : Exception occured in Product Processing Worker", e);
        }
    }

}
