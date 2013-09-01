/*==============================================================================
Copyright (c) 2012-2013 QUALCOMM Austria Research Center GmbH.
All Rights Reserved.

@file
    AboutScreen.java

@brief
    About Screen Activity for the CloudReco sample application

==============================================================================*/

package com.qualcomm.QCARSamples.CloudRecognition;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/** The activity for showing the About text in the CloudReco sample. */
public class AboutScreen extends Activity implements OnClickListener
{
    private TextView mAboutText;
    private Button mStartButton;


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_screen);

        mAboutText = (TextView) findViewById(R.id.about_text);
        mAboutText.setText(Html.fromHtml(getString(R.string.about_text)));
        mAboutText.setMovementMethod(LinkMovementMethod.getInstance());

        // Setups the link color
        mAboutText.setLinkTextColor(getResources().getColor(
                R.color.holo_light_blue));

        mStartButton = (Button) findViewById(R.id.button_start);
        mStartButton.setOnClickListener(this);
    }


    /** Starts the CloudReco main activity */
    private void startARActivity()
    {
        Intent i = new Intent(this, CloudReco.class);
        startActivity(i);
    }
    
    private void startMainMenuActivity()
    {
        Intent i = new Intent(this, MenuLumi.class);
        startActivity(i);
    }


    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.button_start:
            startMainMenuActivity();
            break;
        }
    }
}
