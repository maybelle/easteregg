package com.spawnmore.hiddenobject.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * The dreaded Utils class, a.k.a. "I have no idea where to put this, so DAMMIT SCREW HIGH COHESION
 * LOW COUPLING JUST TAKE IT ALL"
 * 
 * @author maybelle
 *
 */
public class Utils
{
    public static boolean isDebugBuild(Context context)
    {
        return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
}
