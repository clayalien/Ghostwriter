package com.clayalien.ghostwriter.monster;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

public abstract class Monster {
	
	protected float x,y;
	protected String target;
	protected Bitmap bitmap;
	protected float speed = 0.5f;
	protected float attackTime = 20f;
	protected float nextAttack = 20f;
	protected int damage = 1;
	
		
	public Monster(int x, int y, String target, Resources resources)
	{
		this.x = x;
		this.y = y;
		this.target = target;
		
	}

	public void draw(Canvas c, Paint paint) 
	{
	    paint.setStyle(Paint.Style.FILL);
	    
	    c.drawBitmap(bitmap,x ,y , paint);
	   	    
	    paint.setAntiAlias(true);
	    paint.setTextSize(20);
	    paint.setTextAlign(Align.CENTER);
	    
	    
	    paint.setColor(Color.BLACK);
	    paint.setStyle(Paint.Style.STROKE);
	    paint.setTypeface(Typeface.DEFAULT_BOLD);
	    paint.setStrokeWidth(2);
	    c.drawText(target, x + (bitmap.getWidth()/2), y + bitmap.getHeight() + 2 , paint);
	    
	    paint.setColor(Color.RED);
	    paint.setStyle(Paint.Style.FILL);
	    paint.setStrokeWidth(0);
	    c.drawText(target, x + (bitmap.getWidth()/2), y + bitmap.getHeight() + 2 , paint);
	    
	    if (nextAttack < 5) {
	    	paint.setColor(Color.RED);
	    	paint.setStyle(Paint.Style.STROKE);
		    paint.setStrokeWidth(2);
	    	c.drawRect(x, y, x + bitmap.getWidth(), y +bitmap.getHeight(), paint);
	    }
	       
	}

	public boolean check(String test) {
		return target.equalsIgnoreCase(test);
	}

	public int update() {
		if (y < 200){
			y += speed ;
			return 0;
		} 
		if (nextAttack-- <= 0) {
			nextAttack = attackTime;
			return damage;
		}
		return 0;
	}
	    
	    
}
