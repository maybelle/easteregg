package com.spawnmore.hiddenobject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IntroActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.intro_view);
        
        /*final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);
        
        setupFullscreenControls(controlsView, contentView);*/
    }
    
    public void onClickedPlay(View view)
    {
        Intent i = new Intent(this, FinderActivity.class);
        startActivity(i);
    }
}
