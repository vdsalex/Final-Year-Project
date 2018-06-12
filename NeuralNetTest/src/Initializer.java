import NeuralNet.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Initializer
{
    private final static int HIDDEN_LAYER_SIZE = 3;
    private static List<String> vocabulary;
    private static String poetry;

    Initializer()
    {
        this.vocabulary = new ArrayList<>();
        this.poetry = "";
    }

    private static void setPoetry(String filename) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String line;
        StringBuilder stringBuilder = new StringBuilder();

        while((line = bufferedReader.readLine()) != null)
        {
            stringBuilder.append(line);
            stringBuilder.append('\n');
        }

        poetry = stringBuilder.toString();
    }

    private static void setVocabulary(String filename) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String line;

        while((line = bufferedReader.readLine()) != null)
        {
            vocabulary.add(line.trim());
        }
    }

    private void setWeights(double[][] weights, String filename) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String line;
        int i = 0, j;

        while((line = bufferedReader.readLine()) != null)
        {
            String[] lineValues = line.split(" ");
            j = 0;

            for(String valueString: lineValues)
            {
                double value = Double.parseDouble(valueString.trim());
                weights[i][j++] = value;
            }

            i++;
        }
    }

    private void initializeNeuronLayer(List<Neuron> neurons, int n)
    {
        for(int i = 0; i < n; i++)
        {
            Neuron neuron = new Neuron();

            neurons.add(neuron);
        }
    }

    private void initializeNetworkConnections(List<Neuron> input, List<Neuron> hidden, List<Neuron> output, double[][] inputWeights, double[][] outputWeights)
    {
        int numberOfNeurons = input.size();

        for(int i = 0; i < numberOfNeurons; i++)
        {
            Neuron inputNeuron = input.get(i);
            Neuron outputNeuron = output.get(i);

            for(int j = 0; j < HIDDEN_LAYER_SIZE; j++)
            {
                Neuron hiddenNeuron = hidden.get(j);

                NeuronsConnection connectionInputHidden =
                        new NeuronsConnection(inputNeuron, hiddenNeuron);

                NeuronsConnection connectionHiddenOutput =
                        new NeuronsConnection(hiddenNeuron, outputNeuron);

                inputNeuron.addOutputConnection(connectionInputHidden);
                hiddenNeuron.addInputConnection(connectionInputHidden);
                hiddenNeuron.addOutputConnection(connectionHiddenOutput);
                outputNeuron.addInputConnection(connectionHiddenOutput);
            }
        }
    }

    NeuralNet initializeNetwork() throws IOException
    {
        List<Neuron> inputNeurons = new ArrayList<>();
        List<Neuron> hiddenNeurons = new ArrayList<>();
        List<Neuron> outputNeurons = new ArrayList<>();

        setVocabulary("resources/vocabulary.txt");
        setPoetry("resources/poetry.txt");

        int numberOfNeurons = vocabulary.size();
        double[][] inputWeights = new double[numberOfNeurons][3];
        double[][] outputWeights = new double[3][numberOfNeurons];

        setWeights(inputWeights, "resources/wi.txt");
        setWeights(outputWeights, "resources/wo.txt");

        initializeNeuronLayer(inputNeurons, numberOfNeurons);
        initializeNeuronLayer(hiddenNeurons, HIDDEN_LAYER_SIZE);
        initializeNeuronLayer(outputNeurons, numberOfNeurons);
        initializeNetworkConnections(inputNeurons, hiddenNeurons, outputNeurons, inputWeights, outputWeights);

        NeuralNetLayer inputLayer = new NeuralNetLayer(inputNeurons);
        NeuralNetLayer hiddenLayer = new NeuralNetLayer(hiddenNeurons);
        NeuralNetLayer outputLayer = new NeuralNetLayer(outputNeurons);

        return new NeuralNet(inputLayer, hiddenLayer, outputLayer);
    }

    List<String> getVocabulary() { return vocabulary; }
    String getPoetry() { return poetry; }
}
