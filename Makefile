
%.class: %.java
	javac -g $<

ex01: FileSlow.class
	time -l java -cp . FileSlow ${ARGS}

