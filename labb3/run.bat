javac -cp "lib/FIFOmain.jar" -sourcepath "src/*.java" "src/interfaces/*.java" -d "bin"
jar uf "lib/FIFOmain.jar" "bin/FIFO.class"
java -cp "lib/FIFOmain.jar" FIFOmain
javac -cp "./BFTmain.jar" "src/GraphIO.java"
javac -cp "./BFTmain.jar" "src/FIFO.java"
jar uf "lib/BFTmain.jar" "bin/GraphIO.class"
jar uf "lib/BFTmain.jar" "bin/FIFO.class"
java -cp "lib/BFTmain.jar" BFTmain gridgraph
