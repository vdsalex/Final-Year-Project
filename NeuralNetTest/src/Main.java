import NeuralNet.*;

import java.io.IOException;
import java.util.List;

public class Main
{
    private static List<String> vocabulary;
    private static AuxiliaryFunctions auxiliary;
    private final static int CONTEXT_SIZE = 1;
    private final static int RIGHT = 1;
    private final static int LEFT = -1;

    public static void main(String[] args) throws IOException
    {
        Initializer initializer = new Initializer();

        NeuralNet neuralNet = initializer.initializeNetwork();

        vocabulary = initializer.getVocabulary();
        String poetry = initializer.getPoetry();
        auxiliary = new AuxiliaryFunctions(vocabulary, poetry);

        int poetryLength = poetry.length();
        int index = auxiliary.skipSeparators(0, RIGHT);

        while(index < poetryLength)
        {
            String targetWord = auxiliary.getWord(index).toLowerCase();
            List<String> contextWordsRight = auxiliary.getFirstContextSizeWords(CONTEXT_SIZE, index + targetWord.length(), RIGHT);
            List<String> contextWordsLeft = auxiliary.getFirstContextSizeWords(CONTEXT_SIZE, index - 1, LEFT);

            printWordAndList(targetWord, contextWordsLeft);
            printWordAndList(targetWord, contextWordsRight);
            System.out.println();

            index += targetWord.length();

            if(auxiliary.isEndStatementChar(poetry.charAt(index)))
            {
                index = auxiliary.skipEndStatementChars(index);
                index = auxiliary.skipSeparators(index, RIGHT);
            }
            else
            {
                index = auxiliary.skipSeparators(index, RIGHT);
                index = auxiliary.skipEndStatementChars(index);
            }
        }

        //trainNetwork(neuralNet, "plin", "cornul");
    }

    private static void trainNetwork(NeuralNet neuralNet, String contextWordString, String targetWordString)
    {
        int vocabularySize = vocabulary.size();

        int i = auxiliary.findWord(targetWordString, 0, vocabularySize / 2, vocabularySize - 1);
        int j = auxiliary.findWord(contextWordString, 0, vocabularySize / 2, vocabularySize - 1);

        double[] targetWordVector = auxiliary.getWordVector(targetWordString, vocabularySize);

        neuralNet.getInputLayer().getNeurons().get(j).setInput(1.0);

        auxiliary.calculateInputsForLayer(neuralNet.getHiddenLayer());
        auxiliary.calculateInputsForLayer(neuralNet.getOutputLayer());

        double expSum = auxiliary.expSumInputsOfOutputLayerNeurons(neuralNet.getOutputLayer().getNeurons());

        double[] outputVector = new double[vocabularySize];

        for(int index = 0; index < vocabularySize; index++)
        {
            outputVector[index] = auxiliary.softmax(neuralNet.getOutputLayer().getNeurons().get(index).getOutput(), expSum);
        }
    }

    private static void printWordAndList(String word, List<String> list)
    {
        StringBuilder stringBuilder = new StringBuilder("");
        System.out.print("(");
        System.out.print(word);
        System.out.print(", ");

        for(String listItem: list)
        {
            stringBuilder.append(listItem);
            stringBuilder.append(", ");
        }

        if(stringBuilder.length() > 0)
        {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        stringBuilder.append(')');

        System.out.print(stringBuilder.toString());
        System.out.println();
    }
}
