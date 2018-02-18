#!/bin/bash

CLASS="${1:-Jps}"

PID=`jps | grep $CLASS | awk '{print $1}'`
if [ "$PID" == "" ] || ! kill -0 $PID; then
    echo "Process $CLASS died"
    exit
fi
DUMP=dump-$CLASS-`date +"%F_%T"`.hprof
echo "Dumping to $DUMP..."
jmap -dump:format=b,file=$DUMP ${PID}
echo "Dumped!"
