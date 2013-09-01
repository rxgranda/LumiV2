/*==============================================================================
Copyright (c) 2012-2013 QUALCOMM Austria Research Center GmbH.
All Rights Reserved.

@file
    CloudRecoSplashScreen.java

@brief
    Splash screen Activity for the CloudReco sample application
    Splash screen is displayed for 2 seconds, then the About Screen is shown.

==============================================================================*/

package com.qualcomm.QCARSamples.CloudRecognition;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;


public class CloudRecoSplashScreen extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Sets the Splash Screen Layout
        setContentView(R.layout.splash_screen);

        // Generates a Handler to launch the About Screen
        // after 2 seconds
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            public void run()
            {
                // Starts the About Screen Activity
                startActivity(new Intent(CloudRecoSplashScreen.this,
                        AboutScreen.class));
            }
        }, 2000L);
    }


    public void onConfigurationChanged(Configuration newConfig)
    {
        // Manages auto rotation for the Splash Screen Layout
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.splash_screen);
    }
}
