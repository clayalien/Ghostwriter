package com.clayalien.ghostwriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GhostwriterActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        final Button storyButton = (Button) findViewById(R.id.storyButton);
        storyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Context context = getApplicationContext();
            	CharSequence text = "Story mode comming soon!";
            	int duration = Toast.LENGTH_SHORT;

            	Toast toast = Toast.makeText(context, text, duration);
            	toast.show();

            }
        });
        
        final Button infButton = (Button) findViewById(R.id.infButton);
        infButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(GhostwriterActivity.this, NewInfActivity.class);
            	GhostwriterActivity.this.startActivity(myIntent);

            }
        });
    }
}