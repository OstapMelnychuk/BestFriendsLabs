
public class RecursiveStringParser extends SimpleStringParser implements StringParser {

    public RecursiveStringParser(String textForParsing, String subString) {
        super(textForParsing, subString);
    }

    @Override
    public void parse() {
        findIndexesRecursive(0);
    }

    private void findIndexesRecursive(int indexPrev) {
        int index = textForParsing.indexOf(subString);
        if (index != -1) {
            try {
                this.textForParsing = textForParsing.substring(index + 1);
                arrayOfIndexes.add(index + indexPrev);
                findIndexesRecursive(index + indexPrev + 1);
            } catch (StackOverflowError error) {
                System.out.println(error.getMessage());
            }
        }
    }

}
