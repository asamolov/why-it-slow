%.class: %.java
	javac -g $<

ex01: FileSlow.class
	time java -cp . FileSlow ${ARGS}

ex01-fast: FileFast.class
	time java -cp . FileFast ${ARGS}
