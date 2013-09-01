package com.giandroid.lumi.view;

import com.qualcomm.QCARSamples.CloudRecognition.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MenuLumi extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_lumi);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_lumi, menu);
		return true;
	}
	
	public void irPinacoteca(View view) {
	    Intent intent = new Intent(this, Pinacoteca.class);	
	    startActivity(intent);
	}

}