package com.spawnmore.hiddenobject.items;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public abstract class BaseItem
{	
    protected String description;
	protected Rect location;
	
	public BaseItem(Rect location)
	{
		this.location = location;
	}
	
	public BaseItem(int left, int right, int top, int bottom)
	{
		this.location = new Rect();
		this.location.left = left;
		this.location.right = right;
		this.location.top = top;
		this.location.bottom = bottom;
	}
	
	public boolean isPointInItem(Point pt)
	{
		if (location.left < pt.x && 
			location.right > pt.x &&
			location.top < pt.y &&
			location.bottom > pt.y)
		{
			return true;
		}
		
		return false;
	}
	
	public void setDescription(String description)
	{
	    this.description = description;
	}
	
	public String getDescription()
	{
	    return this.description;
	}
	
	/**
	 * Draw this item on the given Canvas
	 * 
	 * @param canvas
	 */
	public abstract void drawSelf(Canvas canvas);

}
