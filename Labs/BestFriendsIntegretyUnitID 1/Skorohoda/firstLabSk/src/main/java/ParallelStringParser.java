import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ParallelStringParser extends SimpleStringParser implements Callable<ArrayList<Integer>>, StringParser {
    private int numberOfThreads;

    public ParallelStringParser(String textForParsing, String subString, int numberOfThreads) {
        super(textForParsing, subString);
        this.numberOfThreads = numberOfThreads;
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
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
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

            future = executor.submit(
                    new ParallelStringParser(copyOfTextForParsing.substring(0, indexOfSplitting),
                            subString, numberOfThreads));

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
