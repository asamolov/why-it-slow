%.class: %.java
	javac -g $<

ex01: FileSlow.class
	time java -cp . FileSlow ${ARGS}

ex01-fast: FileFast.class
	time java -cp . FileFast ${ARGS}

ex02: MapLeak.class
	time java -XX:+PrintGCDetails -XX:+PrintGCTimeStamps  -cp . MapLeak

server: Server.class
	java -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -cp . Server