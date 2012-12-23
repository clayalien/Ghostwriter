package com.clayalien.ghostwriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewInfActivity extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_infinity);
        
        final Button easyButton = (Button) findViewById(R.id.easyButton);
        easyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(NewInfActivity.this, InfinityGameActivity.class);
            	NewInfActivity.this.startActivity(myIntent);

            }
        });
        
        final Button normalButton = (Button) findViewById(R.id.normalButton);
        normalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(NewInfActivity.this, InfinityGameActivity.class);
            	NewInfActivity.this.startActivity(myIntent);

            }
        });
        
        final Button hardButton = (Button) findViewById(R.id.hardButton);
        hardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(NewInfActivity.this, InfinityGameActivity.class);
            	NewInfActivity.this.startActivity(myIntent);

            }
        });
    }

}
