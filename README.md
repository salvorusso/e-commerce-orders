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
