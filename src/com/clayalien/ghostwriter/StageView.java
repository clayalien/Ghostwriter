package com.clayalien.ghostwriter;

import java.util.List;

import com.clayalien.ghostwriter.monster.Monster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.SurfaceView;

class StageView extends SurfaceView {
	
	protected Paint paint;
	protected Bitmap background;
	

    public StageView(Context context) {
        super(context);
        
        paint = new Paint();
        background = BitmapFactory.decodeResource(getResources(), R.drawable.hallwaysmall);
    }
    
    protected void doDraw(Canvas c, List<Monster> monsters, int score, int fps, int life) {

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

}