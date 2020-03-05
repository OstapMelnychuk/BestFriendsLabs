import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ParallelStringParser extends SimpleStringParser implements Callable<ArrayList<Integer>>, StringParser {
    private int numberOfThreads = 1;
    private ExecutorService executor;

    public ParallelStringParser(String textForParsing, String subString, int numberOfThreads) {
        super(textForParsing, subString);
        this.numberOfThreads = numberOfThreads;
        executor = Executors.newFixedThreadPool(numberOfThreads);
    }

    public ParallelStringParser(String textForParsing, String subString) {
        super(textForParsing, subString);
        executor = Executors.newCachedThreadPool();
    }

    private int findIndexForSplitting(String copyOfTextForParsing, int numberOfThreads) {
        int index = copyOfTextForParsing.length() / numberOfThreads + 1;
        char middleCharacter;
        if (numberOfThreads != 1 && copyOfTextForParsing.length() > index) {
            middleCharacter = copyOfTextForParsing.charAt(index);
            for (int i = 0, side = 1; subString.contains(Character.toString(middleCharacter)); i++, side *= -1) {
                index = copyOfTextForParsing.length() / numberOfThreads + 1 + i * side;
                if (index < 0) index *= -1;
                middleCharacter = copyOfTextForParsing.charAt(index);
            }
            return index;
        } else {
            return 0;
        }
    }

    @Override
    public void parse() {
        Future<ArrayList<Integer>> future;
        String copyOfTextForParsing = textForParsing;

        int indexOfSplitting;
        int numberOfCharacters = 0;

        for (int begin = 0; begin < numberOfThreads; begin++) {
            indexOfSplitting = findIndexForSplitting(copyOfTextForParsing, numberOfThreads - begin);

            if (copyOfTextForParsing.length() < indexOfSplitting) {
                indexOfSplitting = copyOfTextForParsing.length();
            } else if (indexOfSplitting == 0) {
                indexOfSplitting = copyOfTextForParsing.length();
            }

            if (this.numberOfThreads != 1) {
                future = executor.submit(
                        new ParallelStringParser(copyOfTextForParsing.substring(0, indexOfSplitting),
                                subString, numberOfThreads));
            } else {
                future = executor.submit(
                        new ParallelStringParser(copyOfTextForParsing.substring(0, indexOfSplitting),
                                subString));
            }
            copyOfTextForParsing = copyOfTextForParsing.substring(indexOfSplitting);

            try {
                int finalNumberOfCharacters = numberOfCharacters;
                arrayOfIndexes.addAll(future.get().stream().map(el -> el + finalNumberOfCharacters).collect(Collectors.toList()));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            numberOfCharacters += indexOfSplitting;
        }
        executor.shutdown();
    }


    @Override
    public ArrayList<Integer> call() {
        findIndexesIterative(textForParsing);
        return getArrayOfIndexes();
    }
}
