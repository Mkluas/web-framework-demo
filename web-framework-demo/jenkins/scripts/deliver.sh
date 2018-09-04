#!/usr/bin/env bash

docker run -d -p 9000:8080 -u root klaus/my-app:1.0-SNAPSHOT --name app
