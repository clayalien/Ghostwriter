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
import android.view.View;

public class Draw2d extends View {
	private Paint paint;
	List<Monster> monsters = new ArrayList<Monster>();
	private Random rnd = new Random();
	private String[] words;
	private int max;
	private int score = 0;
	protected int life = 100;
	protected Bitmap background;

	public Draw2d(Context context) {
		super(context);
		paint = new Paint();
		
		Resources res = getResources();
		words = res.getStringArray(R.array.word_array);
		background = BitmapFactory.decodeResource(getResources(), R.drawable.hallwaysmall);
		max = words.length;
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
	    		this.invalidate();
	    		return true;
	    	}
	    }
		return false;
	}

    	@Override
	protected void onDraw(Canvas c){
	    super.onDraw(c);
	    
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
	    
	    c.drawText("Score: "+score, 2, 20 , paint);
	    
	    paint.setColor(Color.BLACK);
	    c.drawRect(434, 4, 536, 16, paint);
	    paint.setColor(Color.GREEN);
	    c.drawRect(435, 5, 435+life, 15, paint);
	}
    	
}