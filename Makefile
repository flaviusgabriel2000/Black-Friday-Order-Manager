build: Tema2.java OrderThread.java OrderProductsThread.java Order.java
	javac $^

clean:
	rm -f *.class

.PHONY: build clean
