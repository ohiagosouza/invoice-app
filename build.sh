#!/bin/bash
echo "----BUILD STARTED----"
mvn clean package -DskipTests
sudo docker-compose up --build
echo "----BUILD ENDED----"