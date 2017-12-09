#!/bin/bash

##
## Starts the Thrift client that
## connects to the Thrift server.
##

CMD="java -jar"
BASE_DIR=`dirname $0`
JAR="${BASE_DIR}/../../../target/thrift-client-0.1-SNAPSHOT.jar"

for(( i = 0 ; i < $1 ; i++ ))
do
    ${CMD} ${JAR} 2 & $@
done