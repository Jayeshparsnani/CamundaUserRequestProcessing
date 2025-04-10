# CamundaUserRequestProcessing
Following repo explain basic components used in camunda.

This repo explain following components:
1. Service
2. User Task
3. Multi Instance
4. Sub-process
5. Class Activity
6. Business Rule Task
7. DMN

   Use the following request for Insomnia/Postman:
   Post req : http://localhost:8088/api/v1/startRequest
   {
    "userName" : "userTest",
    "userId" : "87564311",
    "fullName" : "Firstname Lastname",
    "address" : "Address",
    "phoneNumber" : "8989898989",
    "userEmail" : "something@gmail.com",
    "existingCustomer" : "Yes",
    "initiationDate" : "01/01/2001",
    "initiatorId" : "909090909090",
    "status" : true,
    "products" : [
        {
            "productId" : "101",
            "productName" : "Amazon",
            "productPrice" : "1000",
            "productEmi" : 200
        },
        {
            "productId" : "102",
            "productName" : "Apple",
            "productPrice" : "2000",
            "productEmi" : 200
        },
        {
            "productId" : "103",
            "productName" : "Tesla",
            "productPrice" : "3000",
            "productEmi" : 200
        }
    ]
}
