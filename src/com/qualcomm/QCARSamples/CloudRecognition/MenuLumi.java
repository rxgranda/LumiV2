package com.qualcomm.QCARSamples.CloudRecognition;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;

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
	public void irCloudReco(View view)
	{
        Intent i = new Intent(this, CloudReco.class);
        startActivity(i);
	}

	
	public void evento(View view){
		Intent i = new Intent(this, Pinacoteca2.class);
        startActivity(i);
		
	}
}