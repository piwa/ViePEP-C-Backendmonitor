#!/bin/bash
mvn clean package -Dmaven.test.skip=true
docker build -t piwa/viepep.backend.monitor .
docker push piwa/viepep.backend.monitor
