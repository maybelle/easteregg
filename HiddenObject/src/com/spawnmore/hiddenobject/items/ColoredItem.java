package com.spawnmore.hiddenobject.items;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

public class ColoredItem extends BaseItem
{
    private int color;
    
    public ColoredItem(int color, int left, int right, int top, int bottom)
    {
        super(left, right, top, bottom);
        this.color = color;
        
        this.description = color + "";
    }

    public ColoredItem(int color, Rect rect)
    {
        super(rect);
        this.color = color;
    }

    @Override
    public void drawSelf(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(this.color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(location, paint);
    }
}
