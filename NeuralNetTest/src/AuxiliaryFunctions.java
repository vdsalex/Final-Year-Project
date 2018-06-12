import NeuralNet.NeuralNetLayer;
import NeuralNet.Neuron;
import NeuralNet.NeuronsConnection;

import java.util.ArrayList;
import java.util.List;

class AuxiliaryFunctions
{
    private final char[] SEPARATORS = {',', ' ', ',', ':', '\'', '\"', '\n', '-', '„'};
    private final int[] LETTERS_WITH_DIACRITICS = {
            (int) 'ș',
            (int) 'ş',
            (int) 'Ș',
            (int) 'Ş',
            (int) 'ă',
            (int) 'Ă',
            (int) 'î',
            (int) 'Î',
            (int) 'â',
            (int) 'Â',
            (int) 'ț',
            (int) 'ţ',
            (int) 'Ț',
            (int) 'Ţ',
            (int) 'ç',
            (int) 'Ç',
            (int) 'é',
            (int) 'è',
            (int) 'ê',
            (int) 'ô',
            (int) 'û',
            (int) 'ù',
            (int) 'à',
            (int) 'ë',
            (int) 'ï'};

    private List<String> vocabulary;
    private String poetry;
    private int poetryLength;

    AuxiliaryFunctions(List<String> vocabulary, String poetry)
    {
        this.vocabulary = vocabulary;
        this.poetry = poetry;
        this.poetryLength = poetry.length();
    }

    double softmax(double value, double expSum)
    {
        return Math.exp(value) / expSum;
    }

    void calculateInputsForLayer(NeuralNetLayer layer)
    {
        List<Neuron> neurons = layer.getNeurons();

        for(Neuron neuron: neurons)
        {
            List<NeuronsConnection> inputConnections = neuron.getInputConnections();
            double input = calculateInputForNeuron(inputConnections);
            neuron.setInput(input);
        }
    }

    double expSumInputsOfOutputLayerNeurons(List<Neuron> neurons)
    {
        double sum = 0.0;

        for(Neuron neuron: neurons)
        {
            sum += Math.exp(neuron.getOutput());
        }

        return sum;
    }

    private double calculateInputForNeuron(List<NeuronsConnection> connections)
    {
        double totalInput = 0.0;

        for(NeuronsConnection connection: connections)
        {
            totalInput += connection.getWeight() * connection.getFromNeuron().getOutput();
        }

        return totalInput;
    }

    int findWord(String word, int rangeStart, int middle, int rangeStop)
    {
        if(vocabulary.get(middle).equals(word))
        {
            return middle;
        }
        else
        {
            if(word.compareTo(vocabulary.get(middle)) > 0)
            {
                return findWord(word, middle, middle + (rangeStop - middle)/2, rangeStop);
            }
            else if(word.compareTo(vocabulary.get(middle)) < 0)
            {
                return findWord(word, rangeStart, rangeStart + (middle - rangeStart)/2, middle);
            }
        }

        return -1;
    }

    double[] getWordVector(String word, int vocabularySize)
    {
        double[] vector = new double[vocabularySize];

        for(int i = 0; i < vocabularySize; i++)
        {
            if(word.equals(vocabulary.get(i)))
            {
                vector[i] = 1.0;

                break;
            }
        }

        return vector;
    }

    int skipSeparators(int index, int direction)
    {
        int textLength = poetry.length();

        while(index < textLength && index >= 0 && isSeparator(poetry.charAt(index)))
        {
            index += direction;
        }

        return index;
    }

    private boolean isSeparator(char c)
    {
        for(char separator: SEPARATORS)
        {
            if(c == separator)
            {
                return true;
            }
        }

        return false;
    }

    List<String> getFirstContextSizeWords(int contextSize, int startIndex, int direction)
    {
        List<String> words = new ArrayList<>();

        if(direction == -1)
        {
            startIndex = skipSeparators(startIndex, -1);

            while(startIndex >= 0 && !isEndStatementChar(poetry.charAt(startIndex)) && contextSize > 0)
            {
                StringBuilder word = new StringBuilder();

                while(startIndex >= 0 && isLetterOrHyphen(poetry.charAt(startIndex)))
                {
                    word.append(poetry.charAt(startIndex));
                    startIndex--;
                }

                words.add(word.reverse().toString().toLowerCase());
                contextSize--;

                startIndex = skipSeparators(startIndex, -1);
            }
        }
        else
        {
            startIndex = skipSeparators(startIndex, 1);

            while(startIndex < poetryLength && !isEndStatementChar(poetry.charAt(startIndex)) && contextSize > 0)
            {
                StringBuilder word = new StringBuilder();

                while(isLetterOrHyphen(poetry.charAt(startIndex)) && startIndex < poetryLength)
                {
                    word.append(poetry.charAt(startIndex));
                    startIndex++;
                }

                words.add(word.toString().toLowerCase());
                contextSize--;

                startIndex = skipSeparators(startIndex, 1);
            }
        }

        return words;
    }

    boolean isLetterOrHyphen(char c)
    {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '-' || isLetterWithDiacritics(c);
    }

    boolean isEndStatementChar(char c)
    {
        return c == '.' || c == '!' || c == '?' || c == ';';
    }

    String getWord(int startIndex)
    {
        StringBuilder word = new StringBuilder("");

        while(startIndex < poetryLength && isLetterOrHyphen(poetry.charAt(startIndex)))
        {
            word.append(poetry.charAt(startIndex));
            startIndex++;
        }

        return word.toString();
    }

    int skipEndStatementChars(int index)
    {
        while(index < poetryLength && isEndStatementChar(poetry.charAt(index)))
        {
            index++;
        }

        return index;
    }

    private boolean isLetterWithDiacritics(char c)
    {
        for(int i = 0; i < LETTERS_WITH_DIACRITICS.length; i++)
        {
            if(c == LETTERS_WITH_DIACRITICS[i])
            {
                return true;
            }
        }

        return false;
    }
}
