

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src\\main\\resources\\plrabn12.txt");

        SimpleStringParser simpleStringParser = new SimpleStringParser(FileUtils.readFileToString(file, "UTF-8"), "a");
        //SimpleStringParser recursiveStringParser = new RecursiveStringParser(FileUtils.readFileToString(file, "UTF-8"), "a");
        SimpleStringParser parallelStringParser = new ParallelStringParser(FileUtils.readFileToString(file, "UTF-8"), "a", 100);

        System.out.println("Time for simple: " + SecondsTimer.calculateTime(simpleStringParser::parse).toMillis() / 1000d + " seconds;\n"
                + simpleStringParser.getArrayOfIndexes().toString());

        /*System.out.println("Time for recursive: " + Timer.calculateTime(recursiveStringParser::parse).toMillis() / 1000d + " seconds;\n"
                + simpleStringParser.getArrayOfIndexes().toString());*/

        System.out.println("Time for parallel: " + SecondsTimer.calculateTime(parallelStringParser::parse).toMillis() / 1000d + " seconds;\n"
                + parallelStringParser.getArrayOfIndexes().toString() + "\n");
    }
}
