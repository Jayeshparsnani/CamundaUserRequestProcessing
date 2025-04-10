package com.camunda.UserRequest.bo.requests;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class UserRequestBody {

    private String userName;
    private String userId;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String userEmail;
    private String existingCustomer;
    private String initiationDate;
    private String initiatorId;
    private boolean status;
    private List<Product> products;

}
