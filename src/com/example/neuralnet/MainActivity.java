package com.example.neuralnet;


import com.example.neuralnet.Board;
import com.example.neuralnet.MainActivity;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends ActionBarActivity {
	
	String text;
	Spinner spinner;

	/*onCreate
	Responsible for creating the front screen, supplying the spinner with all avaible options,
	and setting up one of the buttons required for testing.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        spinner = (Spinner) findViewById(R.id.spinner1);
        
        String spinray[] = {"alpha", "beta", "gamma","delta","epsilon","zeta","eta","theta","iota","kappa","lambda","mu", "nu","xi","omicron","pi","rho","sigma","tau","upsilon","phi","chi","psi","omega","cap1","cap2"};
        ArrayAdapter<String> gameKindArray= new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item, spinray);
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(gameKindArray); 
        
        text = spinner.getSelectedItem().toString();
        
        Button button= (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test(v);
            }
        });

    }

	/*config
	Responsible for sending the user to the config activity.*/
    public void config(View firstView){
        text = spinner.getSelectedItem().toString();
    	Intent startGame = new Intent(MainActivity.this, Config.class);
    	startActivity(startGame);
    	
    }
	/*train
	Responsible for sending the user to the training activity.*/
    public void train(View firstView){
        text = spinner.getSelectedItem().toString();
    	Intent startGame = new Intent(MainActivity.this, Board.class);
        startGame.putExtra( "LETTER", text);
    	startActivity(startGame);
    	
    }
	/*test
	Responsible for sending the user to the testing activity.*/
    public void test(View firstView){
    	Intent startGame = new Intent(MainActivity.this, Test.class);
    	startActivity(startGame);
    	
    }
    
	/*ex
	Exits the app*/
    public void ex(View firstView){
    	System.exit(1);
    	
    }
    


}
