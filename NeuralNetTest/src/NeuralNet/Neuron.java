package NeuralNet;

import NeuralNet.NeuronsConnection;
import java.util.ArrayList;
import java.util.List;

public class Neuron
{
    private double input;

    private List<NeuronsConnection> inputConnections;
    private List<NeuronsConnection> outputConnections;

    public Neuron()
    {
        this.inputConnections = new ArrayList<>();
        this.outputConnections = new ArrayList<>();
        this.input = 0.0;
    }

    public Neuron(double input)
    {
        this();
        this.input = input;
    }

    // getters
    public List<NeuronsConnection> getInputConnections() { return inputConnections; }
    public List<NeuronsConnection> getOutputConnections() { return outputConnections; }
    public double getOutput() { return input; }

    public void addInputConnection(NeuronsConnection neuronsConnection)
    {
        inputConnections.add(neuronsConnection);
    }

    public void addOutputConnection(NeuronsConnection neuronsConnection)
    {
        outputConnections.add(neuronsConnection);
    }

    // setters
    public void setInput(double input)
    {
        this.input = input;
    }
}
