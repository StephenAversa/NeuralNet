package com.example.neuralnet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import android.os.Environment;

public class Neuron {
	public static final double c = 0.8;
	public static final double epsilon = 0.001;
	public static final double lambda = 0.3;
	public static final double error = 0.1;
	public int m;
	public List<Double> pWeights;
	public static final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
	public static final String file = "NeurNetFinal.xml";
	
	public double [] weights;
	public double output;
	
	public Neuron(){
		weights = new double [73];
		for(int i=0; i<73; i++){
			weights[i] = 0.5;
		}
		m = 0;
		pWeights = new ArrayList<Double>();
	}
	
	public void resetM(){
		m = 0;
	}

	public void setWeight(double[] newWeight){
            weights = newWeight;
    }

	public void train(double [] input,int dOut){
		double netInput = 0;
		for(int i=0; i<72; i++){
			netInput += input[i] * weights[i];
		}
		
		netInput += 1 * weights[72];
		
		double output = (1/ (1 + Math.pow(Math.E, -1 * lambda * netInput)));
		double weightChange = c * (dOut - output) * output * (1 - output);
		
		for(int i=0; i<72; i++){
			if(input[i] == 1){
				weights[i] += weightChange;
			}
		}
		weights[72] += weightChange;
		
		if(dOut == 1){
			if(pWeights.size() == 3){
				pWeights.remove(0);
			}
			pWeights.add(weightChange);
			
			if(pWeights.size() == 3){
				double [] d = new double [3];
				
				for(int i = 0; i<3; i++){
					d[i] = pWeights.get(i);
				}
				
				if (Math.abs(d[0] - d[1]) < epsilon) {
					if (Math.abs(d[1] - d[2]) < epsilon){
						m++;
				    }
			    }
			}
		}
		
	}
	


	private void save(){
		SAXBuilder builder = new SAXBuilder(); 
		File myFile = new File(filePath, file);
		
		try {
            	if (!myFile.exists()){
            		Document doc = new Document();
            		Element rootNode = new Element("Network");
            		for (Entry<String, Neuron> entry : NeuronNet.neuronet.neurons.entrySet()) {
                            String key = entry.getKey();
                            Neuron neuron = entry.getValue();
                           
                            double[] tWeights = neuron.getWeights();
                           
                            Element neurons = new Element("Neuron-" + key);
                           
                            for (int i = 0; i < 73; i++){
                                    Element weight = new Element("Weight-" + i);
                                    weight.setText(Double.toString(tWeights[i]));
                                    neurons.addContent(weight);
                            }
                           
                            rootNode.addContent(neurons);
                    }
                    doc.addContent(rootNode);
                   
                    XMLOutputter xmlOutput = new XMLOutputter();
                    xmlOutput.output(doc, new FileWriter(myFile.getAbsolutePath()));
            }else{
                    Document doc = (Document) builder.build(myFile);                                      
                    Element rootNode = doc.getRootElement();
                   
                    for (Entry<String, Neuron> entry : NeuronNet.neuronet.neurons.entrySet()) {
                        	
                    		String key = entry.getKey();
                        	Neuron neuron = entry.getValue();
                           
                            double[] curWeights = neuron.getWeights();
                           
                            Element neurons = rootNode.getChild("Neuron-" + key);
                           
                            for (int i = 0; i < 73; i++){
                                    Element weight = neurons.getChild("Weight-" + i);
                                    weight.setText(Double.toString(curWeights[i]));
                            }
                    }
                   
                    XMLOutputter xmlOutput = new XMLOutputter();
                    xmlOutput.output(doc, new FileWriter(myFile.getAbsolutePath()));
            }                                                              
           
    } catch (JDOMException e) {
    } catch (IOException e) {
    }
	}
    	
	static public Map<String, double[]> read(){
            SAXBuilder builder = new SAXBuilder();
            File myFile = new File(filePath, file);
           
            try {
                    Document doc = (Document) builder.build(myFile);
                    Element rootNode = doc.getRootElement();
                    
                    Map<String, double[]> neurons = new HashMap<String, double[]>();
                   
                    for (String key : NeuronNet.neuronet.neurons.keySet()) {
                    	
                           
                            double[] weights = new double[73];
                            
                            Element neuron = rootNode.getChild("Neuron-" + key);
                           
                            for (int i = 0; i < 73; i++){
                                    Element weight = neuron.getChild("Weight-" + i);
                                    weights[i] = Double.parseDouble(weight.getText());
                            }
                            neurons.put(key, weights);
                    }
                   
                    return neurons;
                   
            } catch (JDOMException e) {
            } catch (IOException e) {
            }
            return null;
    }
	
	public boolean hasConverged(){
            if (3 <= m){
            	save();
            	resetM();
                return true;
            }
            return false;
    }
	

	private double[] getWeights(){
		return weights;
	}
	
	public static String receive(double [] inputs){
		String let = "?";
        double first = 0,second = 0;
        double out;
       
        Map<String, double[]> neurons = read();             
        for (Entry<String, double[]> entry : neurons.entrySet()) {
        	
        	String letter = entry.getKey();
            double[] tempWeights = entry.getValue();
            double netInput = 0;
            for (int i = 0; i < 72; i++){
            	netInput += inputs[i] * tempWeights[i];
            }
            
            netInput += 1 * tempWeights[72]; 
            out = (1/ (1 + Math.pow(Math.E, -1 * lambda * netInput)));
        
            if (out > first){
            		second = first;
            		first = out;
                    let = letter;
            }
        }
        
        if(Math.abs(first - second) <= error){
        	let = "?";
        }
        return let;
	}
}
