import java.util.ArrayList;

public class SimpleStringParser implements StringParser {
    protected String textForParsing;
    protected String subString;
    protected ArrayList<Integer> arrayOfIndexes;

    public SimpleStringParser(String textForParsing, String subString) {
        this.textForParsing = textForParsing;
        this.subString = subString;
        this.arrayOfIndexes = new ArrayList<>();
    }

    public ArrayList<Integer> getArrayOfIndexes() {
        return arrayOfIndexes;
    }

    protected void findIndexesIterative(String textForParsing) {
        String copyTextForParsing = textForParsing;
        int index = textForParsing.indexOf(subString);
        int prevCharacters;
        while (index != -1) {
            prevCharacters = textForParsing.length() - copyTextForParsing.length();
            copyTextForParsing = copyTextForParsing.substring(index + 1);
            arrayOfIndexes.add(prevCharacters + index);
            index = copyTextForParsing.indexOf(subString);
        }
    }

    @Override
    public void parse() {
        findIndexesIterative(textForParsing);
    }
}
