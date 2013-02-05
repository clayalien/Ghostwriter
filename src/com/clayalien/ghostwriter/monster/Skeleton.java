package com.clayalien.ghostwriter.monster;

import com.clayalien.ghostwriter.R;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class Skeleton extends Monster{
	
	protected float speed = 1f;
	protected float attackTime = 50f;
	protected int damage = 5;

	public Skeleton(int x, int y, String target, Resources resources) {
		super(x, y, target, resources);
		bitmap = BitmapFactory.decodeResource(resources, R.drawable.skel);
	}

}
