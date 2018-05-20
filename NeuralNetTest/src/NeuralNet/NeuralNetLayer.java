package NeuralNet;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetLayer
{
    private List<Neuron> neurons;

    public NeuralNetLayer()
    {
        this.neurons = new ArrayList<>();
    }

    public NeuralNetLayer(List<Neuron> neurons)
    {
        this.neurons = neurons;
    }
}
