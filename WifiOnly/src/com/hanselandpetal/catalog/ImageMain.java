package com.hanselandpetal.catalog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ImageMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flower_show);
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		
		TextView tv = (TextView) findViewById(R.id.flower_detail);
		tv.setText(message+"   ");

	}

}
