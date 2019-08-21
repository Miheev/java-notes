#!/usr/bin/env bash

case $1 in
  start)
    echo "Starting Docker Test Environment"
    cp -rf src/main/webapp/index.html src/main/resources/static/index.html
    cp -rf src/main/webapp/style.css src/main/resources/static/style.css
    cp -rf src/main/webapp/app src/main/resources/static/app
    ./mvnw clean install -P testJar -DskipTests;

#    docker-compose -f docker-jar/docker-compose.yml up -d;
    #With force image rebuild
    docker-compose -f docker-jar/docker-compose.yml up -d --build;

    echo "";
    echo "Server Url: http://localhost:9090";
    echo "";
    ;;
  stop)
    echo "Stopping Docker Test Environment"
    docker-compose -f docker-jar/docker-compose.yml down;
    rm -rf src/main/resources/static/index.html
    rm -rf src/main/resources/static/style.css
    rm -rf src/main/resources/static/app
    ;;
  *)
    echo "Usage: $0 {start|stop}" >&2
    exit 1
    ;;
esac
