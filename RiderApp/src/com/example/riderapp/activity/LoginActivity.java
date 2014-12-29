package com.example.riderapp.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.riderapp.R;
import com.example.riderapp.webservice.WsClient;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button login_btn;
	public TextView tv_username;

	WsClient wsClient;
	ImageView imageView;
	private static final String IMGUR_CLIENT_ID = "...";
	private static final MediaType MEDIA_TYPE_PNG = MediaType
			.parse("image/jpeg");

	private static final int CAMERA_REQUEST = 1888;
	private static final int ADD_ACTIVITY_CODE = 1002;
	String imageToBeUpload;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout_normal);
		login_btn = (Button) findViewById(R.id.btn_login);
		tv_username = (TextView) findViewById(R.id.text_username);
		imageView = (ImageView) findViewById(R.id.login_logo);
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
				// Intent cameraIntent = new Intent(
				// MediaStore.ACTION_IMAGE_CAPTURE);
				// startActivityForResult(cameraIntent, CAMERA_REQUEST);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == Activity.RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap bitmap = (Bitmap) extras.get("data");
			imageView.setImageBitmap(bitmap);// 将图片显示在ImageView里

			File myDir = new File(Environment.getExternalStorageDirectory()
					+ "/savedimages");
			myDir.mkdirs();
			String fname = "Image-" + System.currentTimeMillis() + ".jpg";
			File file = new File(myDir, fname);
			FileOutputStream out;
			try {
				out = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
				imageToBeUpload = file.getPath();
				Toast.makeText(LoginActivity.this, file.getPath(),
						Toast.LENGTH_LONG).show();
				new LoginTask().execute();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class LoginTask extends AsyncTask<String, Object, Object> {

		@Override
		protected Object doInBackground(String... params) {
			// Object result = wsClient.getSoapObject("getPerson", "name",
			// "aahha", "age", "1");
			// Vector response = (Vector) result;
			// Object[] objs = response.toArray();
			Object result = "success";

			OkHttpClient client = new OkHttpClient();
			RequestBody requestBody = new MultipartBuilder()
					.type(MultipartBuilder.FORM)
					.addPart(
							Headers.of("Content-Disposition",
									"form-data; name=\"title\" "),
							RequestBody.create(null, "Square Logo"))
					.addPart(
							Headers.of("Content-Disposition",
									"form-data; name=\"image\" ;filename=\"love.jpg\" "),
							RequestBody.create(MEDIA_TYPE_PNG, new File(
									imageToBeUpload))).build();

			Request request = new Request.Builder()
					.header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
					.url("http://192.168.1.102:8080/testapp/Testdb")
					.post(requestBody).build();

			Response response;
			try {
				response = client.newCall(request).execute();
				System.out.println(response.body().string());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPostExecute(Object result) {
			tv_username.setText(result.toString());

			super.onPostExecute(result);
		}
	}

}
