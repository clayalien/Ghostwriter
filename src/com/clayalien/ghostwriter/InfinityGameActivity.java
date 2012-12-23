package com.clayalien.ghostwriter;

import java.util.Random;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class InfinityGameActivity extends Activity{
	
	private Integer score = 0;
	private String[] words;
	private int max;
	private Random rnd = new Random();
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        
        final TextView targetText = (TextView)findViewById(R.id.targetText);
        final EditText firingText = (EditText)findViewById(R.id.firingText);
        final TextView scoreText = (TextView)findViewById(R.id.scoreText);
        
        Resources res = getResources();
        words = res.getStringArray(R.array.word_array);
        max = words.length;
        
        firingText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
            	String fired = s.toString();
            	String target = (String) targetText.getText();
            	if (target.equalsIgnoreCase(fired)) {
            		int next = rnd.nextInt(max);
            		score++;
            		scoreText.setText(String.valueOf(score));
            		targetText.setText(words[next]);
            		s.clear();
            	}
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        }); 
    }
    
    
}
