package com.spawnmore.hiddenobject.ui;

import java.util.List;

import android.annotation.SuppressLint;
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
import com.spawnmore.hiddenobject.util.Utils;

@SuppressLint("ViewConstructor")
public class BaseItemOverlay extends View implements SensorEventListener
{
    private static final int MILLIS_DELAY_BETWEEN_SENSOR_EVENTS = 1000;
    private static final int NUM_DATA_POINT = 10;
    
    private List<BaseItem> listItems;
    private Paint debugTextPaint;
    
    private SensorManager sensorManager;
    private Sensor accelSensor;
    private Sensor magnetSensor;
    private float[][] rawAcceleration;      // moving window of NUM_DATA_POINT values that we can average to get rid of some noise
    private float[][] rawMagnetometer;      // moving window of NUM_DATA_POINT values that we can average to get rid of some noise
    private float[] normalizedAcceleration;     // averaged values
    private float[] normalizedMagnetometer;     // averaged values

    private float[] rawRotationMatrix = new float[9];
    private float[] rotationMatrix = new float[9];
    private float[] orientation = new float[3];

    private int accelDataIndex = 0;
    private int magnetDataIndex = 0;
    private long lastDrawTime;
    
    
    public BaseItemOverlay(Context context, List<BaseItem> items)
    {
        super(context);
        this.listItems = items;

        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.accelSensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.magnetSensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    
        this.rawAcceleration = new float[3][NUM_DATA_POINT];
        this.normalizedAcceleration = new float[3];
        this.rawMagnetometer = new float[3][NUM_DATA_POINT];
        this.normalizedMagnetometer = new float[3];
        
        this.sensorManager.registerListener(this, this.accelSensor, SensorManager.SENSOR_DELAY_GAME);
        this.sensorManager.registerListener(this, this.magnetSensor, SensorManager.SENSOR_DELAY_GAME);
        
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
        
        if (Utils.isDebugBuild(getContext()))
        {
	        canvas.drawText("x: " + orientation[0] + " y: " + orientation[1] + " z: " + orientation[2], 15, 300, debugTextPaint);
        }
        
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
        if (event.sensor == this.accelSensor)
        {
            this.rawAcceleration[0][accelDataIndex] = event.values[0];
            this.rawAcceleration[1][accelDataIndex] = event.values[1];
            this.rawAcceleration[2][accelDataIndex] = event.values[2];
            this.accelDataIndex = (this.magnetDataIndex + 1) % NUM_DATA_POINT;
        }
        else if (event.sensor == this.magnetSensor)
        {
            this.rawMagnetometer[0][magnetDataIndex] = event.values[0];
            this.rawMagnetometer[1][magnetDataIndex] = event.values[1];
            this.rawMagnetometer[2][magnetDataIndex] = event.values[2];
            this.magnetDataIndex = (this.magnetDataIndex + 1) % NUM_DATA_POINT;
        }
        
        this.normalizedAcceleration[0] = 0;
        this.normalizedAcceleration[1] = 0;
        this.normalizedAcceleration[2] = 0;
        
        for (int i = 0; i < 10; ++i)
        {
            this.normalizedAcceleration[0] = this.normalizedAcceleration[0] + this.rawAcceleration[0][i];
            this.normalizedAcceleration[1] = this.normalizedAcceleration[1] + this.rawAcceleration[1][i];
            this.normalizedAcceleration[2] = this.normalizedAcceleration[2] + this.rawAcceleration[2][i];
        }
        
        for (int i = 0; i < 3; ++i)
        {
            this.normalizedAcceleration[i] /= NUM_DATA_POINT;
        }
        
        
        this.normalizedMagnetometer[0] = 0;
        this.normalizedMagnetometer[1] = 0;
        this.normalizedMagnetometer[2] = 0;
        
        for (int i = 0; i < 10; ++i)
        {
            this.normalizedMagnetometer[0] = this.normalizedMagnetometer[0] + this.rawMagnetometer[0][i];
            this.normalizedMagnetometer[1] = this.normalizedMagnetometer[1] + this.rawMagnetometer[1][i];
            this.normalizedMagnetometer[2] = this.normalizedMagnetometer[2] + this.rawMagnetometer[2][i];
        }
        
        for (int i = 0; i < 3; ++i)
        {
            this.normalizedMagnetometer[i] /= NUM_DATA_POINT;
        }
        
        SensorManager.getRotationMatrix(this.rawRotationMatrix, null, normalizedAcceleration, normalizedMagnetometer);
        SensorManager.remapCoordinateSystem(this.rawRotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, this.rotationMatrix);
        SensorManager.getOrientation(this.rotationMatrix, this.orientation);
        
        for (int i = 0; i < this.normalizedAcceleration.length; ++i)
        {
            this.orientation[i] = (float) Math.toDegrees(this.orientation[i]);
        }
        
        if (Utils.isDebugBuild(getContext()))
        {
            if (System.currentTimeMillis() - this.lastDrawTime > MILLIS_DELAY_BETWEEN_SENSOR_EVENTS)
            {
                this.lastDrawTime = System.currentTimeMillis();
                this.invalidate();
            }
        }
    }
    

}
