#!/bin/bash
echo "Building ordermanager v1.0.0..."
cd ../orders
docker build --no-cache -t unict/ordermanager:1.0.0 -f Dockerfile .

echo "Deploying with compose..."
cd ../scripts
docker-compose -p order-service -f docker-compose.yaml --env-file .env up -d