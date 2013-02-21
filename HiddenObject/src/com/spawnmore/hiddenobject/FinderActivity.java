package com.spawnmore.hiddenobject;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.spawnmore.hiddenobject.items.BaseItem;
import com.spawnmore.hiddenobject.items.ColoredItem;
import com.spawnmore.hiddenobject.ui.FinderView;

public class FinderActivity extends Activity implements GestureDetector.OnGestureListener
{
    private GestureDetector gestureDetector;
	private FinderView finderView;
	private List<BaseItem> listItems;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		loadObjects();

		this.finderView = new FinderView(this, this.listItems);
		this.setContentView(this.finderView);
		
		this.gestureDetector = new GestureDetector(this, this);
	}
	
	@Override
	protected void onResume()
	{
	    super.onResume();
	}
	
	@Override
	protected void onPause()
	{
	    super.onPause();
	}
	
	private void loadObjects()
	{
        this.listItems = new ArrayList<BaseItem>();
        
	    this.listItems.add(new ColoredItem(Color.MAGENTA, 5, 45, 20, 60));
	    this.listItems.add(new ColoredItem(Color.CYAN, 100, 145, 220, 260));
	}
	
	
	@Override
    public boolean onTouchEvent(MotionEvent event)
	{
        return gestureDetector.onTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent arg0)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
            float arg3)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onLongPress(MotionEvent arg0)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
            float arg3)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event)
    {
        Point p = new Point();
        p.x = (int) Math.round(event.getX());
        p.y = (int) Math.round(event.getY());
        for (BaseItem item : this.listItems)
        {
            if (item.isPointInItem(p))
            {
                Toast.makeText(this, "Tapped on object! " + item.getDescription(), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        
        Toast.makeText(this, "No object found", Toast.LENGTH_SHORT).show();
        
        return false;
    }

}
