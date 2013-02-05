package com.clayalien.ghostwriter.old;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.clayalien.ghostwriter.R;
import com.clayalien.ghostwriter.R.array;
import com.clayalien.ghostwriter.R.drawable;
import com.clayalien.ghostwriter.monster.Ghost;
import com.clayalien.ghostwriter.monster.Monster;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class AnimationView extends SurfaceView implements SurfaceHolder.Callback {
	protected Paint paint;
	protected List<Monster> monsters = new ArrayList<Monster>();
	protected Random rnd = new Random();
	protected String[] words;
	protected int max;
	protected int score = 0;
	protected int life = 100;
	protected Bitmap background;
	
	
    class AnimationThread extends Thread {
    	
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

        /** How to display the text */
    	protected Paint textPaint;

        public AnimationThread(SurfaceHolder surfaceHolder) {
            mSurfaceHolder = surfaceHolder;

            /** Initiate the text painter */
            textPaint = new Paint();
            textPaint.setARGB(255,255,255,255);
            textPaint.setTextSize(32);
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
    	    		life = 100;
    	    		score = 0;
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
        	}

            mLastTime = now;
        }

        /**
         * Draws to the provided Canvas.
         */
        protected void doDraw(Canvas c) {

        	paint.setStyle(Paint.Style.FILL);

    	    // make the entire canvas white
    	    paint.setColor(Color.BLACK);
    	    c.drawPaint(paint);
    	    
    	    
    	    c.drawBitmap(background,0 ,0 , paint);
    	    
    	    for (Monster m: monsters) {
    	    	m.draw(c, paint);
    	    }
    	    
    	    paint.setColor(Color.WHITE);
    	    paint.setStyle(Paint.Style.FILL);
    	    paint.setAntiAlias(true);
    	    paint.setTextSize(15);
    	    
    	    paint.setTextAlign(Align.LEFT);
    	    
    	    c.drawText("Score: "+score+ "  fps: " +fps, 2, 20 , paint);
    	    
    	    paint.setColor(Color.BLACK);
    	    c.drawRect(434, 4, 536, 16, paint);
    	    paint.setColor(Color.GREEN);
    	    c.drawRect(435, 5, 435+life, 15, paint);
        }

        /**
         * So we can stop/pauze the game loop
         */
        public void setRunning(boolean b) {
            mRun = b;
        }      

    }

    /** The thread that actually draws the animation */
    protected AnimationThread thread;

    public AnimationView(Context context) {
        super(context);
        
        paint = new Paint();
		
		Resources res = getResources();
		words = res.getStringArray(R.array.word_array);
		background = BitmapFactory.decodeResource(getResources(), R.drawable.hallwaysmall);
		max = words.length;

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        // create thread only; it's started in surfaceCreated()
        thread = new AnimationThread(holder);

    }
    
    public void addMonster() {
		Monster m = new Ghost (rnd.nextInt(360),rnd.nextInt(100)+20,words[rnd.nextInt(max)],getResources());
        monsters.add(m);
	}
	
	public boolean checkMonsters(String test) {
		for (Monster m: monsters) {
	    	if (m.check(test)) {
	    		monsters.remove(m);
	    		addMonster();
	    		score++;
	    		return true;
	    	}
	    }
		return false;
	}

    /**
     * Obligatory method that belong to the:implements SurfaceHolder.Callback
     */

    /**
     * Callback invoked when the surface dimensions change.Â&nbsp;
     */
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * Callback invoked when the Surface has been created and is ready to be
     * used.
     */
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    /**
     * Callback invoked when the Surface has been destroyed and must no longer
     * be touched. WARNING: after this method returns, the Surface/Canvas must
     * never be touched again!
     */
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