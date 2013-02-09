package com.spawnmore.hiddenobject.ui;

import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.widget.FrameLayout;

import com.spawnmore.hiddenobject.camera.CameraUtils;
import com.spawnmore.hiddenobject.camera.ui.CameraSurface;
import com.spawnmore.hiddenobject.items.BaseItem;

public class FinderView extends FrameLayout
{
    private Camera camera;
    private CameraSurface cameraView;
    private BaseItemOverlay itemOverlayView;
    private List<BaseItem> listItems;

    public FinderView(Context context, List<BaseItem> items)
    {
        super(context);
        
        this.listItems = items;
        
        this.camera = CameraUtils.getCameraInstance();
        this.cameraView = new CameraSurface(context, this.camera);
        this.addView(this.cameraView);
        
        this.itemOverlayView = new BaseItemOverlay(context, this.listItems);
        this.addView(this.itemOverlayView, new LayoutParams 
                            (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        
    }

}
