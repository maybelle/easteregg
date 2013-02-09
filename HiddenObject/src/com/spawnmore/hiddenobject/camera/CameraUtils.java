package com.spawnmore.hiddenobject.camera;

import android.hardware.Camera;
import android.util.Log;

public class CameraUtils
{
    public static Camera getCameraInstance()
    {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            Log.e(CameraUtils.class.getName(), "Error opening camera: " + e.getMessage());
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}
