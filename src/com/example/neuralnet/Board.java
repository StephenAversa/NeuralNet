package com.example.neuralnet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnTouchListener;

public class Board extends Activity implements OnTouchListener{
	int count;
	Neuron corNeuron = null;
	public ImageView [] grid;
	public double [] input = new double [80];
	public int trainCount = 0;
	String letter;
	TextView let,cnt;

	/*onCreate
	Responsible for creating the grid, setting the necessary UI elements, and creating all of
	the onTouch listeners for the buttons.*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_board);
		
		grid = createGrid(8,10);
		Intent intent = getIntent();
		letter = intent.getStringExtra("LETTER");
		init();
		count = 0;
		cnt = (TextView) findViewById(R.id.countID);
		cnt.setText("0");
		
		let = (TextView) findViewById(R.id.letterID);
		let.setText(letter);
		
		for (int i = 0; i < 80; i++) {
			grid[i].setOnTouchListener(new View.OnTouchListener(){
			public boolean onTouch(View v, MotionEvent event) {
			   
			   switch(event.getAction()){
			   case MotionEvent.ACTION_MOVE:
				   ImageView img = gridUpdate(event.getRawX(), event.getRawY()); //find button dragged over	    
				   if (img == null){
				    	return true;
				   }
				   img.setColorFilter(Color.BLACK,PorterDuff.Mode.MULTIPLY);
				   break;
			       }
			   return true;
			}});
		}
	}
	
	/*createGrid
	Creates the grid of squares to be used by the user to pass in letters.*/
	ImageView[] createGrid(int x, int y){
		int size = x * y;
		ImageView[] bs = new ImageView[size];
		int count = 0;

		for (int i = 0; i < y; i ++){
			for (int j = 0; j < x; j ++){
				String name = "t" + j + i;
				int changeId = getResources().getIdentifier(name, "id", getPackageName());
				bs[count] = (ImageView) findViewById(changeId);
				count++;
			}
		}
		return bs;
	}
	
	/*init
	Initalizes the drawing board.*/
	private void init(){
		for(int i=0; i<80; i++){
			input[i] = 0;
			grid[i].setColorFilter(Color.WHITE,PorterDuff.Mode.MULTIPLY);
		}
		
		corNeuron = NeuronNet.neuronet.neurons.get(letter);
	}
	
	/*gridUpdate
	Checks for where the user is dragging on the screen to accurately update the buttons to flip.*/
	public ImageView gridUpdate(float x , float y){
		int [] loc = new int[2];
		for(int i =0; i<80; i++){
			grid[i].getLocationOnScreen(loc);
			
			if(loc[0] <= x ){
					if(x <= (loc[0] + grid[i].getWidth())){
						if(loc[1] <= y ){
							if(y <= (loc[1] + grid[i].getHeight())){
								input[i] = 1;
							    return grid[i];
							}
						}
					}
				}
			}
		return null;
	}
	
	/*trainBut
	Calls the train function to train the AI. Then attempts to converge.*/
	public void trainBut(View v){
		train();
		checkConverge();
		count++;
		cnt = (TextView) findViewById(R.id.countID);
		cnt.setText(Integer.toString(count));
		
	}
	
	/*trainBut2
	Calls the train function to train the AI five times.*/
	public void trainBut2(View v){
		for (int i = 0; i < 5; i++){
			train();
		}
		checkConverge();
		count+=5;
		cnt = (TextView) findViewById(R.id.countID);
		cnt.setText(Integer.toString(count));
	}
	
	/*reset
	Resets the letter values.*/
	public void reset(View v){
		wipe();
	}
	
	/*erase
	Resets the grid the user draws in.*/
	public void erase(View v){
		for(int i=0; i<80; i++){
			grid[i].setColorFilter(Color.WHITE,PorterDuff.Mode.MULTIPLY);
			input[i] = 0;
		}
	}
	
	/*checkConverge()
	Checks to see if the neuron has converged.*/
	public void checkConverge()
	{
		if(corNeuron.hasConverged()){
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
		}
	}
	
	/*train()
	Trains the neuron.*/
	public void train(){
		for (Neuron neuron : NeuronNet.neuronet.neurons.values()) {          
            if(neuron != corNeuron){
            	neuron.train(input, 0);
            }else{
            	neuron.train(input, 1);
            }
		}
		trainCount++;
	}
	
	public void wipe(){
		NeuronNet.neuronet.resetAllNeuronM();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
