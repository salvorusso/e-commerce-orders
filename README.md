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
Order Service REST API supports following operations:
Method | URI | Description | Parameters
--- | --- | --- | --- 
`POST` | */orders* | Adds a new order for the user indicated in the header |
`GET` | */orders* | List all user orders. Supports pagination | int per_page, int page
`GET` | */orders/{id}* | Returns a specific order based on its id | UUID id

Requests must have the header  HTTP _**X-User-ID**_ . The userId 0 will represent an admin, any other userId will represent a generic user.
