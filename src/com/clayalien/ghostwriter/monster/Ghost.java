package com.clayalien.ghostwriter.monster;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.clayalien.ghostwriter.R;

public class Ghost extends Monster {
	
	protected float speed = 0.5f;
	protected float attackTime = 2f;
	protected int damage = 1;

	public Ghost(int x, int y, String target, Resources resources) {
		super(x, y, target, resources);
		bitmap = BitmapFactory.decodeResource(resources, R.drawable.ghost);
	}
	
}

