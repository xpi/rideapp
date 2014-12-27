package com.example.riderapp.activity;

import java.lang.reflect.Method;
import java.util.Vector;

import org.ksoap2.serialization.SoapObject;

import com.example.riderapp.R;
import com.example.riderapp.webservice.WsClient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button login_btn;
	public TextView tv_username;

	WsClient wsClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout_normal);
		login_btn = (Button) findViewById(R.id.btn_login);
		tv_username = (TextView) findViewById(R.id.text_username);

		// http://localhost:8080/axis2/services/ComplexObj/getPerson?name=w&age=100
		wsClient = new WsClient(
				"http://192.168.1.102:8080/axis2/services/ComplexObj?wsdl",
				"http://test.com");
		login_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(LoginActivity.this,
				 MainActivity.class);
				 startActivity(intent);
				//new LoginTask().execute();
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.bar_regist) {
			Intent intent = new Intent(this, RegistActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login_menu, menu);
		return true;
	}

	class LoginTask extends AsyncTask<String, Object, Object> {

		@Override
		protected Object doInBackground(String... params) {
			Object result = wsClient.getSoapObject("getPerson", "name",
					"aahha", "age", "1");
			Vector response = (Vector) result;
			Object[] objs = response.toArray();

			return result;
		}

		@Override
		protected void onPostExecute(Object result) {
			tv_username.setText(result.getClass().getName());

			super.onPostExecute(result);
		}
	}

}
