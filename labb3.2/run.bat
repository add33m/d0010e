javac -cp "./FIFOmain.jar" FIFO.java
jar uf FIFOmain.jar FIFO.class
java -cp FIFOmain.jar FIFOmain
javac -cp "./BFTmain.jar" GraphIO.java
javac -cp "./BFTmain.jar" FIFO.java
jar uf BFTmain.jar GraphIO.class
jar uf BFTmain.jar FIFO.class
java -cp BFTmain.jar BFTmain gridgraph
