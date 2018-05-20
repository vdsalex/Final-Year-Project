package NeuralNet;

public class NeuralNet
{
    private NeuralNetLayer inputLayer;
    private NeuralNetLayer hiddenLayer;
    private NeuralNetLayer outputLayer;

    public NeuralNet(NeuralNetLayer inputLayer, NeuralNetLayer hiddenLayer, NeuralNetLayer outputLayer)
    {
        this.inputLayer = inputLayer;
        this.hiddenLayer = hiddenLayer;
        this.outputLayer = outputLayer;
    }
}
