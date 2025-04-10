package com.camunda.UserRequest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camunda.UserRequest.bo.requests.UserRequestBody;
import com.camunda.UserRequest.constant.AppConstant;
import com.camunda.UserRequest.service.AppService;

import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class AppController {

    @Autowired
    AppService appService;

    @PostMapping("/startRequest")
    public ResponseEntity<?> startProcess(@RequestBody UserRequestBody requestBody) {
        
        log.info("Starting the process");
        try{
            Map<String, Object> variMap = new HashMap<>();
            variMap.put(AppConstant.USER_REQUEST, requestBody);
            ProcessInstanceEvent event = appService.startRequestProcess(AppConstant.CAMUNDA_PROCESS_ID, variMap);
            return new ResponseEntity<>(event, HttpStatus.OK);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Processing Request" + e.getMessage());
        }

    }
    

}
