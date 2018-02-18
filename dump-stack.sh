#!/bin/bash

COUNTER="${3:-10}"
SLEEP="${2:-1}"
CLASS="${1:-Jps}"

while [ $COUNTER -ge 0 ]; do
    PID=`jps | grep $CLASS | awk '{print $1}'`
    if [ "$PID" == "" ] || ! kill -0 $PID; then
        echo "Process $CLASS died"
        break
    fi
    echo "Stack $COUNTER"
    jstack -l ${PID} > stack-$CLASS-`date +"%F_%T"`.txt
    sleep $SLEEP
    let COUNTER=COUNTER-1
done

