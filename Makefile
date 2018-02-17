%.class: %.java
	javac -g $<

ex01: FileSlow.class
	time java -cp . FileSlow ${ARGS}

ex01-fast: FileFast.class
	time java -cp . FileFast ${ARGS}

ex02: MapLeak.class
	time java -XX:+PrintGCDetails -XX:+PrintGCTimeStamps  -cp . MapLeak

ex02-fast: MapLeak.class
	time java -DuseGoodKey=true -XX:+PrintGCDetails -XX:+PrintGCTimeStamps  -cp . MapLeak

server: Server.class
	java -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -cp . Server

ex03: Matrix.class
	time java -Xmx2G -Xms2G -XX:+PrintGCDetails -XX:+PrintGCTimeStamps  -cp . Matrix

ex03-fast: Matrix.class
	time java -DsumByRow=true -Xmx1G -Xms1G -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -cp . Matrix
