# e-commerce-orders
Generic implementation of an order management service for an ecommerce, built with Docker, SpringBoot, Mongodb and Kafka.
Applying the fundamental principles of microservices, this application allows you to manage user orders.

## Build and Deploy
Use the utility script `scripts/build_and_deploy.sh` to do all the stuff, or do it manually:
```
cd orders
docker build --no-cache -t unict/ordermanager:1.0.0 -f Dockerfile .
cd ../scripts
docker-compose -p order-service -f docker-compose.yaml --env-file .env up -d
```
Remember to customize _.env_ file properly.

## Order Service
### REST API
Order Service REST API supports following operations:
Method | URI | Description | Parameters
--- | --- | --- | --- 
`POST` | */orders* | Adds a new order for the user indicated in the header |
`GET` | */orders* | List all user orders. Supports pagination | int per_page, int page
`GET` | */orders/{id}* | Returns a specific order based on its id | UUID id

Requests must have the header  HTTP _**X-User-ID**_ . The userId 0 will represent an admin, any other userId will represent a generic user.

### Kafka Client
Order Service is also producer and consumer on the topic _orders_ of the following messages:

- When a new order is created successfully, this message is produced and forwarded on the topics _orders_ and _notifications_ :
```
key = order_completed 
value = {    
  orderId: id,   
  products: [ { product_id: quantity }, "..." ],    
  total: amount,    
  shippingAddress: {...},    
  billingAddress: {...},    
  userId: :id,    
  extraArgs: {} 
  }
```

- When this message is consumed, if the _status_code_ received is not 0, the corresponding order status is set to _Abort_
```
key = order_validation  
value = {  
  timestamp: UnixTimestamp
  orderId: id,
  status: status_code,
  extraArgs: {}
  }
```

- When this message is consumed, it is verified that the triple orderId, userId, amountPaid exists. If it exists, the order status is set to _Paid_ and the same message is forwarded on the topics _notifications_ , _invoicing_. Otherwise, the order status is set to _Abort_ , and the message is forwarded on the topic _logging_ whit _key = order_paid_validation_failure_ , and one of the     following _extraArgs_ :
  - Order not found: ` extraArgs: {error: "ORDER_NOT_FOUND"} `
  - Wrong amount paid: ` extraArgs: {error: "WRONG_AMOUNT_PAID"} `
```
key = order_paid   
value = {  
  timestamp: UnixTimestamp
  userId: userId,
  amountPaid: amountPaid,
  extraArgs: {...}
  }
```   
  
### Error handling
When a request fails and an exception is raised, the following kafka message is forwarded on the topic _logging_:
```
key = http_errors   
value = {  
  timestamp: UnixTimestamp
  sourceIp: sourceIp,
  service: ordermanager,
  request: path + method,
  error: {...}
  }
```  
If the error is of type _50x_ , _error_ contains the stack trace. 
If the error is of type _40x_ , _error_ contains the raw http status code.
