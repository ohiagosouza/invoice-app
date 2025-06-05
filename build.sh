#!/bin/bash
mvn clean package -DskipTests
sudo docker build -t quoted-app .