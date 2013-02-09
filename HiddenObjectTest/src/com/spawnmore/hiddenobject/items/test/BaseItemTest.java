package com.spawnmore.hiddenobject.items.test;

import android.graphics.Point;
import android.test.AndroidTestCase;

import com.spawnmore.hiddenobject.items.ColoredItem;

public class BaseItemTest extends AndroidTestCase
{
    private ColoredItem squareItem;

    protected void setUp() throws Exception
    {
        super.setUp();
        squareItem = new ColoredItem(0, 5, 10, 15, 20);
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    public void testIsPointInItem()
    {
        Point inSquare = new Point();
        inSquare.x = 8;
        inSquare.y = 18;
        assertTrue(squareItem.isPointInItem(inSquare));
        
        Point notInSquareY = new Point();
        notInSquareY.x = 8;
        notInSquareY.y = 10;
        assertFalse(squareItem.isPointInItem(notInSquareY));
    }
}
