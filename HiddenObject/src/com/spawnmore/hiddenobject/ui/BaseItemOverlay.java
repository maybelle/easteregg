package com.spawnmore.hiddenobject.ui;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

import com.spawnmore.hiddenobject.items.BaseItem;

public class BaseItemOverlay extends View implements SensorEventListener
{
    private static final int MILLIS_DELAY_BETWEEN_SENSOR_EVENTS = 1000;
    
    private List<BaseItem> listItems;
    private Paint debugTextPaint;
    
    private SensorManager sensorManager;
    private Sensor accelSensor;
    private float[] gravity;
    private float[][] rawAcceleration;
    private float[] normalizedAcceleration;
    private int dataIndex;
    private long lastDrawTime;
    
    
    public BaseItemOverlay(Context context, List<BaseItem> items)
    {
        super(context);
        this.listItems = items;

        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        
        this.gravity = new float[3];
        this.rawAcceleration = new float[3][10];
        this.normalizedAcceleration = new float[3];
        this.dataIndex = 0;
        
        this.sensorManager.registerListener(this, this.accelSensor, SensorManager.SENSOR_DELAY_GAME);
        
        debugTextPaint = new Paint();
        debugTextPaint.setColor(Color.WHITE);
        debugTextPaint.setStyle(Style.FILL);
        
        this.lastDrawTime = System.currentTimeMillis();
    }
    
    @Override
    public void onDraw(Canvas canvas)
    {
        for(BaseItem item : this.listItems)
        {
            item.drawSelf(canvas);
        }
        
        canvas.drawText("x: " + normalizedAcceleration[0] + " y: " + normalizedAcceleration[1] + " z: " + normalizedAcceleration[2], 15, 300, debugTextPaint);
        
        super.onDraw(canvas);
    }
    

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        /*
        final float alpha = 0.8f;

        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];
        
        acceleration[0][dataIndex] = event.values[0] - gravity[0];
        acceleration[1][dataIndex] = event.values[1] - gravity[1];
        acceleration[2][dataIndex] = event.values[2] - gravity[2];
        */
        
        rawAcceleration[0][dataIndex] = event.values[0];
        rawAcceleration[1][dataIndex] = event.values[1];
        rawAcceleration[2][dataIndex] = event.values[2];
        
        this.normalizedAcceleration[0] = 0;
        this.normalizedAcceleration[1] = 0;
        this.normalizedAcceleration[2] = 0;
        
        for (int i = 0; i < 10; ++i)
        {
            this.normalizedAcceleration[0] = this.normalizedAcceleration[0] + (this.rawAcceleration[0][i] * 0.50f);
            this.normalizedAcceleration[1] = this.normalizedAcceleration[1] + (this.rawAcceleration[1][i] * 0.50f);
            this.normalizedAcceleration[2] = this.normalizedAcceleration[2] + (this.rawAcceleration[2][i] * 0.50f);
        }
        
        this.dataIndex = (this.dataIndex + 1) % 10;
        
        if (System.currentTimeMillis() - this.lastDrawTime > MILLIS_DELAY_BETWEEN_SENSOR_EVENTS)
        {
            this.lastDrawTime = System.currentTimeMillis();
            this.invalidate();
        }
    }
    

}
