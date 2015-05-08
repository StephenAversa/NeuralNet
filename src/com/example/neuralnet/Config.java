package com.example.neuralnet;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Config extends Activity {
	String[] load;
	public static final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
	public static final String file = "NeuralNet.xml";
	float patNum, c, lambda, epsilon, n;
	String[] patterns;
	String pats;

	/*onCreate
	Reads from the raw file and initalizes all the value.*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		
		readFile();
		pats = "";
		for (int i = 0; i < patterns.length; i++){
			pats += patterns[i] + " ";
		}
		EditText num = (EditText) findViewById(R.id.num);
		EditText check1 = (EditText) findViewById(R.id.check1);
		EditText check2 = (EditText) findViewById(R.id.check2);
		EditText check3 = (EditText) findViewById(R.id.check3);
		EditText check4 = (EditText) findViewById(R.id.check4);
		num.setText(Float.toString(patNum));
		check1.setText(pats);
		check2.setText(Float.toString(c));
		check3.setText(Float.toString(lambda));
		check4.setText(Float.toString(n));
	}
	/*takeValues
	Retrieve all the information put in by the user.*/
	void takeValues(){
		String temp;
		EditText num = (EditText) findViewById(R.id.num);
		EditText check2 = (EditText) findViewById(R.id.check2);
		EditText check3 = (EditText) findViewById(R.id.check3);
		EditText check4 = (EditText) findViewById(R.id.check4);
		temp = num.getText().toString();
		patNum = Float.parseFloat(temp);
		temp = check2.getText().toString();
		c = Float.parseFloat(temp);
		temp = check3.getText().toString();
		lambda = Float.parseFloat(temp);
		temp = check4.getText().toString();
		n = Float.parseFloat(temp);
	}

	/*takeValues
	Reads the information from the raw file and parses it.*/
	public void readFile(){
		String txt;
    	try {
    		File sdDir = android.os.Environment.getExternalStorageDirectory();      
    		File Dir = new File(sdDir,"vals.txt");

    		//if (!Dir.exists()) {
    		   // Dir.mkdirs();
        		InputStream inputStream = getResources().openRawResource(R.raw.nums);
                byte[] b = new byte[inputStream.available()];
                inputStream.read(b);
                txt = (new String(b));

                txt= txt.replace("\n", " ").replace("\r", " ");
                splitSaveString(txt);
                assignVals(load);
    		//}else{
    			
			   /* FileInputStream fIn = new FileInputStream(Dir);
			    InputStreamReader myInReader = new InputStreamReader(fIn);
			    
			    char[] buffer = new char[1000];
			    myInReader.read(buffer);
			    splitString(new String(buffer));
			    
			    fIn.close();
			    myInReader.close();*/
    			
    		//}


        } catch (Exception e) {
            txt = ("Error: can't show help.");
        }
	}
	
	/*splitSaveString
	Splits the string of the new file.*/
    public void splitSaveString (String load_name){
    	String [] temp = new String[110];
    	load = load_name.split(" ");
    	}
	/*splitString
	Splits the string of the raw file.*/
    public void splitString (String load_name){
    	load = load_name.split("\n");
    	load = load_name.split(" ");
    	for (int i = 0; i < load.length;i++){
    		load = load_name.split(" ");
    		load[i] = load[i].replace("\n", " ").replace("\r", " ");
    	}
 		}
	/*assignVals
	Use the information read in and assign it to the necessary variables.*/
    public void assignVals(String[] load){
    	String name;
    	
    	patNum = Integer.parseInt(load[3]);
    	patterns = new String[(int) patNum];
    	int count = 0;
    	for (int i = 0; i < load.length; i ++){
    		name = load[i];

    		
    		if (load[i].equals("Patterns:")){
    			i+=2;
    			do{
    				name = load[i];
    	    		if (!name.equals("")){
	    				patterns[count] = name;
	    				count++;
    	    		}
    	    		i++;
    			}while (count < patNum);
    			
    		}
    		if (load[i].equals("c:")){
    			c = Float.parseFloat(load[i+1]);
    		}
    		if (load[i].equals("lambda:")){
    			lambda = Float.parseFloat(load[i+1]);
    		}
    		if (load[i].equals("epsilon:")){
    			epsilon = Float.parseFloat(load[i+1]);
    		}
    		if (load[i].equals("n:")){
    			n = Float.parseFloat(load[i+1]);
    		}
    		
    		
    	}
    }
    
	/*writeToFile
	Write the updated values to a text file.*/
    public void writeToFile(View v){
    	boolean mExternalStorageAvailable = false;
    	@SuppressWarnings("unused")
    	boolean mExternalStorageWriteable = false;
    	String state = Environment.getExternalStorageState();

    	if (Environment.MEDIA_MOUNTED.equals(state))
    	{
    	    mExternalStorageAvailable = mExternalStorageWriteable = true;
    	} 
    	else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) 
    	{
    	    mExternalStorageAvailable = true;
    	    mExternalStorageWriteable = false;
    	} 
    	else 
    	{
    	    mExternalStorageAvailable = mExternalStorageWriteable = false;
    	}
    	
    	if (mExternalStorageAvailable)
    	{
    		File dir =new File(android.os.Environment.getExternalStorageDirectory(),"NMM Files");
    	    if(!dir.exists())
    	    {
    	           dir.mkdirs();
    	    }    
    	    try
    	    {
    		    File f = new File(dir+File.separator+"vals.txt");

    		    FileOutputStream fOut = new FileOutputStream(f);
    		    OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
    		    myOutWriter.append("Number of Patterns: " + patNum + "\n");
    		    myOutWriter.append("\n");
    		    myOutWriter.append("Patterns: \n");
    		    for (int i = 0; i < patterns.length; i++){
    		    	myOutWriter.append(patterns[i] + "\n");
    		    }
    		    myOutWriter.append("\n");
    		    myOutWriter.append("c: " + c + "\n");
    		    myOutWriter.append("lambda: " + lambda + "\n");
    		    myOutWriter.append("epsilon: " + epsilon + "\n");
    		    myOutWriter.append("n: " + n + "\n");
    		    myOutWriter.close();
    		    fOut.close();
    		    
    	    	Intent startGame = new Intent(Config.this, MainActivity.class);
    	    	startActivity(startGame);
    	   
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
    	}
    }
	
	
}
