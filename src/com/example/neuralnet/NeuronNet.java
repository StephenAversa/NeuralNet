package com.example.neuralnet;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class NeuronNet {
	
	public static NeuronNet neuronet = new NeuronNet(); 
	Map<String, Neuron> neurons; 
	boolean loadedFromFile = false; 
	
	
	NeuronNet(){
		neurons = new HashMap<String, Neuron>();
		setNeurons();
	}
	
	private void setNeurons(){
		
		neurons.put("alpha", new Neuron()); neurons.put("beta", new Neuron());
		neurons.put("gamma", new Neuron()); neurons.put("delta", new Neuron());
		neurons.put("epsilon", new Neuron()); neurons.put("zeta", new Neuron());
		neurons.put("eta", new Neuron()); neurons.put("theta", new Neuron());
		neurons.put("iota", new Neuron()); neurons.put("kappa", new Neuron());
		neurons.put("lambda", new Neuron()); neurons.put("mu", new Neuron());
		neurons.put("nu", new Neuron()); neurons.put("xi", new Neuron());
		neurons.put("omicron", new Neuron()); neurons.put("pi", new Neuron());
		neurons.put("rho", new Neuron()); neurons.put("sigma", new Neuron());
		neurons.put("tau", new Neuron()); neurons.put("upsilon", new Neuron());
		neurons.put("phi", new Neuron()); neurons.put("chi", new Neuron());
		neurons.put("psi", new Neuron()); neurons.put("omega", new Neuron());
		neurons.put("cap1", new Neuron()); neurons.put("cap2", new Neuron());
	}
	
    public void getXML()
    {
    	if (loadedFromFile == false)
        {
    		loadedFromFile = true;
               
                File myFile = new File(Neuron.filePath, Neuron.file);
                if (myFile.exists()){
                        Map<String, double[]> tempneurons = Neuron.read();
                        for (Entry<String, double[]> entry : tempneurons.entrySet()) {
                        	
                        	String character = entry.getKey();
                        	double[] weight = entry.getValue();
                        	neurons.get(character).setWeight(weight);
                        }
                }
        }
    }
    
	public void resetAllNeuronM()
	{
		Iterator it = neurons.entrySet().iterator();
		
		while (it.hasNext())
		{
			Map.Entry mEntry = (Map.Entry) it.next();
			Neuron curNeuron = (Neuron) mEntry.getValue();
			curNeuron.resetM();
		}
	}

}