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


public class Test extends Activity implements OnTouchListener {
	public ImageView [] grid;
	public double [] input = new double [80];
	public String character;
	Neuron correctNeuron = null;
	public int trainingCount = 0;
	String letter;
	TextView let;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		let = (TextView) findViewById(R.id.letterID);
		let.setText("");
		grid = createGrid(8,10);
		Intent intent = getIntent();
		letter = intent.getStringExtra("LETTER");
		letter = "alpha";
		init();
		
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
		
		correctNeuron = NeuronNet.neuronet.neurons.get(letter);
	}
	/*gridUpdate
	Checks for where the user is dragging on the screen to accurately update the buttons to flip.*/
	public ImageView gridUpdate(float x , float y)
	{
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
	/*erase
	Resets the grid the user draws in.*/
	public void erase(View v){
		for(int i=0; i<80; i++){
			grid[i].setColorFilter(Color.WHITE,PorterDuff.Mode.MULTIPLY);
			input[i] = 0;
		}
	}
	/*test
	Returns the symbol the user drew as accurately as possible.*/
	public void test(View v){
		String letter = Neuron.receive(input);
		let = (TextView) findViewById(R.id.letterID);
		String cur = let.getText().toString();
		String newCur = cur + letter + " ";
		let.setText(newCur);
	}
	
	/*done
	Returns to the main activity.*/
	public void done(View v){
	Intent startGame = new Intent(Test.this, MainActivity.class);
	finish();
	startActivity(startGame);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
