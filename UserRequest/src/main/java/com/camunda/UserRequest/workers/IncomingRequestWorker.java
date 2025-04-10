package com.camunda.UserRequest.workers;

import org.springframework.stereotype.Service;

import com.camunda.UserRequest.service.AppService;

import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IncomingRequestWorker {
    private final AppService appService;

    public IncomingRequestWorker(AppService appService){this.appService=appService;}

    @JobWorker(type="processRequest", autoComplete = false)
    public void processRequest(ActivatedJob job, JobClient client){
        log.info("IncomingRequestWorker : processRequest : ProProcess Incoming Request with Process Instance Key as {}", job.getProcessInstanceKey());

        try{
            client.newCompleteCommand(job).send().join();
        }catch(Exception e){
            log.error("IncomingRequestWorker : processRequest : Exception occured while processing request", e);
        }
    }
}
