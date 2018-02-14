
%.class: %.java
	javac -g $<

ex01: FileSlow.class
	time -l java -cp . FileSlow ${ARGS}

ex01-fast: FileFast.class
	time -l java -cp . FileFast ${ARGS}
