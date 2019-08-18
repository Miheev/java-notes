#!/usr/bin/env bash

case $1 in
  start)
    echo "Starting Docker Test Environment"
    ./mvnw clean install -P test -DskipTests;

    docker-compose -f docker/docker-compose.yml up -d;
    #With force image rebuild
    #docker-compose -f docker/docker-compose.yml up -d --build;

    echo "";
    echo "Server Url: http://localhost:9090";
    echo "";
    ;;
  stop)
    echo "Stopping Docker Test Environment"
    docker-compose -f docker/docker-compose.yml down;
    ;;
  *)
    echo "Usage: $0 {start|stop}" >&2
    exit 1
    ;;
esac
