package com.clayalien.ghostwriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        
        int score = this.getIntent().getExtras().getInt("EXTRA_SCORE");
        final TextView scoreView = (TextView) findViewById(R.id.scoreView);
        scoreView.setText("Score: "+score);

        
        final Button exitButton = (Button) findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(GameOverActivity.this, GhostwriterActivity.class);
            	GameOverActivity.this.startActivity(myIntent);

            }
        });
    }

}
