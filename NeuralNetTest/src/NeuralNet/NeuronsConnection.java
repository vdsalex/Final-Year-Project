package NeuralNet;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alex on 5/10/2018.
 */
public class NeuronsConnection
{
    private Neuron fromNeuron;
    private Neuron toNeuron;
    private double weight;

    public NeuronsConnection() {}
    public NeuronsConnection(Neuron fromNeuron, Neuron toNeuron, double weight)
    {
        this.fromNeuron = fromNeuron;
        this.toNeuron = toNeuron;
        this.weight = weight;
    }

    public NeuronsConnection(Neuron fromNeuron, Neuron toNeuron)
    {
        this.fromNeuron = fromNeuron;
        this.toNeuron = toNeuron;
        this.weight = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
    }

    // getters
    public Neuron getFromNeuron() { return fromNeuron; }
    public Neuron getToNeuron() { return toNeuron; }
    public double getWeight() { return weight; }
}
