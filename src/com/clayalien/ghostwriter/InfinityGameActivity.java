package com.clayalien.ghostwriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.clayalien.ghostwriter.monster.Ghost;
import com.clayalien.ghostwriter.monster.Monster;
import com.clayalien.ghostwriter.monster.Skeleton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.SingleLineTransformationMethod;
import android.view.SurfaceHolder;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;

public class InfinityGameActivity extends Activity implements SurfaceHolder.Callback{
	
	protected List<Monster> monsters = new ArrayList<Monster>();
	protected Random rnd = new Random();
	protected String[] words;
	protected int max;
	protected int score = 0;
	protected int life = 100;
	protected int spawnEvery = 6;
	protected int timeSinceLast = 0;
	protected int maxSpawn = 3;
	
	 /** The thread that actually draws the animation */
    protected GameThread thread;
	private StageView d;
		
	
class GameThread extends Thread {
	
    	/** Are we running ? */
    	protected boolean mRun;

        /** Used to figure out elapsed time between frames */
    	protected long mLastTime;      

        /** Variables for the counter */
    	protected int frameSamplesCollected = 0;
    	protected int frameSampleTime = 0;
    	protected int fps = 0;

        /** Handle to the surface manager object we interact with */
    	protected SurfaceHolder mSurfaceHolder;

        public GameThread(SurfaceHolder surfaceHolder) {
            mSurfaceHolder = surfaceHolder;
        }

        /**
         * The actual game loop!
         */
        @Override
        public void run() {
            while (mRun) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    if (c == null){
                    	setRunning(false);
                    }
                    synchronized (mSurfaceHolder) {
                    	updatePhysics();
                        doDraw(c);
                    }
                }finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        /**
         * Figures the gamestate based on the passage of
         * realtime. Called at the start of draw().
         * Only calculates the FPS for now.
         */
        private void updatePhysics() {
        	
        	for (Monster m: monsters) {
    	    	life -= m.update();
    	    	if (life <= 0){
    	    		Intent intent = new Intent(InfinityGameActivity.this, GameOverActivity.class);
    	    		intent.putExtra("EXTRA_SCORE", score);
    	    		InfinityGameActivity.this.startActivity(intent);
    	    	}
    	    }
        	
        	
            long now = System.currentTimeMillis();

            if (mLastTime != 0) {

            	//Time difference between now and last time we were here
        		int time = (int) (now - mLastTime);
        		frameSampleTime += time;
        		frameSamplesCollected++;

        		//After 10 frames
        		if (frameSamplesCollected == 10) {

        			//Update the fps variable
	        		fps = (int) (10000 / frameSampleTime);

	        		//Reset the sampletime + frames collected
	        		frameSampleTime = 0;
	        		frameSamplesCollected = 0;
        		}
        		
        		timeSinceLast+= time;
        	}

            mLastTime = now;
            
            if (timeSinceLast > spawnEvery*1000) {
            	addMonster();
            	timeSinceLast = 0;
            }
        }

        /**
         * Draws to the provided Canvas.
         */
        protected void doDraw(Canvas c) {
        	
        	d.doDraw(c, monsters, score, fps, life);
        }

        /**
         * So we can stop/pauze the game loop
         */
        public void setRunning(boolean b) {
            mRun = b;
        }      

    }

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        

        Context c = getApplicationContext();
        LinearLayout v = new LinearLayout(c);
        v.setOrientation(LinearLayout.VERTICAL);
        
        Resources res = getResources();
		words = res.getStringArray(R.array.word_array);
		max = words.length;
        
        d = new StageView(this);
        d.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,370));
        
        final EditText firingText = new EditText(c);     
        firingText.setTransformationMethod(new SingleLineTransformationMethod());
        firingText.requestFocus();
        addMonster();
        firingText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
            	String fired = s.toString();
            	if (checkMonsters(fired)){
            		s.clear();
            	}
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        
     // register our interest in hearing about changes to our surface
        SurfaceHolder holder = d.getHolder();
        holder.addCallback(this);
        thread = new GameThread(holder);
        
        v.addView(d);
        v.addView(firingText);
        
        setContentView(v);

    }
    
    private void addMonster() {
    	if (monsters.size() >= maxSpawn){
    		return;
    	}
    	int choice = rnd.nextInt(2);
    	Monster m;
    	
    	if (choice == 0){
    		m = new Ghost (rnd.nextInt(360),-10,words[rnd.nextInt(max)], getResources());
    	} else {
    		m = new Skeleton (rnd.nextInt(360),-10,words[rnd.nextInt(max)], getResources());
    	}
		
        monsters.add(m);
	}
	
	private boolean checkMonsters(String test) {
		for (Monster m: monsters) {
	    	if (m.check(test)) {
	    		monsters.remove(m);
	    		score++;
	    		return true;
	    	}
	    }
		return false;
	}


    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }


    public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
    
    
    
}
